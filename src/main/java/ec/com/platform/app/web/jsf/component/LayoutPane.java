package ec.com.platform.app.web.jsf.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

import ec.com.platform.util.Constantes;

/**
 * LayoutPane
 *
 * @author Oleg Varaksin / last modified by $Author: $
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @version $Revision: 1.0 $
 */
@FacesComponent(value = "ec.com.platform.app.web.jsf.component.LayoutPane")
public class LayoutPane extends UIComponentBase {

	public static final String COMPONENT_FAMILY = Constantes.DOMAIN_NAME+".app.web.jsf.component";
	private static final String DEFAULT_RENDERER = Constantes.DOMAIN_NAME+".app.web.jsf.component.LayoutPaneRenderer";

	/**
	 * PropertyKeys
	 *
	 * @author  Oleg Varaksin / last modified by $Author: $
	 * @version $Revision: 1.0 $
	 */
	protected enum PropertyKeys {

		position,
		combinedPosition
	}

	public LayoutPane() {
		setRendererType(DEFAULT_RENDERER);
	}

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	// position "north" | "south" | "west" | "east" | "center"
	public String getPosition() {
		return (String) getStateHelper().eval(PropertyKeys.position, "center");
	}

	public void setPosition(String position) {
		getStateHelper().put(PropertyKeys.position, position);
	}

	public String getCombinedPosition() {
		return (String) getStateHelper().eval(PropertyKeys.combinedPosition, "center");
	}

	public void setCombinedPosition(String combinedPosition) {
		getStateHelper().put(PropertyKeys.combinedPosition, combinedPosition);
	}
}
