package ec.com.ebos.orm.crud.support;

public class ParametroSalida extends ParametrosProcedimiento {

	/**
	 * 
	 */
	public ParametroSalida() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param pNombre
	 * @param pValor
	 * @param pTipoDato
	 * @param pTipoParametro
	 */
	public ParametroSalida(String pNombre, int pTipoDato) {
		super(pNombre, null, pTipoDato, ParametrosProcedimiento.SALIDA);
	}
	
	/**
	 * @param pNombre
	 * @param pValor
	 * @param pTipoDato
	 * @param pTipoParametro
	 */
	public ParametroSalida(String pNombre, Object pValor, int pTipoDato) {
		super(pNombre, pValor, pTipoDato, ParametrosProcedimiento.SALIDA);
	}

	/**
	 * @param pNombre
	 * @param pValor
	 * @param pTipoDato
	 * @param pTipoParametro
	 * @param pMapeador
	 */
	public ParametroSalida(String pNombre, Object pValor, int pTipoDato, Object pMapeador) {
		super(pNombre, pValor, pTipoDato, ParametrosProcedimiento.SALIDA);
		setMapeador(pMapeador);
	}
	
	
	/**
	 * El Default  del tipo de Dato es Tipos.VARCHAR
	 * @param pNombre
	 * @param pValor
	 * @param pTipoParametro
	 */
	public ParametroSalida(String pNombre, Object pValor) {
		super(pNombre, pValor, Tipos.VARCHAR, ParametrosProcedimiento.SALIDA);
	}
 

}
