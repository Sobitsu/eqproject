package org.hc.nps.db.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.hc.jp.db.mappers.NoExceptionResulsetWrapper;

import org.springframework.jdbc.core.RowMapper;
import org.hc.nps.db.entities.PlatSysInfo;
public class PlatSysMappers implements RowMapper<PlatSysInfo>{
	@Override
	public PlatSysInfo mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		PlatSysInfo pSys = new PlatSysInfo();
		NoExceptionResulsetWrapper rs = new NoExceptionResulsetWrapper(resultSet);
		pSys.clearingSystemCode = rs.getString("ClearingSystemCode");
		pSys.itemCount = rs.getLong("ItemCount");
		pSys.sumDt = rs.getLong("sumDt");
		pSys.sumKt = rs.getLong("sumKt");
		return pSys;
	}
}
