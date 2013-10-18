package ec.com.ebos.orm.crud.support;

public class ParametroEntrada extends ParametrosProcedimiento {

	/**
	 * 
	 */
	public ParametroEntrada() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param pNombre
	 * @param pValor
	 * @param pTipoDato
	 * @param pTipoParametro
	 */
	public ParametroEntrada(String pNombre, Object pValor, int pTipoDato) {
		super(pNombre, pValor, pTipoDato, ParametrosProcedimiento.ENTRADA);
	}

	/**
	 * El Default  del tipo de Dato es Tipos.VARCHAR
	 * @param pNombre
	 * @param pValor
	 * @param pTipoParametro
	 */
	public ParametroEntrada(String pNombre, Object pValor) {
		super(pNombre, pValor, Tipos.VARCHAR, ParametrosProcedimiento.ENTRADA);
	}

}
