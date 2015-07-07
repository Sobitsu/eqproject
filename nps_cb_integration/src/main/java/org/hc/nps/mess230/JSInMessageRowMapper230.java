package org.hc.nps.mess230;

import java.sql.SQLException;

import org.hc.jp.db.mappers.NoExceptionResulsetWrapper;
import org.hc.jp.db.mappers.RowMapperNoException;
import org.hc.nps.exceptions.mvc.js.JSReportRow;
import org.hc.jp.utils.DateTimeUtils;

public class JSInMessageRowMapper230 extends RowMapperNoException<JSReportRow> {
	private final String pathToDocDir;

	public JSInMessageRowMapper230(String pathToDocDir) {
		this.pathToDocDir = pathToDocDir;
	}
	
	@Override
	public JSReportRow mapRow(NoExceptionResulsetWrapper rs, int rowNum) throws SQLException 
	{
		JSReportRow row = new JSReportRow();
		
		row.edNo = rs.getString("edNo");
		row.fileName = rs.getString("falename");
		row.esCreateDate = DateTimeUtils.formatDate(rs.getDate("EDDate"),DateTimeUtils.ORACLE_DATE_FORMAT_STRING );
		row.status = rs.getString("Status");
		row.psCode = rs.getString("ClearingSystemCode");
		row.registryRecordCount = rs.getString("RegisterItemsQuantity");
		row.creditSum = rs.getString("RegisterCreditSum");
		row.debitSum = rs.getString("RegisterDebetSum");
		row.registryBeginDate =DateTimeUtils.formatDate(rs.getDate("BeginProcessingDate"),DateTimeUtils.ORACLE_DATE_FORMAT_STRING );
		row.registryEndDate =DateTimeUtils.formatDate(rs.getDate("EndProcessingDate"),DateTimeUtils.ORACLE_DATE_FORMAT_STRING );
		row.rejectTextPath = rs.getString("Annotation");
		row.rejectFileName = rs.getString("rejectFile");
		row.docPath = pathToDocDir+"/"+rs.getString("falename");
		return row;
	}

}
