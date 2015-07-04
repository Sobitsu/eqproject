package org.sobit.elqu.admin.utils;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;


public class StatusTableConst {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public BigInteger idStatusCreate;
	public BigInteger idStatusUppdate;
	public BigInteger idStatusDone;
	public StatusTableConst() {		
	
	}
}
