package ec.com.ebos.master.web.jsf.component;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.AjaxBehaviorListener;
import javax.faces.event.FacesListener;

/**
 * OpenEvent
 *
 * @author Oleg Varaksin / last modified by $Author: $
 * @author Eduardo Plua Alay
 * @version $Revision: 2.0 $
 */
public class OpenEvent extends AjaxBehaviorEvent {

	private static final long serialVersionUID = -2077495728332439187L;

	public OpenEvent(UIComponent component, Behavior behavior) {
		super(component, behavior);
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
