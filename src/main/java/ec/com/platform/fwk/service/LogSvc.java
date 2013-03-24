package ec.com.platform.fwk.service;



public interface LogSvc {
	public static final String LOG_INTCPT = "SERVICIO";
	public static final String LOG_SAD = "SAD";
	public static final String LOG_CRUD = "CRUD";
	void writeToLog(LogFwkSwiss pLogFwkSat );
}
