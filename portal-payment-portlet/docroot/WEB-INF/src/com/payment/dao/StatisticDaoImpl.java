package com.payment.dao;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.portal.utils.SqlUtil;

@Repository
public class StatisticDaoImpl implements StatisticDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void updateStatisticPrice(BigDecimal price, int serviceId, int month, int year) {
		this.jdbcTemplate.update(SqlUtil.UPDATE_STATISTIC_PRICE, 
				new Object[] {price, serviceId, month, year});
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
}
