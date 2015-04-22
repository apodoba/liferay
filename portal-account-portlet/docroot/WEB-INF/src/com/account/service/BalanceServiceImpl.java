package com.account.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.account.dao.BalanceDao;
import com.account.dao.BalanceHistoryDao;
import com.account.exception.EmptyFieldException;
import com.portal.domen.Balance;
import com.portal.domen.BalanceHistory;
import com.portal.domen.Payment;

@Service
public class BalanceServiceImpl implements BalanceService {

	private static final String PAYMENT_OPERATION = "increase balance";

	@Autowired
	BalanceDao balanceDao;
	
	@Autowired
	BalanceHistoryDao historyDao;
	
	@Override
	public List<BalanceHistory> getAllHistory(long userId) {
		return historyDao.getAllHistory(userId);
	}
	
	@Override
	@Transactional
	public void addBalance(Payment payment) throws EmptyFieldException {
		if (payment.getUserId() == 0 || payment.getValue() == null || payment.getValue().intValue() <= 0) {
			throw new EmptyFieldException();
		}
		BigDecimal balance = balanceDao.getBalance(payment.getUserId()).getValue();
		balance = balance.add(payment.getValue());
		balanceDao.updateBalance(payment.getUserId(), balance);
		historyDao.insertHistory(payment.getUserId(), payment.getValue(), new Date(), PAYMENT_OPERATION);
	}
	
	@Override
	public BigDecimal getBalanceValue(long userId){
		Balance balance = balanceDao.getBalance(userId);
		return balance.getValue();
	}

}
