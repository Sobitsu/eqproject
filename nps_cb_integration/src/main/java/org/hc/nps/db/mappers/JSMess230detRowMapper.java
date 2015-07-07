package org.hc.nps.db.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.hc.nps.db.entities.JSMess230det;
import org.springframework.jdbc.core.RowMapper;

public final class JSMess230detRowMapper implements RowMapper<JSMess230det> {
	 
    @Override
    public JSMess230det mapRow(ResultSet rs, int rowNum) throws SQLException {
    	JSMess230det mess230det = new JSMess230det();
    	mess230det.Bic = rs.getString("BIC");
    	mess230det.CollId = rs.getLong("CollId");
    	mess230det.DC	= rs.getString("DC");
    	mess230det.RegisterItemID = rs.getLong("RegisterItemID");
    	mess230det.Status = rs.getString("Status");
    	mess230det.Sum = rs.getLong("Sum");
        return mess230det;
    };
    }

