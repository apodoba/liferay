package com.weather.scheduler;

import java.io.IOException;

import javax.xml.soap.SOAPException;

import org.json.JSONException;
import org.springframework.scheduling.annotation.Scheduled;

import com.weather.util.WeatherUtil;

public class WeatherScheduler {

	@Scheduled(cron = "0 00 01 ? * *")
	public void updateExchangeRates() throws SOAPException, JSONException, IOException {
		WeatherUtil.updateWeather();
	}
}