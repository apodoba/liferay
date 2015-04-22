package com.payment.service;

import com.payment.exception.BalanceException;
import com.payment.exception.EmptyFieldException;
import com.payment.exception.PaymentException;
import com.payment.exception.ServicePriceEmptyException;
import com.portal.domen.Payment;

public interface PaymentService {
	
	public void addPayment(Payment payment) throws EmptyFieldException, PaymentException, BalanceException, ServicePriceEmptyException;

}
