package ec.com.ebos.master.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ec.com.ebos.master.model.Persona;
import ec.com.ebos.mse.exception.MseException;
import ec.com.ebos.mse.web.jsf.bean.MonaguilloBean;
import ec.com.ebos.util.HTTPUtils;
import ec.com.ebos.util.NumberUtils;

public class DisplayImage extends AbstractFacesServlet {
 
	private static final long serialVersionUID = 6571934761156787429L;

	
	@Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        try {
 
            String id = request.getParameter("Image_id");
            System.out.println("inside servlet–>" + id);
 
            //Map<String, Object> viewMap = getFacesContext(request, response).getViewRoot().getViewMap();
            //MonaguilloBean monaguilloBean = (MonaguilloBean) viewMap.get(MonaguilloBean.BEAN_NAME);
            
            MonaguilloBean monaguilloBean = (MonaguilloBean) getManagedBean(MonaguilloBean.BEAN_NAME, getFacesContext(request, response));
            
            Persona persona = monaguilloBean.getPersona(NumberUtils.parseLong(id));
            response.reset();
            response.setContentType(persona.getContentType());
            response.getOutputStream().write(persona.getImagen());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    protected MonaguilloBean getMonaguilloBean2() {
        MonaguilloBean monaguilloBean = (MonaguilloBean) HTTPUtils.getSessionAttribute(MonaguilloBean.BEAN_NAME);
        if (monaguilloBean == null) {
            throw new MseException("mse.error.monaguillobean");
        }
        return monaguilloBean;
    }

	@Override
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
}