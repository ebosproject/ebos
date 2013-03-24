package ec.com.platform.util.web.jsf.handler;

import java.io.IOException;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.application.ViewHandlerWrapper;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import lombok.Getter;
import ec.com.platform.util.Constantes;
import ec.com.platform.util.web.jsf.event.SessionPhaseListener;

/**
 * 
 * @author Eduardo Plua Alay
 *
 */
public class SessionViewHandler extends ViewHandlerWrapper{

    @Getter
    private ViewHandler wrapped;

    public SessionViewHandler(ViewHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public UIViewRoot restoreView(FacesContext context, String viewId) {
        UIViewRoot restoredView = wrapped.restoreView(context, viewId);
        
        if (!(restoredView == null && context.isPostback())) {
            return restoredView;
        }
            
    	//TODO (epa): Personalizar esta logica
    	Map<String, String> requestMap = context.getExternalContext().getRequestParameterMap();
        boolean atloginform = requestMap.containsKey(Constantes.LOGIN_FORM_ID);
        boolean atlogoutform = requestMap.containsKey(Constantes.LOGOUT_FORM_ID);
        boolean timeout = !atloginform && !atlogoutform;
        if (timeout) {
            viewId = Constantes.TIMEOUT_PAGE;
        }
        
        //super.initView(context);
        UIViewRoot createdView = createView(context, viewId);
        context.setViewRoot(createdView);
        
        try {
            getViewDeclarationLanguage(context, viewId).buildView(context, createdView);
        } catch (IOException e) {
            throw new FacesException(e);
        }
        
        if (timeout) {
            SessionPhaseListener.doRedirect();
        }
        
        return createdView;
    }
}
