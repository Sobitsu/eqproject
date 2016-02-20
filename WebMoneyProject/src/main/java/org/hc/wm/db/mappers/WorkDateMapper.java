package org.hc.wm.db.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.hc.jp.db.mappers.NoExceptionResulsetWrapper;

import org.hc.wm.db.entities.WorkDate;
import org.springframework.jdbc.core.RowMapper;

public class WorkDateMapper implements RowMapper<WorkDate>{

	@Override
	public WorkDate mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		WorkDate pSys = new WorkDate();
		NoExceptionResulsetWrapper rs = new NoExceptionResulsetWrapper(resultSet);
		pSys.caldate = rs.getDate("caldate");
		pSys.isworkdate = rs.getString("isworkdate");
		pSys.pervworkdate = rs.getDate("pervworkdate");
		return pSys;
	}
	
}