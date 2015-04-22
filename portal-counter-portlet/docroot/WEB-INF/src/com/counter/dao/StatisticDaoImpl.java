package com.counter.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.portal.domen.Service;
import com.portal.domen.Statistic;
import com.portal.utils.SqlUtil;

@Repository
public class StatisticDaoImpl implements StatisticDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Statistic> getAllStatisticByUser(long user_id) {
		List<Statistic> statistics = this.jdbcTemplate.query(SqlUtil.SELECT_ALL_STATISTIC_BY_USER+SqlUtil.SELECT_STATISTIC_ORDER, 
				new Object[] {user_id},
				new StatisticMapper());
		return statistics;
	}
	

	@Override
	public List<Statistic> getAllStatisticByPeriod(long userId, int month, int year) {
		StringBuffer queryCondition = new StringBuffer(SqlUtil.SELECT_ALL_STATISTIC_BY_USER);
		List<Object> params = new ArrayList<Object>();
		params.add(userId);
		
		if(month != 0){
			params.add(month);
			queryCondition.append("AND ")
				.append(SqlUtil.FIELD_MONTH).append("=? ");
		}
		if(year != 0){
			params.add(year);
			if(!queryCondition.toString().isEmpty()){
				queryCondition.append("AND ");
			}
			queryCondition.append(SqlUtil.FIELD_YEAR).append("=? ");
		}
		queryCondition.append(SqlUtil.SELECT_STATISTIC_ORDER);
		List<Statistic> statistics = this.jdbcTemplate.query(queryCondition.toString(), params.toArray(),new StatisticMapper());
		return statistics;
	}

	@Override
	@Transactional
	public void addStatistic(Statistic statistic) {
		this.jdbcTemplate.update(SqlUtil.INSERT_STATISTIC, statistic.getValue(), statistic
				.getService().getId(), statistic.getMonth(), statistic
				.getYear(), statistic.getUserId());
	}

	@Override
	@Transactional
	public void updateStatisticValue(Statistic statistic) {
		this.jdbcTemplate.update(SqlUtil.UPDATE_STATISTIC_VALUE, 
				new Object[] {statistic.getValue(), statistic.getUserId(), statistic.getStatisticId()});
	}

	@Override
	public Statistic getStatistic(Service service, long userId, int year, int month) {
		Statistic statistic = null;
		try{
			statistic = (Statistic) jdbcTemplate.queryForObject(SqlUtil.SELECT_STATISTIC_BY_PERIOD_AND_SERVICE,
				new Object[] {userId, month, year, service.getId()},
				new StatisticMapper());
		}catch(EmptyResultDataAccessException exception ){
			return statistic;
		}
		return statistic;
	}
	
	@Override
	public BigDecimal getPeriodPrice(long userId, int year, int month) {
		try{
			BigDecimal price = this.jdbcTemplate.queryForObject(SqlUtil.SELECT_STATISTIC_PRICE, new Object[] {userId, month, year}, BigDecimal.class);
			return price;
		}catch(EmptyResultDataAccessException e){
			return new BigDecimal(0);
		}
	}

	private static final class StatisticMapper implements RowMapper<Statistic> {
		public Statistic mapRow(ResultSet rs, int rowNumber) throws SQLException {
			Statistic statistic = new Statistic();
			statistic.setStatisticId(rs.getInt(SqlUtil.FIELD_STATISTIC_ID));
			statistic.setUserId(rs.getInt(SqlUtil.FIELD_USER_ID));
			statistic.setValue(rs.getDouble(SqlUtil.FIELD_VALUE));
			statistic.setMonth(rs.getInt(SqlUtil.FIELD_MONTH));
			statistic.setPrice(rs.getBigDecimal(SqlUtil.FIELD_STATISTIC_PRICE));
			statistic.setYear(rs.getInt(SqlUtil.FIELD_YEAR));
			statistic.setService(Service.valueOf(rs.getInt(SqlUtil.FIELD_STATISTIC_SERVICE)));
			return statistic;
		}
	}
}
