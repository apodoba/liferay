package com.payment.service;

import java.math.BigDecimal;

public interface StatisticService {

	public void updateStatisticPrice(BigDecimal price, int serviceId, int month, int year);
}
