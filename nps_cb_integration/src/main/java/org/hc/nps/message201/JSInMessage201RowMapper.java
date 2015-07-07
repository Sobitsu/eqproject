package org.hc.nps.message201;

import java.sql.SQLException;

import org.hc.jp.db.mappers.NoExceptionResulsetWrapper;
import org.hc.jp.db.mappers.RowMapperNoException;
import org.hc.nps.exceptions.mvc.js.JSReportRow;

public class JSInMessage201RowMapper extends RowMapperNoException<JSReportRow>
{
	private final String pathToDocDir;

	public JSInMessage201RowMapper(String pathToDocDir) {
		this.pathToDocDir = pathToDocDir;
	}


	@Override
	public JSReportRow mapRow(NoExceptionResulsetWrapper rs, int rowNum) throws SQLException 
	{
		JSReportRow row = new JSReportRow();
		row.id				= rs.getLong("id");
		row.edNo			= rs.getString("edNo");
		row.esCreateDate	= rs.getString("EDDate");
		row.fileName 		= rs.getString("filename");
		row.esControlCode 	= rs.getString("CtrlCode");
		row.description		= rs.getString("Annotation");
		row.messageId		= rs.getString("MsgID") ;
		row.esParentId		= rs.getString("EDRefID_EDNo");
		row.esControlTime	= rs.getString("CtrlTime");
		row.reject201FileName = rs.getString("filename");
		row.docPath = pathToDocDir+"/"+rs.getString("filename");
		return row;
	}

}
