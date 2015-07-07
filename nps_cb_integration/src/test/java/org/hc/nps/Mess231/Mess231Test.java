package org.hc.nps.Mess231;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.hc.nps.biks.BiksDBFLoader;
import org.hc.nps.calendar.CalendarUtils;
import org.hc.nps.db.DBTestUtils;
import org.hc.jp.db.RunInTransaction;
import org.hc.jp.files.FileManager;
import org.hc.nps.files.FileNames;
import org.hc.nps.inMessage.InMessageReader;
import org.hc.nps.mess230.Mess230DB;
import org.hc.nps.mess230.Mess230Out;
import org.hc.nps.message231.Mess231Reader;
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

public class Mess231Test {
	@Value(value = "${fornspc.path}")
	private String dir230name;
	@Value(value = "${fromnspc.path}")
	private String dirinmessname;
	@Autowired
	Mess231Reader m231r;
	@Value(value = "${repcb231.path}")
	private String dir231name;
	@Autowired
	private RunInTransaction runInTransaction;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static String testfiledir = "testfiles";
	private static String test231filename = "ED231.xml";
	private static String test230filename = "good_workdate.xml";
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
		File test231file = filemanager.getFile(testfiledir, test231filename);
		File testfiledir231 = filemanager.getPathDir(dir231name);
		FileUtils.copyFileToDirectory(test231file, testfiledir231);
		m231r.doIt();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM reestrinfo");
		assertNotNull(rows);
		assertEquals(4, rows.size());
		Map<String, Object> row = rows.get(0);
		assertNotNull(row);
		assertEquals("DONECONTROLCB", row.get("status"));
		row = rows.get(1);
		assertNotNull(row);
		assertEquals("PAYDCB", row.get("status"));
		row = rows.get(2);
		assertNotNull(row);
		assertEquals("WAITMONEYCB", row.get("status"));
		row = rows.get(3);
		assertNotNull(row);
		assertEquals("REFUSECB", row.get("status"));
		Long countinreehist = jdbcTemplate.queryForObject("SELECT count(id) FROM reestrinfoed321", Long.class);
		assertNotNull(countinreehist);
		assertEquals(4L, countinreehist.longValue());
		countinreehist = jdbcTemplate.queryForObject("SELECT count(id) FROM reestrhisted231", Long.class);
		assertNotNull(countinreehist);
		assertEquals(1L, countinreehist.longValue());
		List<Map<String, Object>> rows1 = jdbcTemplate.queryForList("SELECT * FROM reestrhisted231");
		assertNotNull(rows1);
		assertEquals(1, rows1.size());
		row = rows1.get(0);
		assertNotNull(row);
		assertEquals("02", row.get("ClearingSystemCode"));
		assertEquals(4, row.get("RegisterItemsQuantity"));
		assertEquals(test231filename, row.get("falename"));
		assertEquals(2, row.get("RegisterMode"));
		List<Map<String, Object>> rows2 = jdbcTemplate.queryForList("SELECT * FROM reestrinfoed321");
		assertNotNull(rows2);
		assertEquals(4, rows2.size());
	}
	
	@Before
	public void before() throws Exception
	{
		DBTestUtils.runSQLInTransaction(runInTransaction, jdbcTemplate, "/CalendarTest.sql");
		DBTestUtils.runSQLInTransaction(runInTransaction, jdbcTemplate, "/fullbptest.sql");
		filemanager.removeAllFiles(dir231name);
		filemanager.removeAllFiles(dir231name+"/arc");
		filemanager.removeAllFiles(dir231name+"/tmp");
		filemanager.removeAllFiles(dirinmessname);
		filemanager.removeAllFiles(dirinmessname+"/arc");
		filemanager.removeAllFiles(dirinmessname+"/tmp");
		filemanager.removeAllFiles(dir230name);
		filemanager.removeAllFiles(dir230name+"/arc");
		filemanager.removeAllFiles(dir230name+"/tmp");

	}		
}
