package org.hc.nps.inMessage201;

import org.hc.nps.message201.JSInMessSession201;
import org.hc.nps.message201.JSMESS201;
import org.hc.nps.message201.inMessage201Check;
import org.hc.nps.message201.inMessage201DB;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class inMessage201CheckTest {
	@Autowired
	private inMessage201DB inMDB;
	@Test
	public void inMessADDTest() throws Exception
	{
		JSInMessSession201 inMess = new JSInMessSession201();
		inMess.filename = "x2x.xml";
		JSMESS201 imsg = new JSMESS201();
		imsg.EDNo = "12";
		imsg.EDDate = "2014-12-15";
		imsg.EDAuthor = "4525000001";
		imsg.EDReceiver = "4525545000";
		imsg.CtrlCode = "2900";
		imsg.CtrlTime = "11:34:00";
		imsg.Annotation = "Успешное завершение первой стадии контроля";
		imsg.ErrorDiagnostic = "kzkzkzkzkzzk";
		imsg.MsgID ="047182727";
		imsg.EDNo1 = "55";
		imsg.EDDate1 = "2014-10-15";
		imsg.EDAuthor1 = "4525999991";
		@SuppressWarnings("unused")
		inMessage201Check check = new inMessage201Check();
}
}
