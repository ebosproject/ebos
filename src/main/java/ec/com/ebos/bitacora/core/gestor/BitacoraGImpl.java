package ec.com.ebos.bitacora.core.gestor;

import org.springframework.stereotype.Repository;

import ec.com.ebos.bitacora.core.exception.BitacoraException;
import ec.com.ebos.generic.core.gestor.GenericGImpl;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-03-12
 * 
 */
@Repository("bitacoraG")
public class BitacoraGImpl extends GenericGImpl<Object, BitacoraException> implements BitacoraG {

	private static final long serialVersionUID = -2677308041792049091L;
	
}
