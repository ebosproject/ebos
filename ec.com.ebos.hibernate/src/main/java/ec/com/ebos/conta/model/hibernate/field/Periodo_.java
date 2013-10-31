package ec.com.ebos.conta.model.hibernate.field;

import ec.com.ebos.conta.model.field.Contabilidad_;
import ec.com.ebos.conta.model.hibernate.HibernatePeriodo;


/**
 * Entity model attributes for {@link HibernatePeriodo}
 * 
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
public abstract class Periodo_ extends Contabilidad_{

	public static final String ejercicio = "ejercicio";
	public static final String periodoFiscalPais = "periodoFiscalPais";
	public static final String fechaInicial = "fechaInicial";
	public static final String fechaFinal = "fechaFinal";
	
}

