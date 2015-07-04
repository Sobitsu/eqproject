package org.sobit.elqu.admin.db.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.hc.jp.db.mappers.NoExceptionResulsetWrapper;
import org.sobit.elqu.admin.db.entities.InReeInfo;
import org.springframework.jdbc.core.RowMapper;

public class InReeInfomapper implements RowMapper<InReeInfo>{

	@Override
	public InReeInfo mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		InReeInfo inRee = new InReeInfo();
		NoExceptionResulsetWrapper rs = new NoExceptionResulsetWrapper(resultSet);
		inRee.sysCode = rs.getString("ClearingSystemCode");
		inRee.bic = rs.getString("Bic");
		inRee.dc = rs.getString("DC");
		inRee.sum = rs.getLong("Sum");
		inRee.status = rs.getString("Status");
		inRee.filename = rs.getString("filename");
		return inRee;
	}

}
