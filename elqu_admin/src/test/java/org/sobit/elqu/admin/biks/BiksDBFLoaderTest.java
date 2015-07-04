package org.sobit.elqu.admin.biks;

import java.util.List;
import java.util.Map;

import org.sobit.elqu.admin.db.DBTestUtils;
import org.hc.jp.db.RunInTransaction;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sobit.elqu.admin.biks.BiksDBFLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Ctac
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-web-context.xml" })
public class BiksDBFLoaderTest 
{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private RunInTransaction runInTransaction;
	
	@Autowired
	private BiksDBFLoader biksDBFLoader;
	
	@Test
	public void testLoadDBFToDB()
	{
		biksDBFLoader.loadBiks();
		
		Long count = jdbcTemplate.queryForObject("SELECT count(id) FROM biks", Long.class);
		assertNotNull(count);
		assertEquals(3106L, count.longValue());
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM biks WHERE bik LIKE '040173749'");
		assertNotNull(rows);
		assertEquals(1, rows.size());
		
		Map<String, Object> row = rows.get(0);
		assertNotNull(row);
		assertEquals("30101810000000000749", row.get("corr_acc"));
		assertEquals("ООО \"КБ \"ТАЛЬМЕНКА-БАНК\"", row.get("full_name"));
		assertEquals("ТАЛЬМЕНКА-БАНК", row.get("name"));
		assertEquals("826", row.get("reg_num"));
		assertEquals("09316294", row.get("okpo"));
	}
	
	@Before
	public void before() throws Exception
	{
		DBTestUtils.runSQLInTransaction(runInTransaction, jdbcTemplate, "/BiksLoad.sql");
	}
}
