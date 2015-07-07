package org.hc.nps.message231;

import java.sql.SQLException;

import org.hc.jp.db.mappers.NoExceptionResulsetWrapper;
import org.hc.jp.db.mappers.RowMapperNoException;
import org.hc.nps.exceptions.mvc.js.JSReportRow;
import org.hc.jp.utils.DateTimeUtils;

/**
*
* @author Kat
*/
public class JSInMessage231RowMapper extends RowMapperNoException<JSReportRow> {
	private final String pathToDocDir;

	public JSInMessage231RowMapper(String pathToDocDir) {
		this.pathToDocDir = pathToDocDir;
	}
	

	@Override
	public JSReportRow mapRow(NoExceptionResulsetWrapper rs, int rowNum) throws SQLException 
	{
		JSReportRow row = new JSReportRow();
		row.edNo = rs.getString("EDNo");
		row.fileName = rs.getString("falename");
		row.esCreateDate =DateTimeUtils.formatDate(rs.getDate("EDDate"),DateTimeUtils.ORACLE_DATE_FORMAT_STRING );
		row.registryBeginDate =DateTimeUtils.formatDate(rs.getDate("BeginProcessingDate"),DateTimeUtils.ORACLE_DATE_FORMAT_STRING );
		row.registryEndDate =DateTimeUtils.formatDate(rs.getDate("EndProcessingDate"),DateTimeUtils.ORACLE_DATE_FORMAT_STRING );
		row.systemCode = rs.getString("ClearingSystemCode");
		row.registryRecordCount = rs.getString("RegisterItemsQuantity");
		row.registryType  = rs.getString("RegisterMode");
		row.docPath = pathToDocDir+"/"+rs.getString("filename");
		return row;
	}
}
