package ec.com.ebos.bitacora.core.process;

import org.springframework.stereotype.Repository;

import ec.com.ebos.bitacora.core.exception.BitacoraException;
import ec.com.ebos.root.core.process.RootPImpl;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-03-12
 * 
 */
@Repository("bitacoraG")
public class BitacoraPImpl extends RootPImpl<Object, BitacoraException> implements BitacoraP {

	private static final long serialVersionUID = -2677308041792049091L;
	
}
