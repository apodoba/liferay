package com.notification.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notification.dao.PaymentDao;
import com.notification.dao.StatisticDao;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	PaymentDao paymentDao;

	@Autowired
	StatisticDao statisticDao;

	@Override
	public BigDecimal needToBePaid(long userId, int month, int year) {
		BigDecimal realPrice = statisticDao.getPeriodPrice(userId, year, month);
		BigDecimal allPayments = paymentDao.getAllPayments(userId, month, year);
		BigDecimal toPay = realPrice.subtract(allPayments);
		return toPay;
		
	}

}
