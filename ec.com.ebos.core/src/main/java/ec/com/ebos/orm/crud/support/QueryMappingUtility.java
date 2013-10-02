package ec.com.ebos.orm.crud.support;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.object.MappingSqlQuery;

/**
 * @author <a href="mailto:juanleonsolis@gmail.com">Ing. Juan Leon Solis</a>
 * @FechaModificacion Feb 28, 2007
 */

public class QueryMappingUtility extends MappingSqlQuery {

	public List execute() {
		return super.execute();
	}

	public QueryMappingUtility() {
	}

	protected Object mapRow(ResultSet pRs, int pRowNum) throws SQLException {
		return null;
	}

}
