package org.hc.nps.message201;

import org.hc.jp.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class Metch_reestr {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void seek_reestr(JSInMessSession201 inMess){
		//обновление реестра, на который есть ответ
		StringBuilder sqlUpdate = new StringBuilder();
		String state_r = new String();
		sqlUpdate.append("UPDATE reestrhist set status = ? where EDNo = ? and EDDate = ? and EDAuthor = ?");
		sqlUpdate.append(";");
		try{
			if (inMess.req_BD.CtrlCode.equals("2900")){
				state_r = "ACCEPT";}
			else { state_r = "ERRORREESTR"; 
			}
			jdbcTemplate.update(sqlUpdate.toString(),new Object[] {state_r,inMess.req_BD.EDRefID_EDNo, DateTimeUtils.formatDate(inMess.req_BD.EDRefID_EDDate), inMess.req_BD.EDRefID_EDAuthor} );
		
			System.out.println("ОБНОВИЛИ!!!!!!!!!!!!");
		}
		catch (Exception e){
		System.out.println("ОШИБКА!!!!!!!!!!!!!!!!!!!!!");}
		
	}
}
