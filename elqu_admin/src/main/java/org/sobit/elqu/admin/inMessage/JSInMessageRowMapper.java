package org.sobit.elqu.admin.inMessage;

import java.sql.SQLException;

import org.hc.jp.db.mappers.NoExceptionResulsetWrapper;
import org.hc.jp.db.mappers.RowMapperNoException;
import org.hc.jp.utils.DateTimeUtils;
import org.sobit.elqu.admin.exceptions.mvc.js.JSReportRow;

/**
 *
 * @author Ctac
 */
public class JSInMessageRowMapper extends RowMapperNoException<JSReportRow>
{
	private final String pathToDocDir;

	public JSInMessageRowMapper(String pathToDocDir) {
		this.pathToDocDir = pathToDocDir;
	}
	

	@Override
	public JSReportRow mapRow(NoExceptionResulsetWrapper rs, int rowNum) throws SQLException 
	{
		JSReportRow row = new JSReportRow();
		
		row.id = rs.getLong("id");
		row.idEd230 = rs.getString("id_230");
		row.creditSum = rs.getString("SumKt");
		row.debitSum = rs.getString("SumDt");
		row.fileName = rs.getString("filename");
		row.recordCount =  (int) rs.getLong("itemCount");
		row.psCode = rs.getString("ClearingSystemCode");
		row.status = rs.getString("Status");
		row.docPath = pathToDocDir+"/"+rs.getString("filename");
		row.curRegistryDate =DateTimeUtils.formatDate(rs.getDate("EndProcessingDate"),DateTimeUtils.ORACLE_DATE_FORMAT_STRING );
		return row;
	}
}
