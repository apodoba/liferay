package com.payment.dao;

import java.math.BigDecimal;

import com.portal.domen.Balance;

public interface BalanceDao {
	
	public Balance getBalance(long userId); 
	public void updateBalance(long userId, BigDecimal value); 
}
