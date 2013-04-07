package ec.com.platform.fwk.crud.support;

import org.apache.commons.lang.ArrayUtils;

public abstract class ParametrosProcedimiento {

	public final static int ENTRADA = 0;

	public final static int SALIDA = 1;

	public final static int ENTRADA_SALIDA = 2;

	private String nombre;

	private int tipoParametro;

	private int tipoDato;

	private Object valor;

	private Object mapeador;

	public ParametrosProcedimiento() {
	}

	/**
	 * @param pNombre
	 * @param pValor
	 * @param pTipoDato
	 * @param pTipoParametro
	 */
	public ParametrosProcedimiento(String pNombre, Object pValor,
			int pTipoDato, int pTipoParametro) {
		nombre = pNombre;
		setTipoParametro(pTipoParametro);
		setTipoDato(pTipoDato);
		valor = pValor;
	}

	/**
	 * @param pNombre
	 * @param pTipoParametro
	 * @param pTipoDato
	 * @param pValor
	 * @param pMapeador
	 */
	public ParametrosProcedimiento(String pNombre, int pTipoParametro,
			int pTipoDato, Object pValor, Object pMapeador) {
		super();
		nombre = pNombre;
		tipoParametro = pTipoParametro;
		tipoDato = pTipoDato;
		valor = pValor;
		mapeador = pMapeador;
	}

	/**
	 * Metodo (getter) de acceso a nombre.
	 * 
	 * @return Retorna nombre.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Metodo (setter) de Modfificacion de nombre
	 * 
	 * @param pNombre
	 *            nombre a Asignar.
	 */
	public void setNombre(String pNombre) {
		nombre = pNombre;
	}

	/**
	 * Metodo (getter) de acceso a tipoDato.
	 * 
	 * @return Retorna tipoDato.
	 */
	public int getTipoDato() {
		return tipoDato;
	}

	/**
	 * Metodo (setter) de Modfificacion de tipoDato
	 * 
	 * @param pTipoDato
	 *            tipoDato a Asignar.
	 */
	public void setTipoDato(int pTipoDato) {
		if (ArrayUtils.contains(Tipos.TIPOS, pTipoDato)) {
			tipoDato = pTipoDato;
		} else {
			throw new IllegalArgumentException("Valor de parametro no valido: "
					+ pTipoDato + "."
					+ "Consultar los valores en ec.com.platform.fwk.crud.Tipos");
		}

	}

	/**
	 * Metodo (getter) de acceso a tipoParametro.
	 * 
	 * @return Retorna tipoParametro.
	 */
	public int getTipoParametro() {
		return tipoParametro;
	}

	/**
	 * Metodo (setter) de Modfificacion de tipoParametro
	 * 
	 * @param pTipoParametro
	 *            tipoParametro a Asignar.
	 */
	public void setTipoParametro(int pTipoParametro) {
		if ((pTipoParametro == ENTRADA) || (pTipoParametro == SALIDA)) {
			tipoParametro = pTipoParametro;
		} else {
			throw new IllegalArgumentException(
					"Valor de parametro no valido: "
							+ pTipoParametro
							+ "."
							+ "Consultar los valores en ec.com.platform.fwk.crud.ParametrosProcedimiento");
		}
	}

	/**
	 * Metodo (getter) de acceso a valor.
	 * 
	 * @return Retorna valor.
	 */
	public Object getValor() {
		return valor;
	}

	/**
	 * Metodo (setter) de Modfificacion de valor
	 * 
	 * @param pValor
	 *            valor a Asignar.
	 */
	public void setValor(Object pValor) {
		valor = pValor;
	}

	/**
	 * Metodo (getter) de acceso a mapeador.
	 * 
	 * @return Retorna mapeador.
	 */
	public Object getMapeador() {
		return mapeador;
	}

	/**
	 * Metodo (setter) de Modfificacion de mapeador
	 * 
	 * @param pMapeador
	 *            mapeador a Asignar.
	 */
	public void setMapeador(Object pMapeador) {
		mapeador = pMapeador;
	}

}
