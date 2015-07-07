package org.hc.nps.inMessage201;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.hc.jp.db.RunInTransaction;
import org.hc.jp.files.FileManager;
import org.hc.nps.biks.BiksDBFLoader;
import org.hc.nps.calendar.CalendarUtils;
import org.hc.nps.db.DBTestUtils;
import org.hc.nps.files.FileNames;
import org.hc.nps.inMessage.InMessageReader;
import org.hc.nps.mess230.Mess230DB;
import org.hc.nps.mess230.Mess230Out;
import org.hc.nps.message201.inMessage201Reader;
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
public class Mess201Test {
	@Value(value = "${fornspc.path}")
	private String dir230name;
	@Value(value = "${fromnspc.path}")
	private String dirinmessname;
	@Autowired
	inMessage201Reader m201r;
	@Value(value = "${repcb201.path}")
	private String dir201name;
	@Autowired
	private RunInTransaction runInTransaction;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private static String testfiledir = "testfiles";
	private static String test201filename = "ED201.xml";
	private static String test230filename = "good_workdate.xml";
	private static String test230filename1 = "good_weekedndate1.xml";
	private static String test230filename2 = "good_weekedndate2.xml";
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
	@Test
	public void readertest() throws Exception{
		
		File test1file = filemanager.getFile(testfiledir, test230filename);
		File testfiledirf = filemanager.getPathDir(dirinmessname);
		FileUtils.copyFileToDirectory(test1file, testfiledirf);
		bnlloader.loadBiks();
		imr.doIt();
		m230db.doIt();
		m230out.doIt();
		
		test1file = filemanager.getFile(testfiledir, test230filename1);
		testfiledirf = filemanager.getPathDir(dirinmessname);
		FileUtils.copyFileToDirectory(test1file, testfiledirf);
		imr.doIt();
		m230db.doIt();
		m230out.doIt();
		
		test1file = filemanager.getFile(testfiledir, test230filename2);
		testfiledirf = filemanager.getPathDir(dirinmessname);
		FileUtils.copyFileToDirectory(test1file, testfiledirf);
		imr.doIt();
		m230db.doIt();
		m230out.doIt();
		
		File test201file = filemanager.getFile(testfiledir, test201filename);
		File testfiledir201 = filemanager.getPathDir(dir201name);
		FileUtils.copyFileToDirectory(test201file, testfiledir201);
		m201r.doIt();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM reestrhist");
		assertNotNull(rows);
		assertEquals(3, rows.size());
		Map<String, Object> row = rows.get(0);
		assertNotNull(row);
		assertEquals("EXPORTED", row.get("status"));
		row = rows.get(1);
		assertNotNull(row);
		assertEquals("EXPORTED", row.get("status"));
		row = rows.get(2);
		assertNotNull(row);
		assertEquals("ERRORREESTR", row.get("status"));
		
		Long counted201hist = jdbcTemplate.queryForObject("SELECT count(id) FROM ed201hist", Long.class);
		assertNotNull(counted201hist);
		assertEquals(1L, counted201hist.longValue());
		
		List<Map<String, Object>> rows1 = jdbcTemplate.queryForList("SELECT * FROM ed201hist");
		assertNotNull(rows1);
		assertEquals(1, rows1.size());
		row = rows1.get(0);
		assertNotNull(row);
		assertEquals(1002150, row.get("EDNo"));
		assertEquals("4583001999", row.get("EDAuthor"));
		assertEquals("4533333333", row.get("EDReceiver"));
		assertEquals("2870", row.get("CtrlCode"));
		assertEquals("Реестр клиринговых платежей за указанный период обработки уже успешно принят", row.get("Annotation"));
		assertEquals("Реестр клиринговых платежей за указанный период обработки уже успешно принят Банком России", row.get("ErrorDiagnostic"));
		assertEquals(12L, row.get("EDRefID_EDNo"));
		assertEquals("4533333333", row.get("EDRefID_EDAuthor"));
		assertEquals(test201filename, row.get("filename"));
	}
	
	
	@Before
	public void before() throws Exception
	{
		DBTestUtils.runSQLInTransaction(runInTransaction, jdbcTemplate, "/mess201test.sql");
		DBTestUtils.runSQLInTransaction(runInTransaction, jdbcTemplate, "/CalendarTest.sql");
		DBTestUtils.runSQLInTransaction(runInTransaction, jdbcTemplate, "/fullbptest.sql");
		filemanager.removeAllFiles(dir201name);
		filemanager.removeAllFiles(dir201name+"/arc");
		filemanager.removeAllFiles(dir201name+"/tmp");
		filemanager.removeAllFiles(dirinmessname);
		filemanager.removeAllFiles(dirinmessname+"/arc");
		filemanager.removeAllFiles(dirinmessname+"/tmp");
		filemanager.removeAllFiles(dir230name);
		filemanager.removeAllFiles(dir230name+"/arc");
		filemanager.removeAllFiles(dir230name+"/tmp");

	}	
}
