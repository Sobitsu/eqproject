package org.sobit.elqu.admin.db.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.sobit.elqu.admin.db.entities.JSMess230head;
import org.springframework.jdbc.core.RowMapper;

public final class JSMess230headRowMapper implements RowMapper<JSMess230head> {

	@Override
	public JSMess230head mapRow(ResultSet rs, int rowNum) throws SQLException {
		JSMess230head messhead = new JSMess230head();
		messhead.EDNo = rs.getString("EDNo");
		messhead.EDAuthor = rs.getString("EDAuthor");
		messhead.EDDate = rs.getDate("EDDate");
		messhead.BeginProcessingDate = rs.getDate("BeginProcessingDate");
		messhead.EndProcessingDate = rs.getDate("EndProcessingDate");
		messhead.ClearingSystemCode = rs.getString("ClearingSystemCode");
		messhead.Status = rs.getString("Status");
		messhead.RegisterItemsQuantity = rs.getLong("RegisterItemsQuantity");
		messhead.RegisterCreditSum = rs.getLong("RegisterCreditSum");
		messhead.RegisterDebetSum = rs.getLong("RegisterDebetSum");
		messhead.id = rs.getLong("id");
		return messhead;
	}

}
