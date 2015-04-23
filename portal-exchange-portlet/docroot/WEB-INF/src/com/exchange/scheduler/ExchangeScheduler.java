package com.exchange.scheduler;

import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;

import com.exchange.cache.ExchangeRate;

public class ExchangeScheduler {
	
	private static Logger LOGGER = Logger.getLogger(ExchangeScheduler.class);

	@Scheduled(cron="${scheduler.period}")
	public void updateExchangeRates(){
		try {
			ExchangeRate.updateRates();
		} catch (SOAPException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}