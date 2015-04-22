package com.notification.service;

import java.math.BigDecimal;

public interface PaymentService {
	
	public BigDecimal needToBePaid(long l, int month, int year);

}
