package ec.com.ebos.app.web.jsf.component;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.component.tabview.Tab;

import ec.com.ebos.seguridad.model.Opcion;

public class CustomTab extends Tab{
	@Getter @Setter
	private Opcion opcion; 
}
