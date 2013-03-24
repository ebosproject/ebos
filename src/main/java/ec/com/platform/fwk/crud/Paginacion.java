package ec.com.platform.fwk.crud;

import java.util.Map;

import lombok.Data;

import org.primefaces.model.SortOrder;

@Data
public class Paginacion{
	private int rowCount;
	private int first;
	private int pageSize;
	private String sortField;
    private SortOrder sortOrder;
    private Map<String, String> filters;
}