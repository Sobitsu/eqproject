package org.sobit.elqu.admin.inMessage;

import java.sql.SQLException;

import org.hc.jp.db.mappers.NoExceptionResulsetWrapper;
import org.hc.jp.db.mappers.RowMapperNoException;
import org.hc.jp.utils.DateTimeUtils;
import org.sobit.elqu.admin.exceptions.mvc.js.JSReportRow;

public class JSInDocRowMapper extends RowMapperNoException<JSReportRow>{
	@SuppressWarnings("unused")
	private final String pathToDocDir;

	public JSInDocRowMapper(String pathToDocDir) {
		this.pathToDocDir = pathToDocDir;
	}
	

	@Override
	public JSReportRow mapRow(NoExceptionResulsetWrapper rs, int rowNum) throws SQLException 
	{
		String tmp;
		JSReportRow row = new JSReportRow();
		row.id = rs.getLong("id");
		row.bik = rs.getString("BIC");
		row.sum = rs.getString("sum");
		row.status = rs.getString("Status");
		row.nspkFileName = rs.getString("in_file");
		row.sendFileName = rs.getString("falename");
		row.reject201FileName = rs.getString("file_201");
		row.reject231FileName = rs.getString("file_231");
		tmp = rs.getString("dc");
		if (tmp.contentEquals("true")) row.isDebet = true; 
								else row.isDebet = false;
		row.date =DateTimeUtils.formatDate(rs.getDate("BeginProcessingDate"),DateTimeUtils.ORACLE_DATE_FORMAT_STRING );
		return row;
	}
}
