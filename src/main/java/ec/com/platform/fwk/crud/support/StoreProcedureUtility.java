package ec.com.platform.fwk.crud.support;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class StoreProcedureUtility extends StoredProcedure {
	HashMap valoresParametrosEntrada = new HashMap();

	public Map execute() {
		return super.execute(valoresParametrosEntrada);
	}

	ResultSetExtractor resultSetExtractor = new ResultSetExtractor() {
		public Object extractData(ResultSet resultSet) throws SQLException,
				DataAccessException {
			List data = new ArrayList();
			while (resultSet.next()) {
				List datos = new ArrayList();
				int colCount = resultSet.getMetaData().getColumnCount();
				for (int i = 1; i <= colCount; i++) {
					datos.add(resultSet.getString(i));
				}
				data.add(datos.toArray());
			}
			return data;
		}
	};

	RowMapper rowMapper = new RowMapper() {

		public Object mapRow(ResultSet resultSet, int pArg1)
				throws SQLException {
			List data = new ArrayList();
			while (resultSet.next()) {
				List datos = new ArrayList();
				int colCount = resultSet.getMetaData().getColumnCount();
				for (int i = 1; i <= colCount; i++) {
					datos.add(resultSet.getString(i));
				}
				data.add(datos.toArray());
			}
			return data;
		}

	};

	void init(ParametrosProcedimiento[] pParametros) {
		for (int i = 0; i < pParametros.length; i++) {
			ParametrosProcedimiento param = (ParametrosProcedimiento) pParametros[i];

			int tipo = param.getTipoParametro();

			switch (tipo) {
			case ParametrosProcedimiento.ENTRADA:
				declareParameter(new SqlParameter(param.getNombre(), param
						.getTipoDato()));
				valoresParametrosEntrada.put(param.getNombre(), param
						.getValor());
				break;
			case ParametrosProcedimiento.SALIDA:

				if (param.getTipoDato() == Tipos.CURSOR) {
					if (param.getMapeador() == null) {
						declareParameter(new SqlOutParameter(param.getNombre(),
								param.getTipoDato(), resultSetExtractor));

					} else {
						if (param.getMapeador() instanceof RowMapper) {
							declareParameter(new SqlOutParameter(param
									.getNombre(), param.getTipoDato(),
									(RowMapper) param.getMapeador()));
						} else if (param.getMapeador() instanceof ResultSetExtractor) {
							declareParameter(new SqlOutParameter(param
									.getNombre(), param.getTipoDato(),
									(ResultSetExtractor) param.getMapeador()));
						}
					}
				} else {
					declareParameter(new SqlOutParameter(param.getNombre(),
							param.getTipoDato()));
				}
				break;
			default:
				break;
			}
		}
		compile();
	}

	public StoreProcedureUtility(DataSource dataSource,
			String pNombreProcedimiento, ParametrosProcedimiento[] pParametros) {
		this(dataSource, pNombreProcedimiento, pParametros, false);
	}

	public StoreProcedureUtility(DataSource dataSource,
			String pNombreProcedimiento, ParametrosProcedimiento[] pParametros,
			boolean isFuncion) {
		super(dataSource, pNombreProcedimiento);
		setFunction(isFuncion);
		init(pParametros);
	}
}