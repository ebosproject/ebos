package ec.com.ebos.orm.crud;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Parametros de paginacion de una consulta
 * 
 */
public final class PaginationParams {

	public static final int DEFAULT_ROWS_PER_PAGE = 50;
	public static final int ROWS_PER_SEARCH_PAGE = 25;
	public static final int ROWS_PER_DETAIL_PAGE = 50;
	public static List<Integer> ROWS_PER_PAGE_LIST = Arrays.asList(10, 25, 50, 75, 100);
	private static Map<Integer, Integer> rowsPerPageOptions;
	static {
		rowsPerPageOptions = new LinkedHashMap<Integer, Integer>();
		for (Integer i : PaginationParams.ROWS_PER_PAGE_LIST) {
			rowsPerPageOptions.put(i, i);
		}
	}
	public static final int PAGE_DELTA = 5;

	public int currentPage;
	public int rowsPerPage;
	public int totalPages;
	public int totalRows;
	public boolean usePages;

	private int firstRow;

	private Map<Integer, Integer> pagesToScroll = new LinkedHashMap<Integer, Integer>();

	private PaginationParams(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
		this.usePages = false;
		reset();
	}

	public PaginationParams() {
		this(DEFAULT_ROWS_PER_PAGE);
	}

	public static PaginationParams forDetailPage() {
		return new PaginationParams(ROWS_PER_DETAIL_PAGE);
	}

	public static PaginationParams forSearchPage() {
		return new PaginationParams(ROWS_PER_SEARCH_PAGE);
	}

	public void reset() {
		currentPage = 1;
		totalPages = 1;
		totalRows = 0;
	}

	/**
	 * Valida y corrige <code>currentPage</code> y <code>rowsPerPage</code>
	 * para que esten dentro de los limites normales.<br>
	 * Calcula <code>firstRow</code> en base a <code>currentPage</code> y <code>rowsPerPage</code>.
	 */
	public void init() {
		// validar currentPage
		if (currentPage < 1) { 
			currentPage = 1;
		} else if (currentPage > totalPages) {
			currentPage = totalPages;
		}
		// validar rowsPerPage
		if (!ROWS_PER_PAGE_LIST.contains(rowsPerPage)) {
			rowsPerPage = DEFAULT_ROWS_PER_PAGE;
		}
		// recalcular firstRow
		firstRow = (currentPage - 1) * rowsPerPage;
	}

	/**
	 * Recalcula <code>totalRows</code>, <code>totalPages</code>
	 * y la bandera <code>usePages</code>.<br>
	 * Reconstruye las opciones de paginacion.
	 */
	public void calcPages(int size) {
		pagesToScroll.clear();
		if (size > rowsPerPage || totalRows == 0) {
			// si hay mas elementos que rowsPerPage o no se modifico el total, es que no hubo paginacion
			usePages = false;
			totalRows = size;
			totalPages = 1;
			pagesToScroll.put(1, 1);
		} else {
			// calcular paginacion
			usePages = true;
			totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
			// reconstruir opciones
			int minPage = Math.max(1, currentPage - PaginationParams.PAGE_DELTA);
			int maxPage = Math.min(totalPages, currentPage + PaginationParams.PAGE_DELTA);
			for (int i = minPage; i <= maxPage; i++) {
				pagesToScroll.put(i, i);
			}
		}
	}

	public Map<Integer, Integer> getRowsPerPageOptions() {
		return rowsPerPageOptions;
	}

	public Map<Integer, Integer> getPagesToScroll() {
		return pagesToScroll;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public int getFirstRow() {
		return firstRow;
	}

	public int getMaxRows() {
		return rowsPerPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public boolean isUsePages() {
		return usePages;
	}

	public void setUsePages(boolean usePages) {
		this.usePages = usePages;
	}

}
