package com.account.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.portal.domen.BalanceHistory;

public interface BalanceHistoryDao {
	
	public void insertHistory(long userId, BigDecimal value, Date date, String operation);
	
	public List<BalanceHistory> getAllHistory(long userId);

}
