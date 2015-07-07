package org.hc.nps.inMessage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.hc.nps.biks.BiksDBFLoader;
import org.hc.nps.calendar.CalendarUtils;
import org.hc.nps.db.DBTestUtils;
import org.hc.jp.db.RunInTransaction;
import org.hc.jp.files.FileManager;
import org.hc.nps.files.FileNames;
import org.hc.nps.mess230.Mess230DB;
import org.hc.nps.mess230.Mess230Out;
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

public class fullBPTEST {
	@Value(value = "${fornspc.path}")
	private String dir230name;
	@Value(value = "${fromnspc.path}")
	private String dirinmessname;
	@Autowired
	private RunInTransaction runInTransaction;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static String testfiledir = "testfiles";
	private static String test1filename = "good_workdate.xml";
	private static String test2filename = "good_weekedndate1.xml";
	private static String test3filename = "good_weekedndate2.xml";
	private static String test4filename = "good_weekedndate3.xml";
	private static String test5filename = "good_weekedndate4.xml";
	
	@Autowired
	private InMessageReader imr;
	@Autowired
	private Mess230DB m230db;
	@Autowired
	private Mess230Out m230out;
	@Autowired
	private BiksDBFLoader bnlloader;
	@Autowired
	private FileManager filemanager;
	@Autowired
	private FileNames fn;
	@Autowired
	private CalendarUtils cu;
	private Date todate = java.util.Calendar.getInstance().getTime();

	@Test
	public void fullBPSimpleFiletest() throws Exception {
		File test1file = filemanager.getFile(testfiledir, test1filename);
		File testfiledirf = filemanager.getPathDir(dirinmessname);
		FileUtils.copyFileToDirectory(test1file, testfiledirf);
		bnlloader.loadBiks();
		imr.doIt();
		m230db.doIt();
		m230out.doIt();
		
		
		Long countinreehist = jdbcTemplate.queryForObject("SELECT count(id) FROM inreestrhist", Long.class);
		assertNotNull(countinreehist);
		assertEquals(1L, countinreehist.longValue());

		List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM inreestrhist WHERE id = 1");
		assertNotNull(rows);
		assertEquals(1, rows.size());
		
		Map<String, Object> row = rows.get(0);
		assertNotNull(row);
		assertEquals("DONE", row.get("status"));
		assertEquals(600000L, row.get("SumDt"));
		assertEquals(600000L, row.get("SumKt"));
		assertEquals(4, row.get("ItemCount"));
		assertEquals("2", row.get("ClearingSystemCode"));
		assertEquals(row.get("BeginProcessingDate"),cu.getPrevWorkDate(todate));
		assertEquals(row.get("EndProcessingDate"),cu.getPrevDate(todate));
		countinreehist = jdbcTemplate.queryForObject("SELECT count(id) FROM inreestrinfo", Long.class);
		assertNotNull(countinreehist);
		assertEquals(4L, countinreehist.longValue());
		rows = jdbcTemplate.queryForList("SELECT * FROM inreestrinfo");
		assertNotNull(rows);
		assertEquals(4, rows.size());
		for (Map<String, Object> row1 : rows)
		{
			assertNotNull(row1);
			assertEquals("DONE", row1.get("status"));
			assertEquals(1L, row1.get("collid"));
		}
		countinreehist = jdbcTemplate.queryForObject("SELECT count(id) FROM reestrhist", Long.class);
		assertNotNull(countinreehist);
		assertEquals(1L, countinreehist.longValue());
		rows = jdbcTemplate.queryForList("SELECT * FROM reestrhist WHERE id = 1");
		assertNotNull(rows);
		assertEquals(1, rows.size());
		row = rows.get(0);
		assertNotNull(row);
		assertEquals("EXPORTED", row.get("status"));
		assertEquals(600000L, row.get("RegisterDebetSum"));
		assertEquals(600000L, row.get("RegisterCreditSum"));
		assertEquals(4, row.get("RegisterItemsQuantity"));
		assertEquals("2", row.get("ClearingSystemCode"));
		assertEquals(row.get("BeginProcessingDate"),cu.getPrevWorkDate(todate));
		assertEquals(row.get("EndProcessingDate"),cu.getPrevDate(todate));
		assertNotNull(row.get("EDNo"));
		assertNotNull(row.get("EDDate"));
		assertNotNull(row.get("EDAuthor"));
		File[] fnds = filemanager.getListFiles(filemanager.getPathDir(dir230name));
		for (File f: fnds) {
			assertEquals(f.getName(),row.get("falename"));
		}
		countinreehist = jdbcTemplate.queryForObject("SELECT count(id) FROM reestrinfo", Long.class);
		assertNotNull(countinreehist);
		assertEquals(4L, countinreehist.longValue());
		rows = jdbcTemplate.queryForList("SELECT * FROM reestrinfo");
		assertNotNull(rows);
		assertEquals(4, rows.size());
		for (Map<String, Object> row1 : rows)
		{
			assertNotNull(row1);
			assertEquals("CREATE", row1.get("status"));
			assertEquals(1L, row1.get("collid"));
		}		
		Long result;
		result = cu.getRegisterItemIDcounter(todate);
		assertEquals(result.longValue(),4L);
	}

