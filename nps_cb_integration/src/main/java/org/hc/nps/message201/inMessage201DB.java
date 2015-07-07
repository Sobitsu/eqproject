package org.hc.nps.message201;

import org.hc.jp.exceptions.ExceptionBuilder;
import org.hc.jp.utils.BaseDataParser;
import org.hc.jp.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
*
* @author kat
*/

@Repository
public class inMessage201DB {
	@Autowired
	private ExceptionBuilder exceptionBuilder;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private Metch_reestr CH_Reestr;
	
	private JSMESS201 inmessges = new JSMESS201();
	
	JSinMessage201  reehistinfo;
	
	public void addinMess(JSMESS201 inmes,JSInMessSession201 inMess) throws Exception
	{
	 inMess.inmessges = inmes;
	}
	
	private void calcTables(JSInMessSession201 inMess) throws Exception
	{
		
		JSMESS201 inmessage = new JSMESS201();
		JSinMessage201 reehistinfo = new JSinMessage201();
		inmessage = inMess.inmessges;
		
		if(inmessges == null) return;
			reehistinfo.EDNo = BaseDataParser.parseLong(inmessage.EDNo);
			reehistinfo.EDDate =  DateTimeUtils.parseDate(inmessage.EDDate,"yyyy-mm-dd");
			reehistinfo.CtrlTime = inmessage.CtrlTime;
			reehistinfo.EDAuthor = inmessage.EDAuthor;
			reehistinfo.EDReceiver = inmessage.EDReceiver;
			reehistinfo.CtrlCode = inmessage.CtrlCode;
			reehistinfo.ErrorDiagnostic = inmessage.ErrorDiagnostic;
			reehistinfo.Annotation = inmessage.Annotation;
			reehistinfo.MsgID = inmessage.MsgID;
			reehistinfo.EDRefID_EDNo = BaseDataParser.parseLong(inmessage.EDNo1);
			reehistinfo.EDRefID_EDDate = DateTimeUtils.parseDate(inmessage.EDDate1);
			reehistinfo.EDRefID_EDAuthor = inmessage.EDAuthor1;
			inMess.req_BD = reehistinfo; 
			
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveDB(JSInMessSession201 inMess) throws Exception
 	{
		Long curTime =System.currentTimeMillis(); 
		curTime.toString();
		if (inMess.inmessges.EDNo == null) return;
		calcTables(inMess);
		insertinMessIntoDB(inMess);
		//изменим состояние реестра
		CH_Reestr.seek_reestr(inMess);
	}
	


	private void insertinMessIntoDB(JSInMessSession201 inMess)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO ed201hist(EDNo, EDDate, EDAuthor,  CtrlCode, CtrlTime,EDReceiver,Annotation,ErrorDiagnostic,MsgID,EDRefID_EDNo,EDRefID_EDDate,EDRefID_EDAuthor,filename) VALUES ");
		
		JSinMessage201 rinfo = new JSinMessage201(); 
		rinfo = inMess.req_BD;
			sql.append("('").append(rinfo.EDNo).append("'")
					.append(", '").append(DateTimeUtils.formatDate(rinfo.EDDate)).append("' ")
					.append(", '").append(rinfo.EDAuthor).append("' ")
					.append(", '").append(rinfo.CtrlCode).append("' ")
					.append(", '").append(rinfo.CtrlTime).append("' ")
					.append(", '").append(rinfo.EDReceiver).append("' ")
					.append(", '").append(rinfo.Annotation).append("' ")
					.append(", '").append(rinfo.ErrorDiagnostic).append("' ")
					.append(", '").append(rinfo.MsgID).append("' ")
					.append(", '").append(rinfo.EDRefID_EDNo).append("' ")
					.append(", '").append(DateTimeUtils.formatDate(rinfo.EDRefID_EDDate)).append("' ")
					.append(", '").append(rinfo.EDRefID_EDAuthor).append("' ")
					.append(", '").append(inMess.filename).append("')");
		
		sql.append(";");
		
		jdbcTemplate.update(sql.toString());
	}
}