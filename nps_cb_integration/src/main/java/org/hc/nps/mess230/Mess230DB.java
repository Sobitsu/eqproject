package org.hc.nps.mess230;

import java.util.Date;
import java.util.List;

import org.hc.nps.calendar.CalendarUtils;
import org.hc.jp.db.JDBCUtils;
import org.hc.nps.db.entities.InReeHist;
import org.hc.nps.db.entities.InReeInfo;
import org.hc.nps.db.entities.JSMess230det;
import org.hc.nps.db.entities.JSMess230head;
import org.hc.nps.db.mappers.InReeHistMapper;
import org.hc.nps.db.mappers.InReeInfomapper;
import org.hc.jp.exceptions.ExceptionBuilder;
import org.hc.jp.utils.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional; 

/**
*
* @author Dale
*/
@Repository
public class Mess230DB {
	
	private static final Logger LOG = LoggerFactory.getLogger(Mess230DB.class);
	
	private final String SQL_SELECT_REESTRINFO = "SELECT Bic, Sum, DC, ClearingSystemCode FROM inreestrinfo WHERE CollId = ? and status = 'CREATE'";
	@Value(value = "${EDAUTHOR}")
	 private String EDAUTHOR;
	
	@Autowired
	private ExceptionBuilder exceptionBuilder;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private CalendarUtils cu;
	@Transactional(propagation = Propagation.REQUIRED)
	private void calcreedetail(List<JSMess230det> reedetail, Long id, Date curdate)
	{		
		String sql_str;
		Long i = cu.getRegisterItemIDcounter(curdate);
		sql_str = SQL_SELECT_REESTRINFO;
		List<InReeInfo> reestrs = jdbcTemplate.query(sql_str, new InReeInfomapper(),new Object[] { id});
		for (InReeInfo ps : reestrs) {
			i++;
			JSMess230det rdetail = new JSMess230det();
			rdetail.Bic = ps.bic;
			rdetail.DC = ps.dc;
			rdetail.RegisterItemID = i;
			rdetail.Sum = ps.sum;
			rdetail.Status = "CREATE";
			reedetail.add(rdetail);
		}
		LOG.debug("setRegisterItemIDcounter: curdate=" + curdate + "; i=" + i);
		cu.setRegisterItemIDcounter(curdate, i);
	}
	private void saveReeDb(JSMess230head rhead)
	{	
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO reestrhist(Status, BeginProcessingDate, EndProcessingDate, ClearingSystemCode,RegisterItemsQuantity, "
				+ "RegisterCreditSum, RegisterDebetSum, inreeID, EDDate, EDNo, EDAuthor) VALUES ");
		sql.append("('").append(rhead.Status).append("'")
			.append(", '").append(DateTimeUtils.formatDate(rhead.BeginProcessingDate)).append("' ")
			.append(", '").append(DateTimeUtils.formatDate(rhead.EndProcessingDate)).append("' ")
			.append(", '").append(rhead.ClearingSystemCode).append("' ")
			.append(", '").append(rhead.RegisterItemsQuantity.toString()).append("' ")
			.append(", '").append(rhead.RegisterCreditSum.toString()).append("' ")
			.append(", '").append(rhead.RegisterDebetSum.toString()).append("' ")
			.append(", '").append(rhead.id.toString()).append("' ")
			.append(", '").append(DateTimeUtils.formatDate(rhead.EDDate)).append("' ")
			.append(", '").append(rhead.EDNo).append("' ")
			.append(", '").append(rhead.EDAuthor).append("')");
		sql.append(";");
		Long newid = JDBCUtils.insertAndGetId(jdbcTemplate,  sql.toString());
		StringBuilder sqlDet = new StringBuilder();
		sqlDet.append("INSERT INTO reestrinfo(Status, CollId, RegisterItemID, BIC,Sum, DC) VALUES ");
		Boolean isNotFirst = false;
		for (JSMess230det detail : rhead.RegisterItemsInfo){
				if(isNotFirst)
					sqlDet.append(", ");
				sqlDet.append("('").append(detail.Status).append("'")
						.append(", '").append(newid.toString()).append("' ")
						.append(", '").append(detail.RegisterItemID).append("' ")
						.append(", '").append(detail.Bic).append("' ")
						.append(", '").append(detail.Sum.toString()).append("' ")
						.append(", '").append(detail.DC).append("')");
			isNotFirst = true;
			}
			sqlDet.append(";");
			jdbcTemplate.update(sqlDet.toString());
			StringBuilder sqlUpdate = new StringBuilder();
			sqlUpdate.append("UPDATE inreestrhist set Status = 'DONE' where ID = ?");
			sqlUpdate.append(";");
			jdbcTemplate.update(sqlUpdate.toString(), new Object[] { rhead.id});	
			String sqlup = "UPDATE inreestrinfo set Status = 'DONE' where CollId = ? and status = 'CREATE'";
			jdbcTemplate.update(sqlup, new Object[] { rhead.id});
	};	

