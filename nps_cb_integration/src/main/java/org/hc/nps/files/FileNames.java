package org.hc.nps.files;

/**
 * @author dale
 *
 */


import java.util.Date;
import org.hc.nps.calendar.CalendarUtils;
import org.hc.jp.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FileNames {
	@Autowired
	private JdbcTemplate jdbcTemplate;;
	
	@Value(value = "${mail.sender}")
	private String idmailsender;

	@Value(value = "${mail.reciver}")
	private String idmailreceiver;

	@Value(value = "${mail.230.type.visa}")
	private String mail230typevisa;

	@Value(value = "${mail.230.type.mast}")
	private String mail230typemast;

	
	@Value(value = "${mail.typeX}")
	private String mailtypex;
	private static String MESSED230 = "ED230";

	@Autowired
	private CalendarUtils cu;
	
private String getfilenameed230(String platsys, Date todate){
	String mtype = mail230typemast;
	if (todate==null) todate = java.util.Calendar.getInstance().getTime();
	if (platsys.contentEquals("1")) mtype = mail230typevisa;
	if (platsys.contentEquals("2")) mtype = mail230typemast;
	String result = idmailsender +"_"+ idmailreceiver +"_"+ mtype +"_"+ mailtypex;
	result = result +"_"+  DateTimeUtils.formatDate(todate, DateTimeUtils.NSPCMAIL_DATE_FORMAT_STRING)+"_" + cu.getcountfile(platsys, todate) +".xml";
	return result;	
}
public String getfilename(String messtype, String platsys, Date todate){
	String result = "unknowntype.dat";
		if (messtype.contains(MESSED230) == true) {
			result = getfilenameed230(platsys, todate);
		}
	return result;
}
}
