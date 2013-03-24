package ec.com.platform.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author Luis Tama Wong
 *
 */
public class DateUtils {

    public static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("GMT");
    /**
     * Number of milliseconds in a standard second.
     * @since 2.1
     */
    public static final long MILLIS_PER_SECOND = 1000;
    /**
     * Number of milliseconds in a standard minute.
     * @since 2.1
     */
    public static final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND;
    /**
     * Number of milliseconds in a standard hour.
     * @since 2.1
     */
    public static final long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;
    /**
     * Number of milliseconds in a standard day.
     * @since 2.1
     */
    public static final long MILLIS_PER_DAY = 24 * MILLIS_PER_HOUR;

	public static final int SECONDS_PER_MINUTE = 60;
	public static final int SECONDS_PER_HOUR = 60 * SECONDS_PER_MINUTE;
	public static final int SECONDS_PER_DAY = 24 * SECONDS_PER_HOUR;
	public static final int SECONDS_PER_WEEK = 7 * SECONDS_PER_DAY;

    /**
     * This is half a month, so this represents whether a date is in the top
     * or bottom half of the month.
     */
    public final static int SEMI_MONTH = 1001;

    /**
     * Simple time format: "HH:mm"
     */
    public static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss.SSS");
    /**
     * Simple date format: "dd-MM-yyyy"
     */
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    /**
     * Full timestamp (date-time) format: "yyyy-MM-dd HH:mm:ss.SSS"
     */
    public static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * Validate date range, considering date, month and year only (not time).
	 * @param startDate
	 * @param endDate
	 * @param acceptBothNull
	 * @param acceptEquals
	 * @return true in the following cases:<br />
	 * 1. acceptBothNull is true, and both startDate and endDate are null<br />
	 * 2. acceptEquals is true and startDate equals endDate<br />
	 * 3. startDate is before endDate
	 */
	public static boolean validateRange(Date startDate, Date endDate, boolean acceptBothNull, boolean acceptEquals) {
		if (startDate == null && endDate == null) // ambas fechas son null
			return acceptBothNull;
		if (startDate == null || endDate == null) // una de las dos es null -> error
			return false;
		if (areSameDateMonthYear(startDate,endDate)) // ambas fechas son iguales en dia, mes y anio
			return acceptEquals;
		return startDate.before(endDate); // orden de las fechas
	}
	
	public static Calendar getCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	public static Date getFirstDateMonth(Date dateFrom) {
		Calendar calFrom = Calendar.getInstance(); // fecha de hoy
		calFrom.setTime(dateFrom);
		calFrom.set(Calendar.DAY_OF_MONTH, 1); // primer dia de este mes
		return calFrom.getTime();
	}
	public static Date getFirstDateMonth() {
		return getFirstDateMonth(getCurrentDate());
	}

	public static Date getLastDateMonth(Date dateFrom) {
		Calendar calFrom = new GregorianCalendar(); // fecha de hoy
		calFrom.setTime(dateFrom);
		calFrom.set(Calendar.DAY_OF_MONTH, 1); // primer dia de este mes
		calFrom.add(Calendar.MONTH, 1); // sumar un mes
		calFrom.add(Calendar.DAY_OF_MONTH, -1); // restar un mes -> ultimo dia de este mes
		return calFrom.getTime();
	}
	public static Date getLastDateMonth() {
		return getLastDateMonth(getCurrentDate());
	}
	
