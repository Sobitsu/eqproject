package org.sobit.elqu.admin.db.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.hc.jp.db.mappers.NoExceptionResulsetWrapper;
import org.sobit.elqu.admin.db.entities.InReeHist;
import org.springframework.jdbc.core.RowMapper;

public class InReeHistMapper implements RowMapper<InReeHist>{
	@Override
	public InReeHist mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		InReeHist inRee = new InReeHist();
		NoExceptionResulsetWrapper rs = new NoExceptionResulsetWrapper(resultSet);
		inRee.ClearingSystemCode = rs.getString("ClearingSystemCode");
		inRee.BeginProcessingDate = rs.getDate("BeginProcessingDate");
		inRee.EndProcessingDate = rs.getDate("EndProcessingDate");
		inRee.id = rs.getLong("id");
		inRee.ItemCount = rs.getLong("ItemCount");
		inRee.SumDt = rs.getLong("SumDt");
		inRee.SumKt = rs.getLong("SumKt");
		inRee.status = rs.getString("Status");
		return inRee;
	}
}
