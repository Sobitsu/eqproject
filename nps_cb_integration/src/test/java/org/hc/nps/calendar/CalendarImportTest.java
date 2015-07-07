package org.hc.nps.calendar;


import static org.junit.Assert.*;

import javax.xml.bind.JAXBException;

import org.hc.nps.db.DBTestUtils;
import org.hc.jp.db.RunInTransaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-web-context.xml" })

public class CalendarImportTest {
	@Autowired
	private RunInTransaction runInTransaction;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private CalendarUpdateReader cur;
	
	@Test

	public void main() throws JAXBException{
		cur.loadCalendar();
		String countinreehist = jdbcTemplate.queryForObject("SELECT isworkdate FROM workcalendar where caldate = '2015-01-24'", String.class);
		assertNotNull(countinreehist);
		assertEquals(countinreehist,"0");
		countinreehist = jdbcTemplate.queryForObject("SELECT isworkdate FROM workcalendar where caldate = '2015-02-23'", String.class);
		assertNotNull(countinreehist);
		assertEquals(countinreehist,"0");

		
	}
	@Before
	public void before() throws Exception
	{
	
		DBTestUtils.runSQLInTransaction(runInTransaction, jdbcTemplate, "/calendarimport.sql");
	}

}
