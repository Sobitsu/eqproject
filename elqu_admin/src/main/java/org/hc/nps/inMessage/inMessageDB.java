package org.hc.nps.inMessage;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hc.jp.exceptions.ExceptionBuilder;
import org.hc.jp.utils.BaseDataParser;
import org.hc.jp.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
//import org.hc.nps.calendar.CalendarUtils;
import org.hc.jp.db.JDBCUtils;
import org.hc.nps.db.entities.PlatForGroupInfo;
import org.hc.nps.db.entities.PlatSysInfo;
import org.hc.nps.db.mappers.PlatForGroupInfoMapper;
import org.hc.nps.db.mappers.PlatSysMappers;

/**
*
* @author dale
*/

@Repository
public class inMessageDB{
	
	@Autowired
	private ExceptionBuilder exceptionBuilder;
	
//	@Autowired
//	private CalendarUtils calendarUtils;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static String SQLLOADFORGROUP = "Select ClearingSystemCode, Bic, count(*) as ItemCount, sum(IF(DC='1',Sum,0)) as sumDt, sum(IF(DC='2',Sum,0)) as sumKt  FROM inreestrinfo WHERE SessionID = ? GROUP BY ClearingSystemCode, bic";
	
	private String getPlatSysCode(String scheme)
	{
		String psCode = "1";
		if (scheme.contentEquals("VISA")) psCode = "1";
		if (scheme.contentEquals("MAST")) psCode = "2";
		return psCode;
		
	}
	private void calcTables(JSInMessSession inMess) throws Exception
	{
		JSinreestrinfo  reehistinfo;
		for(JSINMess inmsg : inMess.inmessges)
		{
			if(inmsg == null) continue;
			reehistinfo =  new JSinreestrinfo();
			reehistinfo.ClearingSystemCode = getPlatSysCode(inmsg.SCHEME);
			reehistinfo.Bic = inmsg.TRANSFER_BIC;
			reehistinfo.Sum = BaseDataParser.parseLong(inmsg.NET_AMOUNT) ;
			reehistinfo.DC= inmsg.DC;
			reehistinfo.Msgblock = inmsg.Msgblock;
			reehistinfo.UniId = inmsg.ID;
			reehistinfo.SessionDate = java.util.Calendar.getInstance().getTime();
			reehistinfo.SessionID = inMess.SessionId;
			reehistinfo.Status = inmsg.Status;
			reehistinfo.fileName = inmsg.filename;
			inMess.reestrinfo.add(reehistinfo);
		}
	}
	
	public void addinMess(JSINMess inmessage,JSInMessSession inMess) throws Exception
	{
		inMess.inmessges.add(inmessage);
	}
	//system.curenttime
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveDB(JSInMessSession inMess) throws Exception
 	{
		Long curTime =System.currentTimeMillis(); 
		inMess.SessionId = curTime.toString();
		calcTables(inMess);
		insertinMessIntoDB(inMess.reestrinfo);
		groupmness(inMess);
		createreestrinf(inMess);
	}
	private void updatebic(String csc, String bic, String sessid) {
		String sqlupdate = "UPDATE inreestrinfo SET status = 'GROUPED' where ClearingSystemCode=? and Bic=? and SessionID = ?" ;
		jdbcTemplate.update(sqlupdate, new Object[] { csc, bic, sessid});
	}
	//функция дополнительного клиринга 
	private void groupmness(JSInMessSession inMess)
	{
		//возьмем выборку записей текущей сессии сгруппировав их по бикам. для всех записей у которых колличество больше 1 проведем доп клиринг
		List<PlatForGroupInfo> grinfos = jdbcTemplate.query(SQLLOADFORGROUP, new PlatForGroupInfoMapper(),  new Object[] { inMess.SessionId });
		Long sumdt;
		Long sumkt;
		Long sum;
		Long i = 1000L;
		String dc = "1";
		JSinreestrinfo  reehistinfo;
		List<JSinreestrinfo> reehistlist = new LinkedList<JSinreestrinfo>();
		for (PlatForGroupInfo grinfo : grinfos)
		{
			if (grinfo.itemcount == 1) continue;
			sumdt = grinfo.sumdt;
			sumkt = grinfo.sumkt;
			sum = sumdt - sumkt;
			if (sum > 0L ) dc = "1";
			if (sum < 0L ) {
				sum = -sum; 
				dc = "2";
				};
			if (sum == 0L) 
					{
						updatebic(grinfo.clearingsystemcode,grinfo.bic,inMess.SessionId);
						continue;
					};	
			reehistinfo =  new JSinreestrinfo();
			reehistinfo.ClearingSystemCode = grinfo.clearingsystemcode;
			reehistinfo.Bic = grinfo.bic;
			reehistinfo.Sum = sum;
			reehistinfo.DC= dc;
			reehistinfo.Msgblock = "Created";
			reehistinfo.UniId = i.toString() + inMess.SessionId;
			i++;
			reehistinfo.SessionDate = java.util.Calendar.getInstance().getTime();
			reehistinfo.SessionID = inMess.SessionId;
			reehistinfo.Status = "CREATE";
			reehistinfo.fileName = "create";
			reehistlist.add(reehistinfo);
			updatebic(grinfo.clearingsystemcode,grinfo.bic,inMess.SessionId);			
		}
		insertinMessIntoDB(reehistlist);
	}
	