	/**
	 * Tests for date equality based on date, month and year.
	 * @param date1
	 * @param date2
	 * @return boolean
	 */
	public static boolean areSameDateMonthYear(Date date1, Date date2) {
		Calendar cal1 = new GregorianCalendar();
		cal1.setTime(date1);
		Calendar cal2 = new GregorianCalendar();
		cal2.setTime(date2);
		return cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH) &&
				cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
				cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
	}
	
	/**
	 * Tests for date equality based on month and year.
	 * @param date1
	 * @param date2
	 * @return boolean
	 */
	public static boolean areSameMonthYear(Date date1, Date date2) {
		Calendar cal1 = new GregorianCalendar();
		cal1.setTime(date1);
		Calendar cal2 = new GregorianCalendar();
		cal2.setTime(date2);
		return cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
				cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
	}
	
	/**
     * Adds a number of days to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date  the date, not null
     * @param amount  the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     * 
     * @see org.apache.commons.lang.time.DateUtils#addDays(Date, int)
     */
	public static Date addDays(Date date, int amount) {
		return org.apache.commons.lang.time.DateUtils.addDays(date, amount);
	}

	public static Date truncate(Date date) {
		return org.apache.commons.lang.time.DateUtils.truncate(date, Calendar.DATE);
	}

	/**
     * Sets the hours field to a date returning a new object.  Hours range 
     * from  0-23.
     * The original date object is unchanged.
     *
     * @param date  the date, not null
     * @param amount the amount to set
     * @return a new Date object set with the specified value
     * @throws IllegalArgumentException if the date is null
     * 
     * @see org.apache.commons.lang.time.DateUtils#addHours(Date, int)
     */
 	public static Date setHours(Date date, int amount) {
		return org.apache.commons.lang.time.DateUtils.setHours(date, amount);
	}
	
    /**
     * Sets the minute field to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date  the date, not null
     * @param amount the amount to set
     * @return a new Date object set with the specified value
     * @throws IllegalArgumentException if the date is null
     * 
     * @see org.apache.commons.lang.time.DateUtils#setMinutes(Date, int)
     */
 	public static Date setMinutes(Date date, int amount) {
		return org.apache.commons.lang.time.DateUtils.setMinutes(date, amount);
	}

    /**
     * Sets the seconds field to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date  the date, not null
     * @param amount the amount to set
     * @return a new Date object set with the specified value
     * @throws IllegalArgumentException if the date is null
     * 
     * @see org.apache.commons.lang.time.DateUtils#setSeconds(Date, int)
     */
	public static Date setSeconds(Date date, int amount) {
		return org.apache.commons.lang.time.DateUtils.setSeconds(date, amount);
	}
	
	/**
	 * @see http://www.java2s.com/Code/Java/Development-Class/DateDiffcomputethedifferencebetweentwodates.htm
	 */
	public static long getDiffDays(Date date1, Date date2) {
	    // Get msec from each, and subtract.
	    long diff = date2.getTime() - date1.getTime();
		return diff / (1000 * 60 * 60 * 24);
	}

	public static String getDiffDate(Date date1, Date date2) {
	    // Get msec from each, and subtract.
	    Long diff = date2.getTime() - date1.getTime();
	    Double daysD = new Double(diff) / (1000 * 60 * 60 * 24);
	    Long daysL = (new Double(Math.floor(daysD))).longValue();
	    Double hoursD = (daysD-daysL) * 24;
	    Long hoursL = (new Double(Math.floor(hoursD))).longValue();
	    Long minutes = (new Double((hoursD - hoursL) * 60)).longValue();
	    String value = minutes + " m.";
	    
	    if(!hoursL.equals(0L) || !minutes.equals(0L)){
	    	value = hoursL + " h. " + value;
	    }
	    if(!daysL.equals(0L)){
	    	value = daysL + " d. " + value;
	    }
		return value;
	}

	/**
	 * Permite obtener la cantidad de meses que existe entre 2 fechas
	 * @param fechaInicial
	 * @param fechaFinal
	 * @return int
	 * @see ec.com.kruger.framework.common.util.ConverterUtil#mesesDiferencia(Date,Date)
	 */
	public static final int getDiffMonths(Date fechaInicial, Date fechaFinal) {
		// TODO revisar logica
		Calendar calInicial = new GregorianCalendar();
		calInicial.setTime(fechaInicial);
		Calendar calFinal = new GregorianCalendar();
		calFinal.setTime(fechaFinal);
		int mesInicial = (calInicial.get(Calendar.YEAR) * 12) + calInicial.get(Calendar.MONTH);
		int mesFinal = (calFinal.get(Calendar.YEAR) * 12) + calFinal.get(Calendar.MONTH);
		return mesFinal - mesInicial;
	}

	/**
     * Adds a number of months to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date  the date, not null
     * @param amount  the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     * 
     * @see org.apache.commons.lang.time.DateUtils#addMonths(Date, int)
     */
	public static Date addMonths(Date date, int amount) {
		return org.apache.commons.lang.time.DateUtils.addMonths(date, amount);
	}

	/**
	 * 
	 * @param date
	 * @return
	 * @since 2009-09-02
	 */
	public static Date getRoundedTime(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		Integer round = Math.round((float)calendar.get(Calendar.MINUTE) / 30);
		calendar.set(Calendar.MINUTE, round == 2 ? 0 : round * 30);
		if (round == 2)
			calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 1);
		return calendar.getTime();
	}

	/**
	 * 
	 * @param dayOfWeekDate
	 * @param weekStartDate
	 * @return
	 */
	public static Date getDateFromWeek(Date dayOfWeekDate, Date weekStartDate) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(dayOfWeekDate);
		return getDateFromWeek(cal.get(Calendar.DAY_OF_WEEK), weekStartDate);
	}

	/**
	 * 
	 * @param date
	 * @return
	 * @since 2009-09-02
	 */
	public static String getFormattedRoundedTime(Date date) {
		return getFormattedTime(getRoundedTime(date));
	}

	/**
	 * 
	 * @return
	 * @since 2012-02-26
	 */
	public static String getFormattedTime() {
		return getFormattedTime(getCurrentDate());
	}

	/**
	 * 
	 * @param date
	 * @return
	 * @since 2010-05-26
	 */
	public static String getFormattedTime(Date date) {
		return getFormattedDate(date, TIME_FORMAT);
	}

	/**
	 * 
	 * @return
	 * @since 2012-12-02
	 */
	public static String getFormattedTimestamp() {
		return getFormattedTimestamp(getCurrentDate());
	}

	/**
	 * 
	 * @param date
	 * @return
	 * @since 2012-12-02
	 */
	public static String getFormattedTimestamp(Date date) {
		return getFormattedDate(date, TIMESTAMP_FORMAT);
	}

	/**
	 * 
	 * @return current date formatted as "dd-MM-yyyy"
	 * @since 2012-02-02
	 * @see #DATE_FORMAT
	 * @see #getFormattedDate(Date, DateFormat)
	 */
	public static String getFormattedDate() {
		return getFormattedDate(getCurrentDate(), DATE_FORMAT);
	}

	/**
	 * 
	 * @param date
	 * @return date formatted as "dd-MM-yyyy"
	 * @since 2012-02-02
	 * @see #DATE_FORMAT
	 * @see #getFormattedDate(Date, DateFormat)
	 */
	public static String getFormattedDate(Date date) {
		return getFormattedDate(date, DATE_FORMAT);
	}

	/**
	 * 
	 * @param pattern
	 * @return current date formatted with given pattern
	 * @since 2012-04-25
	 * @see SimpleDateFormat
	 * @see #getFormattedDate(Date, DateFormat)
	 */
	public static String getFormattedDate(String pattern) {
		return getFormattedDate(getCurrentDate(), new SimpleDateFormat(pattern));
	}

	/**
	 * 
	 * @param date
	 * @param pattern
	 * @return date formatted with given pattern
	 * @since 2009-09-02
	 * @see SimpleDateFormat
	 * @see #getFormattedDate(Date, DateFormat)
	 */
	public static String getFormattedDate(Date date, String pattern) {
		return getFormattedDate(date, new SimpleDateFormat(pattern));
	}

	/**
	 * 
	 * @param date
	 * @param dateFormat
	 * @return date formatted with given format
	 * @since 2012-02-02
	 */
	public static String getFormattedDate(Date date, DateFormat dateFormat) {
		if (date == null || dateFormat == null) {
			return StringUtils.EMPTY;
		}
		return dateFormat.format(date);
	}

	/**
	 * 
	 * @param date
	 * @param pattern
	 * @return date parsed as "dd-MM-yyyy"
	 * @since 2012-04-25
	 * @see #DATE_FORMAT
	 * @see #getFormattedDate(Date, DateFormat)
	 */
	public static Date getParsedDate(String date) {
		return getParsedDate(date, DATE_FORMAT);
	}

	/**
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 * @since 2012-04-25
	 */
	public static Date getParsedDate(String date, String pattern) {
		return getParsedDate(date, new SimpleDateFormat(pattern));
	}

	/**
	 * 
	 * @param date
	 * @param dateFormat
	 * @return
	 * @since 2012-04-25
	 */
	public static Date getParsedDate(String date, DateFormat dateFormat) {
		if (StringUtils.isBlank(date) || dateFormat == null) {
			return null;
		}
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 
	 * @return
	 * @since 2012-04-25
	 */
	public static Date getCurrentDate() {
		return new Date();
	}

	/**
	 * 
	 * @param date
	 * @param weekStartDate
	 * @return
	 */
	public static Date getDateFromWeek(int dayOfWeek, Date weekStartDate) {
		Calendar fixedCal = new GregorianCalendar();
		fixedCal.setTime(weekStartDate);
		while (fixedCal.get(Calendar.DAY_OF_WEEK) != dayOfWeek) {
			fixedCal.add(Calendar.DATE, 1);
		}
		return fixedCal.getTime();
	}

	/**
	 * Tests for date equality based on month and year.
	 * @param date1
	 * @param date2
	 * @return boolean
	 */
	public static boolean areSameMonthYearOrLeast(Date date1, Date date2) {
		boolean condition = false;
		Calendar cal1 = new GregorianCalendar();
		cal1.setTime(date1);
		Calendar cal2 = new GregorianCalendar();
		cal2.setTime(date2);
		condition = cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
		cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
		if (!condition) {
			int anio1 = cal1.get(Calendar.YEAR);
			int anio2 = cal2.get(Calendar.YEAR);
			int mes1 = cal1.get(Calendar.MONTH);
			int mes2 = cal2.get(Calendar.MONTH);
			if (anio1 >= anio2) {
				if (anio1 > anio2) {
					condition = true;
				} else {
					if (mes1 >= mes2) {
						condition = true;
					}
				}
			}
		}
		return condition;
	}

	public static Date getFirstDateYear(Date dateFrom) {
		Calendar calFrom = Calendar.getInstance(); // fecha de hoy
		calFrom.setTime(dateFrom);
		calFrom.set(Calendar.DAY_OF_YEAR, 1); // primer dia de este a�o
		return calFrom.getTime();
	}
	
	/**
	 * month boundaries crossed between dates
	 * @param minuend
	 * @param subtrahend
	 * @return
	 * @see http://www.coderanch.com/t/381676/java/java/number-months-between-two-given
	 */
	public static int monthsBetween(Date minuend, Date subtrahend)  
	{  
		Calendar cal = Calendar.getInstance();  
		// default will be Gregorian in US Locales  
		cal.setTime(minuend);  
		int minuendMonth =  cal.get(Calendar.MONTH);  
		int minuendYear = cal.get(Calendar.YEAR);  
		cal.setTime(subtrahend);  
		int subtrahendMonth =  cal.get(Calendar.MONTH);  
		int subtrahendYear = cal.get(Calendar.YEAR);  
		  
		// the following will work okay for Gregorian but will not  
		// work correctly in a Calendar where the number of months   
		// in a year is not constant  
		return ((minuendYear - subtrahendYear) * cal.getMaximum(Calendar.MONTH)) +    
		(minuendMonth - subtrahendMonth);  
	}

	/**
	 * @author Eduardo Plua Alay
	 * Compara dos fecha con formato "dd-MM-yyyy"
	 * 	 
	 * @param fechaIni : Fecha inicial
	 * @param fechaFin : Fecha final
	 * @return the value 0 if the argument fechaFin is equal to this fechaIni; 
	 * 			a value less than 0 if this fechaIni is before the Date fechaFin;
	 * 			and a value greater than 0 if this fechaIni is after the Date fechaFin.	 
	 */
	public static int compareTo(Date fechaIni, Date fechaFin) {
		String fechaIniS = DateUtils.getFormattedDate(fechaIni);
		String fechaFinS = DateUtils.getFormattedDate(fechaFin);
		Date fechaIniFormat = DateUtils.getParsedDate(fechaIniS);
		Date fechaFinFormat = DateUtils.getParsedDate(fechaFinS);
						
		return fechaIniFormat.compareTo(fechaFinFormat);
	}  
	
}