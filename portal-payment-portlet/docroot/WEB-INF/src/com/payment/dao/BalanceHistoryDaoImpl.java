package com.payment.dao;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.portal.utils.SqlUtil;

@Repository
public class BalanceHistoryDaoImpl implements BalanceHistoryDao{
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void insertHistory(long userId, BigDecimal value, Date date, String operation) {
		this.jdbcTemplate.update(SqlUtil.INSERT_BALANCE_HISTORY, userId, value, date, operation);
	}
}