    //функция для формирования записей реестра платежных систем
 
	private void createreestrinf(JSInMessSession inMess)
	{
//получение списка загруженных платежных систем
		String sql_str;
		Date todate = java.util.Calendar.getInstance().getTime();
		sql_str = "SELECT ClearingSystemCode, count(ClearingSystemCode) as ItemCount, sum(IF(DC='1',Sum,0)) as sumDt, sum(IF(DC='2',Sum,0)) as sumKt  FROM inreestrinfo WHERE SessionID = ? and Status = 'CREATE' GROUP BY ClearingSystemCode";
		List<PlatSysInfo> lPlatSys = jdbcTemplate.query(sql_str, new PlatSysMappers(),  new Object[] { inMess.SessionId });

		for (PlatSysInfo ps : lPlatSys) {
			JSinreestrhist reehist = new JSinreestrhist();
			reehist.ClearingSystemCode = ps.clearingSystemCode;
			reehist.ItemCount = ps.itemCount;
			reehist.SumDt = ps.sumDt;
			reehist.SumKt = ps.sumKt;
			reehist.Status = "CREATE";
//			reehist.EndProcessingDate = calendarUtils.getPrevDate(todate);
//			reehist.BeginProcessingDate = calendarUtils.getPrevWorkDate(todate);
//			reehist.fileName = inMess.filename;
			inMess.reestrhistarr.add(reehist);
			}
		
/*Последовательная обработка списка плажных систем
* добавление записи в таблицу
* обновление всех записей текущей сессии для данной платежнйо системы установка связи
*/
		for (JSinreestrhist reehist: inMess.reestrhistarr)
		{
			StringBuilder sql = new StringBuilder();
			if(reehist == null)
				continue;
			sql.append("INSERT INTO inreestrhist(Status, BeginProcessingDate, EndProcessingDate, ClearingSystemCode, itemCount, SumDt, SumKt) VALUES ");
				sql.append("('").append(reehist.Status).append("'")
					.append(", '").append(DateTimeUtils.formatDate(reehist.BeginProcessingDate)).append("' ")
					.append(", '").append(DateTimeUtils.formatDate(reehist.EndProcessingDate)).append("' ")
					.append(", '").append(reehist.ClearingSystemCode).append("' ")
//					.append(", '").append(reehist.fileName).append("' ")
					.append(", '").append(reehist.ItemCount).append("' ")
					.append(", '").append(reehist.SumDt).append("' ")
					.append(", '").append(reehist.SumKt).append("')");
				sql.append(";");
				Long id = JDBCUtils.insertAndGetId(jdbcTemplate,  sql.toString());
			StringBuilder sqlUpdate = new StringBuilder();
			sqlUpdate.append("UPDATE inreestrinfo set CollId = ? where sessionID = ? and ClearingSystemCode = ?");
			sqlUpdate.append(";");
			jdbcTemplate.update(sqlUpdate.toString(), new Object[] { id, inMess.SessionId, reehist.ClearingSystemCode });	
		}
	}

	private void insertinMessIntoDB(List<JSinreestrinfo> inMess)
	{
		if (inMess.size()>0) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO inreestrinfo(bic, sum, dc, UniID, MSG_BLOCK, ClearingSystemCode,sessionID,sessionDate, status, filename) VALUES ");
		
		Boolean isNotFirst = false;

		for(JSinreestrinfo rinfo : inMess)//.reestrinfo)
		{
			if(rinfo == null)
				continue;
			if(isNotFirst)
				sql.append(", ");
			
				sql.append("('").
					 append(rinfo.Bic).append("'")
					.append(", '").append(rinfo.Sum).append("' ")
					.append(", '").append(rinfo.DC).append("' ")
					.append(", '").append(rinfo.UniId).append("' ")
					.append(", '").append(rinfo.Msgblock).append("' ")
					.append(", '").append(rinfo.ClearingSystemCode).append("' ")
					.append(", '").append(rinfo.SessionID).append("' ")
					.append(", '").append(DateTimeUtils.formatDate(rinfo.SessionDate)).append("' ")
					.append(", '").append(rinfo.Status).append("' ")
					.append(", '").append(rinfo.fileName)
					.append("')");
			isNotFirst = true;
		}
		sql.append(";");
		jdbcTemplate.update(sql.toString());}
	}
}
