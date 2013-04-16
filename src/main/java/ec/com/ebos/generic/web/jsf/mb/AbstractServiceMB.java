package ec.com.ebos.generic.web.jsf.mb;

import java.io.Serializable;

import javax.faces.bean.ManagedProperty;

import lombok.Getter;
import lombok.Setter;
import ec.com.ebos.app.web.jsf.mb.SessionMB;
import ec.com.ebos.seguridad.core.servicio.SeguridadS;

/**
 * Superclase de los ManageBeans.
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
@Deprecated
public abstract class AbstractServiceMB implements Serializable {

	private static final long serialVersionUID = -962367358939713030L;

	@Getter @Setter
    @ManagedProperty(value = "#{"+SessionMB.BEAN_NAME+"}")
    protected SessionMB sessionMB;
     
    @Getter @Setter
    @ManagedProperty(value = "#{seguridadS}")
    protected SeguridadS seguridadS;
    
}