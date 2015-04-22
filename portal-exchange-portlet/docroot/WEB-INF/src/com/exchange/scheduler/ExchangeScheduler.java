package com.exchange.scheduler;

import javax.xml.soap.SOAPException;

import org.springframework.scheduling.annotation.Scheduled;

import com.exchange.cache.ExchangeRate;

public class ExchangeScheduler {
	/* TODO : I would recommend to put the values for 'cron' parameter into properties file. */
	@Scheduled(cron = "0 00 01 ? * *")
	public void updateExchangeRates() throws SOAPException {
		ExchangeRate.updateRates();
	}
}