	@Test
	public void fullBPWeekendFiletest() throws Exception {
		File test2file = filemanager.getFile(testfiledir, test2filename);
		File test3file = filemanager.getFile(testfiledir, test3filename);
		File testfiledirf = filemanager.getPathDir(dirinmessname);
		FileUtils.copyFileToDirectory(test2file, testfiledirf);
		FileUtils.copyFileToDirectory(test3file, testfiledirf);
		bnlloader.loadBiks();
		imr.doIt();
		m230db.doIt();
		m230out.doIt();


		Long countinreehist = jdbcTemplate.queryForObject("SELECT count(id) FROM inreestrhist", Long.class);
		assertNotNull(countinreehist);
		assertEquals(1L, countinreehist.longValue());

		List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM inreestrhist WHERE id = 1");
		assertNotNull(rows);
		assertEquals(1, rows.size());
		
		Map<String, Object> row = rows.get(0);
		assertNotNull(row);
		assertEquals("DONE", row.get("status"));
		assertEquals(1200000L, row.get("SumDt"));
		assertEquals(1200000L, row.get("SumKt"));
		assertEquals(8, row.get("ItemCount"));
		assertEquals("2", row.get("ClearingSystemCode"));
		assertEquals(row.get("BeginProcessingDate"),cu.getPrevWorkDate(todate));
		assertEquals(row.get("EndProcessingDate"),cu.getPrevDate(todate));
		countinreehist = jdbcTemplate.queryForObject("SELECT count(id) FROM inreestrinfo", Long.class);
		assertNotNull(countinreehist);
		assertEquals(8L, countinreehist.longValue());
		rows = jdbcTemplate.queryForList("SELECT * FROM inreestrinfo");
		assertNotNull(rows);
		assertEquals(8, rows.size());
		for (Map<String, Object> row1 : rows)
		{
			assertNotNull(row1);
			assertEquals("DONE", row1.get("status"));
			assertEquals(1L, row1.get("collid"));
		}
		countinreehist = jdbcTemplate.queryForObject("SELECT count(id) FROM reestrhist", Long.class);
		assertNotNull(countinreehist);
		assertEquals(1L, countinreehist.longValue());
		rows = jdbcTemplate.queryForList("SELECT * FROM reestrhist WHERE id = 1");
		assertNotNull(rows);
		assertEquals(1, rows.size());
		row = rows.get(0);
		assertNotNull(row);
		assertEquals("EXPORTED", row.get("status"));
		assertEquals(1200000L, row.get("RegisterDebetSum"));
		assertEquals(1200000L, row.get("RegisterCreditSum"));
		assertEquals(8, row.get("RegisterItemsQuantity"));
		assertEquals("2", row.get("ClearingSystemCode"));
		assertEquals(row.get("BeginProcessingDate"),cu.getPrevWorkDate(todate));
		assertEquals(row.get("EndProcessingDate"),cu.getPrevDate(todate));
		assertNotNull(row.get("EDNo"));
		assertNotNull(row.get("EDDate"));
		assertNotNull(row.get("EDAuthor"));
		File[] fnds = filemanager.getListFiles(filemanager.getPathDir(dir230name));
		for (File f: fnds) {
			assertEquals(f.getName(),row.get("falename"));
		}
		countinreehist = jdbcTemplate.queryForObject("SELECT count(id) FROM reestrinfo", Long.class);
		assertNotNull(countinreehist);
		assertEquals(8L, countinreehist.longValue());
		rows = jdbcTemplate.queryForList("SELECT * FROM reestrinfo");
		assertNotNull(rows);
		assertEquals(8, rows.size());
		for (Map<String, Object> row1 : rows)
		{
			assertNotNull(row1);
			assertEquals("CREATE", row1.get("status"));
			assertEquals(1L, row1.get("collid"));
		}		
		Long result;
		result = cu.getRegisterItemIDcounter(todate);
		assertEquals(result.longValue(),8L);
	}

