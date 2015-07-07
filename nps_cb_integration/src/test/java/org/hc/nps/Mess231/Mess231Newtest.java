package org.hc.nps.Mess231;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.hc.nps.calendar.CalendarUtils;
import org.hc.nps.db.DBTestUtils;
import org.hc.jp.db.RunInTransaction;
import org.hc.jp.files.FileManager;
import org.hc.nps.files.FileNames;
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

public class Mess231Newtest {
	@Autowired
	Mess231Reader m231r;
	@Value(value = "${repcb231.path}")
	private String dir231name;
	@Autowired
	private RunInTransaction runInTransaction;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private static String testfiledir = "testfiles";
	private static String test231filename = "ED231-3.xml";

	@Autowired
	private FileManager filemanager;
	@Autowired
	private FileNames fn;
	@Autowired
	private CalendarUtils cu;

	
	@Test
	public void readertest() throws Exception{
		File test231file = filemanager.getFile(testfiledir, test231filename);
		File testfiledir231 = filemanager.getPathDir(dir231name);
		FileUtils.copyFileToDirectory(test231file, testfiledir231);
		m231r.doIt();
	}
	
	@Before
	public void before() throws Exception
	{
		DBTestUtils.runSQLInTransaction(runInTransaction, jdbcTemplate, "/CalendarTest.sql");
		DBTestUtils.runSQLInTransaction(runInTransaction, jdbcTemplate, "/fullbptest.sql");
		filemanager.removeAllFiles(dir231name);
		filemanager.removeAllFiles(dir231name+"/arc");
		filemanager.removeAllFiles(dir231name+"/tmp");
	}		
}
