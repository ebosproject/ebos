package ec.com.platform.app.web.jsf.component;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.AjaxBehaviorListener;
import javax.faces.event.FacesListener;

/**
 * ResizeEvent
 *
 * @author Oleg Varaksin / last modified by $Author: $
 * @author Eduardo Plua Alay
 * @version $Revision: 2.0 $
 */
public class ResizeEvent extends AjaxBehaviorEvent {

	private static final long serialVersionUID = -7876083775546797254L;
	
	private double width;
	private double height;

	public ResizeEvent(UIComponent component, Behavior behavior, double width, double height) {
		super(component, behavior);
		this.width = width;
		this.height = height;
	}

	public final double getWidth() {
		return width;
	}

	public final double getHeight() {
		return height;
	}

	@Override
	public boolean isAppropriateListener(FacesListener facesListener) {
		return (facesListener instanceof AjaxBehaviorListener);
	}

	@Override
	public void processListener(FacesListener facesListener) {
		if (facesListener instanceof AjaxBehaviorListener) {
			((AjaxBehaviorListener) facesListener).processAjaxBehavior(this);
		}
	}
}
