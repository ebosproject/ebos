package ec.com.platform.util.web.jsf.event;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.ResponseStateManager;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.primefaces.context.RequestContext;

import ec.com.platform.util.Constantes;
import ec.com.platform.util.DateUtils;
import ec.com.platform.util.HTTPUtils;

/**
 * 
 * @author Eduardo Plua Alay
 *
 */
public class SessionPhaseListener implements PhaseListener {

	private static final long serialVersionUID = -2968652619085120630L;

        private static boolean LOGGING = Boolean.parseBoolean(System.getProperty("ciscnet.session_timeout.logging", "TRUE"));

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		FacesContext context = event.getFacesContext();
		ExternalContext ext = context.getExternalContext();
		HttpSession session = (HttpSession) ext.getSession(false);
		boolean newSession = (session == null) || (session.isNew());
		Map<String, String> requestMap = ext.getRequestParameterMap();
		boolean postback = requestMap.containsKey(ResponseStateManager.VIEW_STATE_PARAM);
		boolean atloginform = requestMap.containsKey(Constantes.LOGIN_FORM_ID);
		boolean atlogoutform = requestMap.containsKey(Constantes.LOGOUT_FORM_ID);
		boolean timedout = (postback && newSession) && !atloginform && !atlogoutform;
		if (timedout) {
			doRedirect(context, "beforePhase");
		}
	}

	@Override
	public void afterPhase(PhaseEvent event) {
		FacesContext context = event.getFacesContext();
		ExternalContext ext = context.getExternalContext();
		String viewId = (context.getViewRoot() == null ? null : context.getViewRoot().getViewId());
		boolean atjsfpage = (StringUtils.endsWith(viewId, Constantes.JSP_SUFFIX) || StringUtils.endsWith(viewId, Constantes.XHTML_SUFFIX));
		boolean athomepage = StringUtils.startsWith(viewId, Constantes.HOME_VIEW);
		boolean withtoken = ext.getSessionMap().containsKey(Constantes.LOGGED_USER_ATTR);
		boolean timedout = !withtoken && atjsfpage && !athomepage;
		if (timedout) {
			doRedirect(context,"afterPhase");
		}
	}

	/**
     * Does a regular or ajax redirect.
     */
    public static void doRedirect(FacesContext fc, String phase) 
        throws FacesException {
        ExternalContext ec = fc.getExternalContext();
        String redirectPage = (fc.getViewRoot() == null ? null : fc.getViewRoot().getViewId());
        try {
            if (ec.isResponseCommitted()) {
                // redirect is not possible
                return;
            }

            // fix for renderer kit (Mojarra's and PrimeFaces's ajax redirect)
            if ((RequestContext.getCurrentInstance().isAjaxRequest()
                || fc.getPartialViewContext().isPartialRequest())
                && fc.getResponseWriter() == null
                && fc.getRenderKit() == null) {
                    ServletResponse response = (ServletResponse) ec.getResponse();
                    ServletRequest request = (ServletRequest) ec.getRequest();
                    response.setCharacterEncoding(request.getCharacterEncoding());

                    RenderKitFactory factory = (RenderKitFactory)  
                     FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);

                    RenderKit renderKit = factory.getRenderKit(fc,
                     fc.getApplication().getViewHandler().calculateRenderKitId(fc));

                    ResponseWriter responseWriter =
                        renderKit.createResponseWriter(
                        response.getWriter(), null, request.getCharacterEncoding());
                        fc.setResponseWriter(responseWriter);
            }

            ec.redirect(ec.getRequestContextPath() + 
                (redirectPage != null ? redirectPage : ""));
            
            if (LOGGING) {
				System.out.println(DateUtils.getFormattedTimestamp() + 
						" [" + HTTPUtils.getRemoteAddr(((HttpServletRequest) ec.getRequest())) + "]" +
						" >>> SessionPhaseListener[" + phase + "]: Session timed out");
			}
        } catch (IOException e) {
        	System.err.println("Redirect to the specified page '" + 
                redirectPage + "' failed");
            throw new FacesException(e);
        }
    }
	
	public static void doRedirect() {
		FacesContext context = FacesContext.getCurrentInstance();
		doRedirect(context ,"noPhase");
	}

}
