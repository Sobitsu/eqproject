package org.hc.wm.db.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.hc.jp.db.mappers.NoExceptionResulsetWrapper;

import org.hc.wm.db.entities.FileDateCounter;
import org.springframework.jdbc.core.RowMapper;

public class FileDateCounterMapper implements RowMapper<FileDateCounter>{
	@Override
	public FileDateCounter mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		FileDateCounter inRee = new FileDateCounter();
		NoExceptionResulsetWrapper rs = new NoExceptionResulsetWrapper(resultSet);
		inRee.mastcount = rs.getLong("mastfilecount");
		inRee.visacount = rs.getLong("visafilecount");
		return inRee;
	}

}
