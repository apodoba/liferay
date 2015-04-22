package com.account.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.portal.domen.BalanceHistory;
import com.portal.utils.SqlUtil;

@Repository
public class BalanceHistoryDaoImpl implements BalanceHistoryDao{
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void insertHistory(long userId, BigDecimal value, Date date, String operation) {
		this.jdbcTemplate.update(SqlUtil.INSERT_BALANCE_HISTORY, userId, value, date, operation);
	}

	@Override
	public List<BalanceHistory> getAllHistory(long userId) {
		List<BalanceHistory> balanceHistories = this.jdbcTemplate.query(SqlUtil.SELECT_BALANCE_HISTORY, new Object[]{userId}, new StatisticMapper());
		return balanceHistories;
	}

	private static final class StatisticMapper implements RowMapper<BalanceHistory> {
		public BalanceHistory mapRow(ResultSet rs, int rowNumber) throws SQLException {
			BalanceHistory balanceHistory = new BalanceHistory();
			balanceHistory.setHistoryId(rs.getInt(SqlUtil.FIELD_HISTORY_ID));
			balanceHistory.setUserId(rs.getLong(SqlUtil.FIELD_USER_ID));
			balanceHistory.setValue(rs.getBigDecimal(SqlUtil.FIELD_VALUE));
			balanceHistory.setDate(rs.getTimestamp(SqlUtil.FIELD_HISTORY_DATE));
			balanceHistory.setOperation(rs.getString(SqlUtil.FIELD_HISTORY_OPERATION));
			return balanceHistory;
		}
	}
}
