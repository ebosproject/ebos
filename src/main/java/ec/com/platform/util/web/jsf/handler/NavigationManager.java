/*
 * Created on 25.10.2004 by Sergey Smirnov, Exadel, Inc.
 *
 */
package ec.com.platform.util.web.jsf.handler;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;

/**
 * NavigationHandler basado en org.exadel.NavigationManager
 * @see javax.faces.application.NavigationHandler
 * @see com.sun.faces.application.NavigationHandlerImpl
 * @author Sergey Smirnov, Exadel, Inc.
 * @author Eduardo Plua Alay 
 */
public class NavigationManager extends NavigationHandler {

	NavigationHandler _base;

	public NavigationManager(NavigationHandler base) {
		super();
		_base = base;
	}

	public void handleNavigation(FacesContext fc, String actionMethod, String action) {
		_base.handleNavigation(fc, actionMethod, action);

	}

}