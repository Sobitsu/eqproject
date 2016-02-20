package org.hc.wm.utils;

import java.util.Calendar;
import java.util.Date;
import org.hc.jp.utils.DateTimeUtils;

public class LogUtil {
	
	private static String hvost(String filetype, Date fordate, String hashsum, String fileName){
		String retstr = filetype + " " + " for day " + DateTimeUtils.formatDate(fordate, DateTimeUtils.NSPCMAIL_DATE_FORMAT_STRING) + " " +" HASH-SUM " + " " +hashsum + " " + "FileName " + " " + fileName;
		return retstr;
	}
	public static String fileexportlog(String filetype, Date fordate, String hashsum, String fileName){
		//Calendar calendar = Calendar.getInstance();
		String curtime =  "";//DateTimeUtils.formatDate(DateTimeUtils.getDateWithoutMilliseconds(calendar.getTime()),DateTimeUtils.NSPCMAIL_DATETIME_FORMAT_STRING);
		String retstr = curtime;
	    retstr = retstr + " " + "export file " + " " + hvost(filetype, fordate,hashsum,fileName) ; 
		return retstr;}
	public static String fileimportlog(String filetype, Date fordate, String hashsum, String fileName){
		Calendar calendar = Calendar.getInstance();
	//	String curtime =  DateTimeUtils.formatDate(DateTimeUtils.getDateWithoutMilliseconds(calendar.getTime()),DateTimeUtils.NSPCMAIL_DATETIME_FORMAT_STRING);
		String retstr = "";//curtime;
		if (fordate==null) fordate = calendar.getTime();
	    retstr = retstr + " " + "import file " + " " + hvost(filetype, fordate,hashsum,fileName) ; 
		return retstr;}
	public static String fileimporterror(String filetype, Date fordate, String hashsum, String fileName){
		Calendar calendar = Calendar.getInstance();
	//	String curtime =  DateTimeUtils.formatDate(DateTimeUtils.getDateWithoutMilliseconds(calendar.getTime()),DateTimeUtils.NSPCMAIL_DATETIME_FORMAT_STRING);
		String retstr = "";//curtime;
		if (fordate==null) fordate = calendar.getTime();
	    retstr = retstr + " " + "error import file " + " " + hvost(filetype, fordate,hashsum,fileName) ; 
		return retstr;}
}
