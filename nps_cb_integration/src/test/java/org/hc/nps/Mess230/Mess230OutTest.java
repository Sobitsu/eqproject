package org.hc.nps.Mess230;

import static org.junit.Assert.*;

import java.io.File;

import org.hc.nps.calendar.CalendarUtils;
import org.hc.nps.db.DBTestUtils;
import org.hc.jp.db.RunInTransaction;
import org.hc.jp.files.FileManager;
import org.hc.nps.files.FileNames;
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

public class Mess230OutTest {
	@Autowired
	private Mess230Out mess230out;

	@Value(value = "${fornspc.path}")
	private String dirname; 

	@Autowired
	private RunInTransaction runInTransaction;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private FileManager filemanager;
	@Autowired
	private FileNames filenames;

	@Autowired
	private CalendarUtils cu;
	@Value(value = "${fornspc.path}")
	private String dir230name;
	
	@Test
	public void Mess230XmlCreate() throws Exception
	{
		File outfledir = filemanager.getPathDir(dirname);
		File tmpfdir = filemanager.getPathDir(dirname+"/tmp");
		filemanager.removeAllFiles(dirname);
		filemanager.removeAllFiles(dirname+"/tmp");
		File[] flist; 	
		mess230out.doIt();
		flist = filemanager.getListFiles(outfledir);
		assertNotNull(flist);
		assertEquals(flist.length,1);
		String state = jdbcTemplate.queryForObject("SELECT status FROM reestrhist", String.class);
		assertEquals(state,"EXPORTED");
		String dbfname = jdbcTemplate.queryForObject("SELECT falename FROM reestrhist", String.class);
		assertEquals(dbfname,flist[0].getName());
		flist = filemanager.getListFiles(tmpfdir);
		assertEquals(flist.length,0);
		assertEquals(cu.getcountfile("2", null),"01");
	}
	@Before
	public void before() throws Exception
	{
		filemanager.removeAllFiles(dir230name);
		filemanager.removeAllFiles(dir230name+"/arc");
		filemanager.removeAllFiles(dir230name+"/tmp");
		
		DBTestUtils.runSQLInTransaction(runInTransaction, jdbcTemplate, "/Mess230Test.sql");
	}
}