	@Test
	public void fullBPWeekendFileGrouptest() throws Exception {
		File test2file = filemanager.getFile(testfiledir, test4filename);
		File test3file = filemanager.getFile(testfiledir, test5filename);
		File testfiledirf = filemanager.getPathDir(dirinmessname);
		FileUtils.copyFileToDirectory(test2file, testfiledirf);
		FileUtils.copyFileToDirectory(test3file, testfiledirf);
		bnlloader.loadBiks();
		imr.doIt();
		m230db.doIt();
		m230out.doIt();
	
		Long countinreehist = jdbcTemplate.queryForObject("SELECT count(id) FROM inreestrhist", Long.class);
		assertNotNull(countinreehist);
		assertEquals(1L, countinreehist.longValue());

		List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM inreestrhist WHERE id = 1");
		assertNotNull(rows);
		assertEquals(1, rows.size());
		
		Map<String, Object> row = rows.get(0);
		assertNotNull(row);
		assertEquals("DONE", row.get("status"));
		assertEquals(1100000L, row.get("SumDt"));
		assertEquals(1100000L, row.get("SumKt"));
		assertEquals(7, row.get("ItemCount"));
		assertEquals("2", row.get("ClearingSystemCode"));
		assertEquals(row.get("BeginProcessingDate"),cu.getPrevWorkDate(todate));
		assertEquals(row.get("EndProcessingDate"),cu.getPrevDate(todate));
		countinreehist = jdbcTemplate.queryForObject("SELECT count(id) FROM inreestrinfo", Long.class);
		assertNotNull(countinreehist);
		assertEquals(9L, countinreehist.longValue());
		rows = jdbcTemplate.queryForList("SELECT * FROM inreestrinfo");
		assertNotNull(rows);
		assertEquals(9, rows.size());
		Long grcount=0L;
		for (Map<String, Object> row1 : rows)
		{
			assertNotNull(row1);
			if ( row1.get("status").toString().contentEquals("GROUPED")) {
				grcount=grcount+1L;
				};
			assertNotNull(row1.get("status"));
			assertEquals(1L, row1.get("collid"));
		}
		assertEquals(grcount.longValue(),2L);
		countinreehist = jdbcTemplate.queryForObject("SELECT count(id) FROM reestrhist", Long.class);
		assertNotNull(countinreehist);
		assertEquals(1L, countinreehist.longValue());
		rows = jdbcTemplate.queryForList("SELECT * FROM reestrhist WHERE id = 1");
		assertNotNull(rows);
		assertEquals(1, rows.size());
		row = rows.get(0);
		assertNotNull(row);
		assertEquals("EXPORTED", row.get("status"));
		assertEquals(1100000L, row.get("RegisterDebetSum"));
		assertEquals(1100000L, row.get("RegisterCreditSum"));
		assertEquals(7, row.get("RegisterItemsQuantity"));
		assertEquals("2", row.get("ClearingSystemCode"));
		assertEquals(row.get("BeginProcessingDate"),cu.getPrevWorkDate(todate));
		assertEquals(row.get("EndProcessingDate"),cu.getPrevDate(todate));
		assertNotNull(row.get("EDNo"));
		assertNotNull(row.get("EDDate"));
		assertNotNull(row.get("EDAuthor"));
		File[] fnds = filemanager.getListFiles(filemanager.getPathDir(dir230name));
		for (File f: fnds) {
			assertEquals(f.getName(),row.get("falename"));
		}
		countinreehist = jdbcTemplate.queryForObject("SELECT count(id) FROM reestrinfo", Long.class);
		assertNotNull(countinreehist);
		assertEquals(7L, countinreehist.longValue());
		rows = jdbcTemplate.queryForList("SELECT * FROM reestrinfo");
		assertNotNull(rows);
		assertEquals(7, rows.size());
		for (Map<String, Object> row1 : rows)
		{
			assertNotNull(row1);
			assertEquals("CREATE", row1.get("status"));
			assertEquals(1L, row1.get("collid"));
		}		
		Long result;
		result = cu.getRegisterItemIDcounter(todate);
		assertEquals(result.longValue(),7L);
	}
	
	@Before
	public void before() throws Exception
	{
		DBTestUtils.runSQLInTransaction(runInTransaction, jdbcTemplate, "/fullbptest.sql");
		DBTestUtils.runSQLInTransaction(runInTransaction, jdbcTemplate, "/CalendarTest.sql");
		filemanager.removeAllFiles(dirinmessname);
		filemanager.removeAllFiles(dirinmessname+"/arc");
		filemanager.removeAllFiles(dirinmessname+"/tmp");
		filemanager.removeAllFiles(dir230name);
		filemanager.removeAllFiles(dir230name+"/arc");
		filemanager.removeAllFiles(dir230name+"/tmp");
	}	
}
