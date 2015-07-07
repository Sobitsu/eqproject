package org.hc.nps.inMessage;

import static org.junit.Assert.*;

import org.hc.nps.biks.BiksDBFLoader;
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
public class inMessageCheckTest {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private RunInTransaction runInTransaction;
	
	@Autowired
	private BiksDBFLoader biksDBFLoader;

	@Autowired
	private inMessageCheck inMC;
	
	@Test
	public void Check_messTest() throws INMesException{ 
	biksDBFLoader.loadBiks();
	inMC.all_bic = "";
	JSINMess imsg = new JSINMess();
	imsg.ID = "12";
	imsg.DC = "1";
	imsg.Msgblock = "test";
	imsg.NET_AMOUNT = "1500";
	imsg.TRANSFER_BIC ="040047002";
	imsg.SCHEME = "MAST";
	inMC.Check_mess(imsg);
	
	imsg = new JSINMess();
	imsg.ID = "13";
	imsg.DC = "2";
	imsg.Msgblock = "test";
	imsg.NET_AMOUNT = "2500";
	imsg.TRANSFER_BIC ="043469751";
	imsg.SCHEME = "MAST";
	inMC.Check_mess(imsg);
	
	imsg = new JSINMess();
	imsg.ID = "14";
	imsg.DC = "2";
	imsg.Msgblock = "test";
	imsg.NET_AMOUNT = "3500";
	imsg.TRANSFER_BIC ="043469751";
	imsg.SCHEME = "VISA";
	try {
		inMC.Check_mess(imsg);
	} catch (INMesException e) {
		assertEquals(e.getMessage().equals(" дублирование БИК в файле "),true);}
	
	inMC.all_bic = "~";
	imsg = new JSINMess();
	imsg.ID = "15";
	imsg.DC = "1";
	imsg.Msgblock = "test";
	imsg.NET_AMOUNT = "4500.01";
	imsg.TRANSFER_BIC ="040147781";
	imsg.SCHEME = "VISA";
	try {
		inMC.Check_mess(imsg);
	} catch (INMesException e) {
		assertEquals(e.getMessage().equals("Не верный формат числа 4500.01"),true);}
	
	inMC.all_bic = "~";
	imsg = new JSINMess();
	imsg.ID = "15";
	imsg.DC = "1";
	imsg.Msgblock = "test";
	imsg.NET_AMOUNT = "4500";
	imsg.TRANSFER_BIC ="040000000";
	imsg.SCHEME = "VISA";
	try {
		inMC.Check_mess(imsg);
	} catch (INMesException e) {
		assertEquals(e.getMessage().equals("не найден БИК в справочнике 040000000"),true);}
	
	inMC.all_bic = "~";
	imsg = new JSINMess();
	imsg.ID = "15";
	imsg.DC = "1";
	imsg.Msgblock = "test";
	imsg.NET_AMOUNT = "4500";
	imsg.TRANSFER_BIC ="0400000000";
	imsg.SCHEME = "VISA";
	try {
		inMC.Check_mess(imsg);
	} catch (INMesException e) {
		assertEquals(e.getMessage().equals("не корректна длина БИК 0400000000не найден БИК в справочнике 0400000000"),true);}
	}
	@Before
	public void before() throws Exception
	{
		DBTestUtils.runSQLInTransaction(runInTransaction, jdbcTemplate, "/BiksLoad.sql");
	}

}
