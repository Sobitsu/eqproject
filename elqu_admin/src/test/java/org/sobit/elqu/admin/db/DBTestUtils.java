package org.sobit.elqu.admin.db;

import java.io.InputStream;
import java.util.concurrent.Callable;
import org.hc.jp.db.RunInTransaction;

import org.springframework.core.io.InputStreamResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

/**
 *
 * @author Ctac
 */
public class DBTestUtils
{
	public static void runSQLInTransaction(RunInTransaction runInTransaction, final JdbcTemplate jdbcTemplate, final String fileName) throws Exception
	{
		runInTransaction.run(new Callable<Void>() {

			@Override
			public Void call() throws Exception 
			{
				InputStream is = getClass().getResourceAsStream(fileName);
				JdbcTestUtils.executeSqlScript(jdbcTemplate, new InputStreamResource(is), false);
				return null;
			}
		});
	}
	
	public static void runSQLStatementInTransaction(RunInTransaction runInTransaction, final JdbcTemplate jdbcTemplate, final String sql) throws Exception
	{
		runInTransaction.run(new Callable<Void>() {

			@Override
			public Void call() throws Exception 
			{
				jdbcTemplate.update(sql);
				return null;
			}
		});
	}
}
