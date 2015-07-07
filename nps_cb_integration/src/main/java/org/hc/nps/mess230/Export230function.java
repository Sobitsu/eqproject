package org.hc.nps.mess230;

import java.util.Date;
import java.util.List;

import org.hc.nps.exceptions.mvc.js.JSReportRow;
import org.hc.nps.exceptions.mvc.js.JSReportTable;
import org.hc.jp.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class Export230function {
	private final String SQL_SELECT_REESTRLIST = "SELECT id, Status, BeginProcessingDate, EndProcessingDate,  "
			+ "	ClearingSystemCode,  RegisterItemsQuantity,  RegisterDebetSum,  "
			+ "RegisterCreditSum,  EDNo,  EDDate,  EDAuthor"
			+" FROM reestrhist WHERE ";

	@Autowired
	private Mess230Out mess230Out;
	public void exportlist230(JSReportTable js) throws ED230Exception{
		StringBuilder sql;// = new StringBuilder();
		for(JSReportRow item: js.items){
			sql = new StringBuilder();
			Date itemdate = DateTimeUtils.parseDate(item.esCreateDate, DateTimeUtils.ORACLE_DATE_FORMAT_STRING);
			sql.append(SQL_SELECT_REESTRLIST);
			sql.append("EDNo =").append(item.edNo).append(" and ")
			   .append("faleName =").append("'").append(item.fileName).append("'").append(" and ")
			   .append("EDDate =").append("'").append(DateTimeUtils.formatDate(itemdate, DateTimeUtils.SERVER_DATE_FORMAT) ).append("'").append(" and ")
			   .append("RegisterItemsQuantity =").append(item.registryRecordCount).append(" and ")
			   .append("RegisterDebetSum =").append(item.debitSum).append(" and ")
			   .append("RegisterCreditSum =").append(item.creditSum).append(" and ")
			   .append("ClearingSystemCode =").append(item.psCode);
				List<JSMess230out> curree = mess230Out.getReeBysql(sql.toString());
				if (curree.size() == 0) continue;
				for(JSMess230out ree : curree)
					{
					mess230Out.createXMLOut(ree);
					}   
		}
	}
}
