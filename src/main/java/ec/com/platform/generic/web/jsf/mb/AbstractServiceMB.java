package ec.com.platform.generic.web.jsf.mb;

import java.io.Serializable;

import javax.faces.bean.ManagedProperty;

import lombok.Getter;
import lombok.Setter;
import ec.com.platform.app.web.jsf.mb.SesionUsuarioMB;
import ec.com.platform.seguridad.core.servicio.SeguridadS;

/**
 * Superclase de los ManageBeans.
 *
 * @author Eduardo Plua Alay
 */
public abstract class AbstractServiceMB implements Serializable {

	private static final long serialVersionUID = -962367358939713030L;

	@Getter @Setter
    @ManagedProperty(value = "#{sesionUsuario}")
    protected SesionUsuarioMB sesionUsuario = null;
     
    @Getter @Setter
    @ManagedProperty(value = "#{seguridadS}")
    protected SeguridadS seguridadS;

}