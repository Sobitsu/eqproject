package org.hc.wm.db.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.hc.jp.db.mappers.NoExceptionResulsetWrapper;

import org.hc.nps.db.entities.Bic_str;
import org.springframework.jdbc.core.RowMapper;

public class Bik_str_mappers implements RowMapper<Bic_str> {
	@Override
	public Bic_str mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Bic_str p_Sys = new Bic_str();
		NoExceptionResulsetWrapper rs = new NoExceptionResulsetWrapper(resultSet);
		p_Sys.bic_num = rs.getString("bic_num");
		return p_Sys;
	}
}
