package org.hc.nps.calendar;

import static org.junit.Assert.*;

import java.util.Date;

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

public class CalendarUtilsTest {
	@Autowired
	private RunInTransaction runInTransaction;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private CalendarUtils cu;
	private Date todate = java.util.Calendar.getInstance().getTime();

	@Test
	public void getPrevWorkDateTest() {
		Date result;
		todate = DateTimeUtils.parseDate("16.01.2015", "dd.MM.yyyy");
		result = cu.getPrevWorkDate(todate);
		assertEquals(DateTimeUtils.compareTo(result, DateTimeUtils.parseDate("15.01.2015", "dd.MM.yyyy")),0);
		todate = DateTimeUtils.parseDate("15.01.2015", "dd.MM.yyyy");
		result = cu.getPrevWorkDate(todate);
		assertEquals(DateTimeUtils.compareTo(result, DateTimeUtils.parseDate("31.12.2014", "dd.MM.yyyy")),0);
	}
	
	@Test
	public void isDateWorkTest() {
		Boolean result;
		todate = DateTimeUtils.parseDate("17.01.2015", "dd.MM.yyyy");
		result = cu.isDateWork(todate);
		assertEquals(result,false);
		todate = DateTimeUtils.parseDate("16.01.2015", "dd.MM.yyyy");
		result = cu.isDateWork(todate);
		assertEquals(result,true);
		todate = DateTimeUtils.parseDate("01.01.2015", "dd.MM.yyyy");
		result = cu.isDateWork(todate);
		assertEquals(result,false);
	}
	
	@Test
	public void getPrevDateTest() {
		Date result;
		todate = DateTimeUtils.parseDate("16.01.2015", "dd.MM.yyyy");
		result = cu.getPrevDate(todate);
		assertEquals(DateTimeUtils.compareTo(result, DateTimeUtils.parseDate("15.01.2015", "dd.MM.yyyy")),0);
		todate = DateTimeUtils.parseDate("15.01.2015", "dd.MM.yyyy");
		result = cu.getPrevDate(todate);
		assertEquals(DateTimeUtils.compareTo(result, DateTimeUtils.parseDate("14.01.2015", "dd.MM.yyyy")),0);		
		todate = DateTimeUtils.parseDate("02.01.2015", "dd.MM.yyyy");
		result = cu.getPrevDate(todate);
		assertEquals(DateTimeUtils.compareTo(result, DateTimeUtils.parseDate("01.01.2015", "dd.MM.yyyy")),0);
	}
	
	@Test
	public void getRegisterItemIDcounterTest() {
		Long result;
		result = cu.getRegisterItemIDcounter(todate);
		assertEquals(result.longValue(),0L);
		todate = DateTimeUtils.parseDate("15.01.2015", "dd.MM.yyyy");
		result = cu.getRegisterItemIDcounter(todate);
		assertEquals(result.longValue(),3L);		
	}
	
	@Test
	public void setRegisterItemIDcounterTest() {
		Long result;
		todate = DateTimeUtils.parseDate("16.01.2015", "dd.MM.yyyy");
		cu.setRegisterItemIDcounter(todate, 10L);
		result = cu.getRegisterItemIDcounter(todate);
		assertEquals(result.longValue(),10L);
		todate = DateTimeUtils.parseDate("15.01.2015", "dd.MM.yyyy");
		result = cu.getRegisterItemIDcounter(todate);
		assertEquals(result.longValue(),3L);		
	}

	@Before
	public void before() throws Exception
	{
		DBTestUtils.runSQLInTransaction(runInTransaction, jdbcTemplate, "/CalendarTest.sql");
	}


}
