package com.payment.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.portal.domen.Payment;
import com.portal.utils.SqlUtil;

@Repository
public class PaymentDaoImpl implements PaymentDao{
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void insertPayment(Payment payment) {
		this.jdbcTemplate.update(SqlUtil.INSERT_PAYMENT, payment.getUserId(), payment.getMonth(), payment.getYear(), payment.getValue());		
	}
}
