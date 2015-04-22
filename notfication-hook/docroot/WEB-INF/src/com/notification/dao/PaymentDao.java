package com.notification.dao;

import java.math.BigDecimal;

public interface PaymentDao {
	
	public BigDecimal getAllPayments(long userId, int month, int year);
	
}