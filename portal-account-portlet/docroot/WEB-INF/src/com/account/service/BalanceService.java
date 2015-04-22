package com.account.service;

import java.math.BigDecimal;
import java.util.List;

import com.account.exception.EmptyFieldException;
import com.portal.domen.BalanceHistory;
import com.portal.domen.Payment;


public interface BalanceService {
	
	public void addBalance(Payment payment) throws EmptyFieldException;
	
	public List<BalanceHistory> getAllHistory(long userId);
	
	public BigDecimal getBalanceValue(long userId);

}
