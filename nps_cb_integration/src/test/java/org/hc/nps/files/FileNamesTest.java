package org.hc.nps.files;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.hc.nps.calendar.CalendarUtils;
import org.hc.nps.db.DBTestUtils;
import org.hc.jp.db.RunInTransaction;
import org.hc.jp.utils.DateTimeUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-web-context.xml" })

public class FileNamesTest {
	@Autowired
	private RunInTransaction runInTransaction;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private CalendarUtils cu;
	@Autowired
	private FileNames fileNames;
	private Date todate = java.util.Calendar.getInstance().getTime();
	@Test
	public void getcountfiletest()
	{ 
		String result;
		result = cu.getcountfile("1", todate);
		assertEquals(result.equals("01"),true);
		result = cu.getcountfile("2", todate);
		assertEquals(result.equals("01"),true);
		todate = DateTimeUtils.parseDate("15.01.2015", "dd.MM.yyyy");
		result = cu.getcountfile("1", todate);
		assertEquals(result.equals("02"),true);
		result = cu.getcountfile("2", todate);
		assertEquals(result.equals("01"),true);
	}
	@Test
	public void setfilescountertest()
	{
		String result;
		todate = DateTimeUtils.parseDate("16.01.2015", "dd.MM.yyyy");
		result = cu.getcountfile("1", todate);
		assertEquals(result.equals("01"),true);
		cu.setfilescounter("1", todate);
		result = cu.getcountfile("1", todate);
		assertEquals(result.equals("02"),true);
		todate = DateTimeUtils.parseDate("15.01.2015", "dd.MM.yyyy");
		result = cu.getcountfile("1", todate);
		assertEquals(result.equals("02"),true);
		cu.setfilescounter("1", todate);
		result = cu.getcountfile("1", todate);
		assertEquals(result.equals("03"),true);

	}
	@Test	
	public void getfilenametest(){
		String filename;
		todate = DateTimeUtils.parseDate("15.01.2015", "dd.MM.yyyy");
		filename = fileNames.getfilename("ED230", "1", todate);
		assertEquals(filename.equals("SSSS_CBCL_NETV_T_20150115_02.xml"),true);
	}

	@Before
	public void before() throws Exception
	{
		DBTestUtils.runSQLInTransaction(runInTransaction, jdbcTemplate, "/CalendarTest.sql");
	}

}
