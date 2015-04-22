package com.payment.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.portal.domen.Balance;
import com.portal.utils.SqlUtil;

@Repository
public class BalanceDaoImpl implements BalanceDao{
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Balance getBalance(long userId) {
		Balance balance = null;
		try{
			balance = (Balance) jdbcTemplate.queryForObject(SqlUtil.SELECT_USER_BALANCE,
				new Object[] {userId},
				new BalanceMapper());
		}catch(EmptyResultDataAccessException exception ){
			return insertBalanceEmpty(userId);
		}
		return balance;
	}
	
	private Balance insertBalanceEmpty(long userId) {
		this.jdbcTemplate.update(SqlUtil.INSERT_BALANCE, userId, 0);	
		Balance balance = (Balance) jdbcTemplate.queryForObject(SqlUtil.SELECT_USER_BALANCE,
				new Object[] {userId},
				new BalanceMapper());
		return balance;
	}
	

	@Override
	public void updateBalance(long userId, BigDecimal value) {
		this.jdbcTemplate.update(SqlUtil.UPDATE_BALANCE, new Object[] {value, userId});

	}
	
	private static final class BalanceMapper implements RowMapper<Balance> {
		public Balance mapRow(ResultSet rs, int rowNumber) throws SQLException {
			Balance balance = new Balance();
			balance.setBalanceId(rs.getInt(SqlUtil.FIELD_BALANCE_ID));
			balance.setUserId(rs.getLong(SqlUtil.FIELD_USER_ID));
			balance.setValue(rs.getBigDecimal(SqlUtil.FIELD_VALUE));
			return balance;
		}
	}
}