	private Boolean checkReestr(JSMess230head rhead) throws ED230Exception{
		Boolean result = true; 
		Long sumdt = 0L;
		Long sumkt = 0L;
		if (rhead.RegisterCreditSum.compareTo(rhead.RegisterDebetSum)!=0) result = false;
		if (rhead.RegisterItemsQuantity.compareTo((long) rhead.RegisterItemsInfo.size()) != 0) result = false;
		for (JSMess230det mdet:rhead.RegisterItemsInfo)
		{
			if (mdet.DC == null|mdet.Sum == null) throw  new ED230Exception("FATAL ERROR!!! Ошибка целостности БД");
			if (mdet.DC.contentEquals("1")) sumdt = sumdt + mdet.Sum;
			if (mdet.DC.contentEquals("2")) sumkt = sumkt + mdet.Sum;
		}
		if (rhead.RegisterCreditSum.compareTo(sumkt) != 0) result = false;
		if (rhead.RegisterDebetSum.compareTo(sumdt)!=0) result = false;
		return result;
	}
	@Scheduled(fixedDelay = 60000L)
	@Transactional(propagation = Propagation.REQUIRES_NEW)//Делаем в транзакции
	public void doIt() throws Exception
	{
		String sql_str;
		Boolean checkres;
		Date todate =java.util.Calendar.getInstance().getTime();
		sql_str = "SELECT id, BeginProcessingDate, EndProcessingDate, ClearingSystemCode, ItemCount, SumDt, SumKt"
				+ " FROM inreestrhist WHERE Status = 'CREATE'";
		List<InReeHist> reestrs = jdbcTemplate.query(sql_str, new InReeHistMapper());
		for (InReeHist ps : reestrs) {
			JSMess230head rhead = new JSMess230head();
			rhead.BeginProcessingDate = ps.BeginProcessingDate;
			rhead.EndProcessingDate = ps.EndProcessingDate;
			rhead.ClearingSystemCode = ps.ClearingSystemCode;
			rhead.RegisterItemsQuantity = ps.ItemCount;
			rhead.RegisterCreditSum = ps.SumKt;
			rhead.RegisterDebetSum = ps.SumDt;
			rhead.EDDate = todate;
			rhead.EDNo = cu.getEDNcounter(todate).toString();
			rhead.EDAuthor = EDAUTHOR;
			rhead.Status = "CREATE";
			rhead.id = ps.id;
			calcreedetail(rhead.RegisterItemsInfo,rhead.id,rhead.EDDate);
			checkres =checkReestr(rhead);
			if (checkres == false) rhead.Status = "CHECKERROR";
			if (rhead.RegisterItemsInfo.size()>0) {
				saveReeDb(rhead);
				cu.setEDNcounter(todate);
			}
		}
	}
}
