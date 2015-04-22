package com.payment.dao;

import java.math.BigDecimal;

public interface StatisticDao {

	public void updateStatisticPrice(BigDecimal price, int serviceId, int month, int year);
	
	public BigDecimal getPeriodPrice(long userId, int year, int month);
}
