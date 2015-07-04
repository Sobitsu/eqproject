package org.sobit.elqu.admin.biks;

import java.util.List;

import org.sobit.elqu.admin.db.entities.Bic_str;
import org.sobit.elqu.admin.db.mappers.Bik_str_mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BiksUtil {
	@Autowired
	private  JdbcTemplate jdbcTemplate;
	
	public Boolean isexistbik(String bik){
		Boolean ret = false;
		//поиск БИК в таблице БИКов
		String sql_str;
		sql_str = "SELECT 1  FROM biks WHERE bik = ?";
		
		List<Bic_str> Lbic= jdbcTemplate.query(sql_str, new Bik_str_mappers(),new Object[]{bik});
	
		if (Lbic.size() > 0) ret = true;
		return ret;
//		return true;
	}
}
