package org.hc.nps.calendar;

import java.util.Date;
import java.util.List;
import org.hc.jp.db.mappers.LongMapper;

import org.hc.nps.db.entities.FileDateCounter;
import org.hc.nps.db.entities.WorkDate;
import org.hc.nps.db.mappers.FileDateCounterMapper;
import org.hc.nps.db.mappers.WorkDateMapper;
import org.hc.jp.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CalendarUtils {
	private static final String SQLGETCALENDARDATE = "SELECT caldate, isworkdate, pervworkdate FROM workcalendar WHERE caldate = ?";
	private static final String SQLLOOKCALENDARDATE = "SELECT caldate, isworkdate, pervworkdate FROM workcalendar WHERE caldate < ? order by caldate desc";
	private static final String SQLSETPREVWORKDATE = "UPDATE workcalendar SET pervworkdate = ? WHERE caldate = ? ";
	private static final String SQLGETIDCOUNTERDATE = "SELECT registeritemcount FROM workcalendar WHERE caldate = ?";
	private static final String SQLSETCOUNTERSDATE = "UPDATE workcalendar SET registeritemcount = ? WHERE caldate = ? ";
	private static final String VISAUPDATECOUNTER = "UPDATE workcalendar SET visafilecount = IF(visafilecount is null,0,visafilecount) + 1 WHERE caldate = ?";
	private static final String MASTUPDATECOUNTER = "UPDATE workcalendar SET mastfilecount = IF(mastfilecount is null,0,mastfilecount) + 1 WHERE caldate = ?";
	private static final String SQLGETFILECOUNTER = "SELECT visafilecount, mastfilecount FROM workcalendar WHERE caldate = ?";
	private static final String SQLGETEDNCOUNTERDATE = "SELECT IF(edncount is null,10,edncount) FROM workcalendar WHERE caldate = ?";
	private static final String SQLSETENDCOUNTERSDATE = "UPDATE workcalendar SET edncount =  IF(edncount is null,10,edncount) + 1 WHERE caldate = ? ";
	

	
	@Autowired
	private JdbcTemplate jdbctemplate;
	

public Date getPrevWorkDate( Date curdate){
		Date result = null;
		Date calcdate = curdate;
		curdate = getPrevDate(curdate);
		List<WorkDate> workdates = jdbctemplate.query(SQLGETCALENDARDATE, new WorkDateMapper(),  new Object[] { DateTimeUtils.formatDate(curdate) });
		for (WorkDate workd : workdates)
		{
			if (workd.isworkdate.contentEquals("1")) result = curdate;
			if (workd.isworkdate.contentEquals("0") & workd.pervworkdate != null ) result = workd.pervworkdate;
		}
		if (result == null) {
			result = getPrevWorkDate(curdate);
		}
		if (result != null) setPrevWorkDate(result,calcdate);
		return result;
	}
public Boolean isDateWork(Date curdate)
{
	Boolean result = false;
	List<WorkDate> workdates = jdbctemplate.query(SQLGETCALENDARDATE, new WorkDateMapper(),  new Object[] { DateTimeUtils.formatDate(curdate) });
	for (WorkDate workd : workdates)
	{
		if (workd.isworkdate.contentEquals("1")) result = true;
		if (workd.isworkdate.contentEquals("0")) result = false;
	}
	return result;
	}
public Date getPrevDate(Date curdate) {
	Date result = curdate;
	List<WorkDate> workdates = jdbctemplate.query(SQLLOOKCALENDARDATE, new WorkDateMapper(),  new Object[] { DateTimeUtils.formatDate(curdate) });
	for (WorkDate workd : workdates)
	{
				result = workd.caldate;
				break;
	}			
	return result;
}	

public Long getRegisterItemIDcounter(Date curdate){
	Long result = 0L;
	List<Long> ids = jdbctemplate.query(SQLGETIDCOUNTERDATE, new LongMapper(),  new Object[] { DateTimeUtils.formatDate(curdate) });
	for (Long id:ids) {
		result = id;
		continue;
	}
	return result;
	}
public String getcountfile(String platsys, Date todate)
{ String result = "01";
	if (todate == null) todate =  (Date) java.util.Calendar.getInstance().getTime();
		List<FileDateCounter>  filecounts = jdbctemplate.query(SQLGETFILECOUNTER, new FileDateCounterMapper(),  new Object[] { DateTimeUtils.formatDate(todate) });
		for (FileDateCounter dfcount : filecounts) {
			if (dfcount.visacount.longValue() == 0L) setfilescounter("1",todate);
			if (dfcount.mastcount.longValue() == 0L) setfilescounter("2",todate);
			if (platsys.contentEquals("1")) result = dfcount.visacount.toString();
			if (platsys.contentEquals("2")) result = dfcount.mastcount.toString();		
		}
	if (result.equals("0")) result = "1";
	if (result.length()==1) result= "0"+result;
	return result;
}

public Long getEDNcounter(Date curdate){
	Long result = jdbctemplate.queryForObject(SQLGETEDNCOUNTERDATE, Long.class,  new Object[] { DateTimeUtils.formatDate(curdate) });
	
/*	List<Long> ids = jdbctemplate.query(SQLGETEDNCOUNTERDATE, new LongMapper(),  new Object[] { DateTimeUtils.formatDate(curdate) });
	for (Long id:ids) {
		result = id;
		continue;
	}*/
	return result;
	}

@Transactional(propagation = Propagation.REQUIRED)
public void setEDNcounter(Date curdate){
	jdbctemplate.update(SQLSETENDCOUNTERSDATE, new Object[] { DateTimeUtils.formatDate(curdate)});
}

@Transactional(propagation = Propagation.REQUIRED)
public void setRegisterItemIDcounter(Date curdate, Long id){
	jdbctemplate.update(SQLSETCOUNTERSDATE, new Object[] { id, DateTimeUtils.formatDate(curdate)});
}
@Transactional(propagation = Propagation.REQUIRED)//Делаем в транзакции
public void setfilescounter(String platsys, Date todate)
{
	String sqlstr = null;
	if (platsys.contentEquals("1")) sqlstr = VISAUPDATECOUNTER;
	if (platsys.contentEquals("2")) sqlstr = MASTUPDATECOUNTER;		
	jdbctemplate.update(sqlstr, new Object[] {  DateTimeUtils.formatDate(todate)});
}
@Transactional(propagation = Propagation.REQUIRED)
private void setPrevWorkDate(Date prevdate,Date curdate) {
		jdbctemplate.update(SQLSETPREVWORKDATE, new Object[] { DateTimeUtils.formatDate(prevdate), DateTimeUtils.formatDate(curdate)});
	}
}
