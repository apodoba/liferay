package com.weather.scheduler;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.scheduling.annotation.Scheduled;

import com.weather.util.WeatherUtil;

public class WeatherScheduler {
	
	private static Logger LOGGER = Logger.getLogger(WeatherScheduler.class);
	
	@Scheduled(cron="${scheduler.period}")
	public void updateExchangeRates() {
		try {
			WeatherUtil.updateWeather();
		} catch (JSONException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}