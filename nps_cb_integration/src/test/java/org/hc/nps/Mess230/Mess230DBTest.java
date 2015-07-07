package org.hc.nps.Mess230;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.hc.nps.calendar.CalendarUtils;
import org.hc.nps.db.DBTestUtils;
import org.hc.jp.db.RunInTransaction;
import org.hc.nps.db.entities.JSMess230det;
import org.hc.nps.db.entities.JSMess230head;
import org.hc.nps.db.mappers.JSMess230detRowMapper;
import org.hc.nps.db.mappers.JSMess230headRowMapper;
import org.hc.nps.mess230.Mess230DB;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-web-context.xml" })

public class Mess230DBTest {
	@Autowired
	private RunInTransaction runInTransaction;
	@Autowired
	private CalendarUtils cu;
	@Value(value = "${EDAUTHOR}")
	private String EDAUTHOR;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private final String SQL_SELECT_REESTRLIST = "SELECT id, Status, BeginProcessingDate, EndProcessingDate,  "
			+ "	ClearingSystemCode,  RegisterItemsQuantity,  RegisterDebetSum,  "
			+ "RegisterCreditSum,  EDNo,  EDDate,  EDAuthor"
			+" FROM reestrhist WHERE status = 'CREATE'";
	private final String SQL_SELECT_REESTRDETAIL = "SELECT CollId, Status, RegisterItemID, Sum, DC, BIC FROM reestrinfo WHERE CollId = ?";
	
	@Autowired
	private Mess230DB mess230db;
	@Test
	public void mess230dbtest() throws Exception{
		Date todate = java.util.Calendar.getInstance().getTime();
		mess230db.doIt();
		List<JSMess230head> reestr = jdbcTemplate.query(SQL_SELECT_REESTRLIST,  new JSMess230headRowMapper());
		assertNotNull(reestr);
		assertEquals(reestr.size(),1);
		assertEquals(reestr.get(0).RegisterCreditSum.longValue(),100L);
		assertEquals(reestr.get(0).RegisterDebetSum.longValue(),100L);
		assertEquals(reestr.get(0).RegisterItemsQuantity.longValue(),2L);
		assertEquals(reestr.get(0).EDNo,"10");
		assertEquals(reestr.get(0).EDAuthor,EDAUTHOR);
		List<JSMess230det> reestrs = jdbcTemplate.query(SQL_SELECT_REESTRDETAIL, new JSMess230detRowMapper(), new Object[] {1L});
		assertNotNull(reestrs);
		assertEquals(reestrs.size(),2);
		assertEquals(reestrs.get(0).Status.toString(),"CREATE");
		assertEquals(reestrs.get(0).RegisterItemID.longValue(),1L);
		String inreestatus = jdbcTemplate.queryForObject("SELECT status FROM inreestrhist", String.class);
		assertEquals(inreestatus,"DONE");
		Long counterid = cu.getRegisterItemIDcounter(todate);
		assertEquals(counterid.longValue(),2L);		
	}
	@Before
	public void before() throws Exception
	{
		DBTestUtils.runSQLInTransaction(runInTransaction, jdbcTemplate, "/CalendarTest.sql");
		DBTestUtils.runSQLInTransaction(runInTransaction, jdbcTemplate, "/Mess230Dbtest.sql");
	}
}
