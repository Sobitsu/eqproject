package org.hc.nps.inMessage201;

import org.hc.nps.message201.JSInMessSession201;
import org.hc.nps.message201.JSMESS201;
import org.hc.nps.message201.Metch_reestr;
import org.hc.nps.message201.inMessage201DB;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-web-context.xml" })
public class Metch_reestrTest {
	@Autowired
	private inMessage201DB inMDB;
	@Autowired
	private Metch_reestr CH_Reestr;
	
	@Test
	public void inMessADDTest() throws Exception
	{
		JSInMessSession201 inMess = new JSInMessSession201();
		inMess.filename = "x2x.xml";
		JSMESS201 imsg = new JSMESS201();
		imsg.EDNo = "133";
		imsg.EDDate = "2014-12-15";
		imsg.EDAuthor = "4525000001";
		imsg.EDReceiver = "4525545000";
		imsg.CtrlCode = "2900";
		imsg.CtrlTime = "11:34:00";
		imsg.Annotation = "Успешное завершение первой стадии контроля";
		imsg.ErrorDiagnostic = "kzkzkzkzkzzk";
		imsg.MsgID ="047182727";
		imsg.EDNo1 = "1";
		imsg.EDDate1 = "2015-01-15";
		imsg.EDAuthor1 = "4533333333";
	
		inMDB.addinMess(imsg,inMess);
		
		inMDB.saveDB(inMess);
		
//		Metch_reestr CH_Reestr = new Metch_reestr();
//		CH_Reestr.seek_reestr(inMess);
	}
}
