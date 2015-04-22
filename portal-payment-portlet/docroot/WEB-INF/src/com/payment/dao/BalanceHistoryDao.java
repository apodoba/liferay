package com.payment.dao;

import java.math.BigDecimal;
import java.util.Date;

public interface BalanceHistoryDao {
	
	public void insertHistory(long userId, BigDecimal value, Date date, String operation);

}
