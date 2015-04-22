package com.payment.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payment.dao.BalanceDao;
import com.payment.dao.BalanceHistoryDao;
import com.payment.dao.PaymentDao;
import com.payment.dao.StatisticDao;
import com.payment.exception.BalanceException;
import com.payment.exception.EmptyFieldException;
import com.payment.exception.PaymentException;
import com.payment.exception.ServicePriceEmptyException;
import com.portal.domen.Payment;

@Service
public class PaymentServiceImpl implements PaymentService {

	private static final String PAYMENT_OPERATION = "payment";

	@Autowired
	PaymentDao paymentDao;

	@Autowired
	BalanceDao balanceDao;
	
	@Autowired
	StatisticDao statisticDao;

	@Autowired
	BalanceHistoryDao historyDao;

	@Override
	@Transactional
	public void addPayment(Payment payment) throws EmptyFieldException, PaymentException, BalanceException, ServicePriceEmptyException {
		if (payment.getUserId() == 0 || payment.getMonth() == 0 || payment.getYear() == 0 || payment.getValue() == null || payment.getValue().intValue() <= 0) {
			throw new EmptyFieldException();
		}
		if (!canPay(payment)) {
			throw new PaymentException();
		}
		BigDecimal balance = balanceDao.getBalance(payment.getUserId()).getValue();
		if (balance.compareTo(payment.getValue()) == -1) {
			throw new BalanceException();
		}
		BigDecimal realPrice = statisticDao.getPeriodPrice(payment.getUserId(), payment.getYear(), payment.getMonth());
		if (realPrice.intValue()== 0) {
			throw new ServicePriceEmptyException();
		}
		if(realPrice.compareTo(payment.getValue()) == -1){
			payment.setValue(realPrice);
		}
		paymentDao.insertPayment(payment);
		balance = balance.subtract(payment.getValue());
		balanceDao.updateBalance(payment.getUserId(), balance);
		historyDao.insertHistory(payment.getUserId(), payment.getValue(), new Date(), PAYMENT_OPERATION);
	}

	private boolean canPay(Payment payment) {
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(Calendar.MONTH, 1);
		int year = currentDate.get(Calendar.YEAR);
		int month = currentDate.get(Calendar.MONDAY);
		if (payment.getYear() > year
				|| (payment.getYear() == year && payment.getMonth() > month)
				|| (payment.getYear() == year && payment.getMonth() == month && currentDate.get(Calendar.DATE) < 20)) {
			return false;
		}
		return true;
	}
}
