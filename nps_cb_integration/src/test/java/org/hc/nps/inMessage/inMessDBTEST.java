package org.hc.nps.inMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.hc.nps.inMessage.JSINMess;
import org.hc.nps.inMessage.inMessageDB; 

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-web-context.xml" })
public class inMessDBTEST {
	
	@Autowired
	private inMessageDB inMDB;
	@Test
	public void inMessADDTest() throws Exception
	{
		JSInMessSession inMess = new JSInMessSession();
		inMess.filename = "x2x.xml";
		JSINMess imsg = new JSINMess();
		imsg.ID = "12";
		imsg.DC = "1";
		imsg.Msgblock = "test";
		imsg.NET_AMOUNT = "1500";
		imsg.TRANSFER_BIC ="047182727";
		imsg.SCHEME = "MAST";
		inMDB.addinMess(imsg,inMess);
		imsg = new JSINMess();

		imsg.ID = "13";
		imsg.DC = "2";
		imsg.Msgblock = "test";
		imsg.NET_AMOUNT = "2500";
		imsg.TRANSFER_BIC ="047182727";
		imsg.SCHEME = "MAST";
		inMDB.addinMess(imsg,inMess);
		imsg = new JSINMess();
		
		imsg.ID = "14";
		imsg.DC = "2";
		imsg.Msgblock = "test";
		imsg.NET_AMOUNT = "3500";
		imsg.TRANSFER_BIC ="047182727";
		imsg.SCHEME = "VISA";
		inMDB.addinMess(imsg,inMess);
		imsg = new JSINMess();

		imsg.ID = "15";
		imsg.DC = "1";
		imsg.Msgblock = "test";
		imsg.NET_AMOUNT = "4500";
		imsg.TRANSFER_BIC ="047182727";
		imsg.SCHEME = "VISA";
		inMDB.addinMess(imsg,inMess);

		inMDB.saveDB(inMess);
	}
}
