package org.hc.nps.db.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.hc.jp.db.mappers.NoExceptionResulsetWrapper;

import org.hc.nps.db.entities.PlatForGroupInfo;
import org.springframework.jdbc.core.RowMapper;

public class PlatForGroupInfoMapper implements RowMapper<PlatForGroupInfo>{

	@Override
	public PlatForGroupInfo mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		PlatForGroupInfo info = new PlatForGroupInfo();
		NoExceptionResulsetWrapper rs = new NoExceptionResulsetWrapper(resultSet);
		info.clearingsystemcode = rs.getString("ClearingSystemCode");
		info.bic = rs.getString("Bic");
		info.itemcount = rs.getLong("ItemCount");
		info.sumdt = rs.getLong("sumDt");
		info.sumkt = rs.getLong("sumKt");
		return info;
	}

}
