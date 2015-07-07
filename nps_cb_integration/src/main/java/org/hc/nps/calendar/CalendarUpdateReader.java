package org.hc.nps.calendar;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.hc.nps.biks.BiksDBFLoader;
import org.hc.nps.files.FileCheckSum;
import org.hc.jp.files.FileManager;
import org.hc.jp.utils.DateTimeUtils;
import org.hc.nps.utils.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CalendarUpdateReader{
	private static final Logger LOG = LoggerFactory.getLogger(BiksDBFLoader.class);
	private static String FILE_NAME_CALUPDATE = "calendar.xml";
	private static String SQLUPDATECALENDAR ="UPDATE workcalendar SET isworkdate = ? where caldate = ?";
	private static String SQLINSERTCALENDAR ="INSERT workcalendar(caldate,isworkdate) values(?,?)";
	
	@Autowired
	private FileManager fileManager;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Transactional(propagation = Propagation.REQUIRES_NEW)

	public void loadCalendar()
	{
		loadCalendar(FILE_NAME_CALUPDATE);
	}
	private File getFile(String fileName)
	{
		return fileManager.getFile(fileName);
	}
	private void loadCalendar(String fileName) {
		File file = getFile(fileName);
		String hashsum = null;
		if(file == null)
			return;

		try
		{
			hashsum= FileCheckSum.md5checkSum(file);	
			FileInputStream fis = new FileInputStream(file);
	        JAXBContext context = JAXBContext.newInstance(JSCalendarImport.class);
	        Marshaller marshaller =  context.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
	        Unmarshaller unmarshaller = context.createUnmarshaller();
	        JSCalendarImport groupCopy = (JSCalendarImport) unmarshaller.unmarshal(fis);
	        if (groupCopy.method.contentEquals("CREATE") & (groupCopy.year!=null)) cretecalendat(groupCopy);
			if (!groupCopy.datesInfo.isEmpty() ) editcalendar(groupCopy);
	} catch (JAXBException exception) {
		LOG.error("FATAL Error. Incorrect file format " + file.getName(), exception);
	} catch (IOException exception) {
		LOG.error(LogUtil.fileimporterror(FILE_NAME_CALUPDATE, null, hashsum, file.getName()), exception);
	}
	}

@SuppressWarnings("deprecation")
private void cretecalendat(JSCalendarImport groupCopy) {
	//Calendar c = Calendar.getInstance();
	Calendar c = Calendar.getInstance();
	Integer dayofweek;
	String isworkdate = null;
	c.set(groupCopy.year.intValue() , 0, 1);
	Date curdate = c.getTime();
	while (curdate.getYear()+1900 ==groupCopy.year.intValue()){ 
		dayofweek = DateTimeUtils.getDayOfWeek(curdate);
		if (dayofweek.intValue()>=6) isworkdate = "0";
		if (dayofweek.intValue()<6) isworkdate = "1";
		jdbcTemplate.update(SQLINSERTCALENDAR, new Object[] { DateTimeUtils.formatDate(curdate), isworkdate});
		curdate = DateTimeUtils.offsetDate(curdate, Calendar.DATE, 1);
		}
	}

private void editcalendar(JSCalendarImport groupCopy)	{
	for (JSCalendarImportDetail cals: groupCopy.datesInfo) {
		String datastring = DateTimeUtils.formatDate(cals.date);
		String iswd = cals.isWork;
		jdbcTemplate.update(SQLUPDATECALENDAR, new Object[] {iswd , datastring });
	}
}
}
