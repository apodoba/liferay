package com.notification.dao;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.portal.utils.SqlUtil;

@Repository
public class PaymentDaoImpl implements PaymentDao{
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public BigDecimal getAllPayments(long userId, int month, int year) {
		try{
			BigDecimal allPayments = jdbcTemplate.queryForObject(SqlUtil.SELECT_ALL_PAYMENT, new Object[] {userId, month, year}, BigDecimal.class);
			return allPayments;
		}catch(EmptyResultDataAccessException e){
			return new BigDecimal(0);
		}
	}

}
