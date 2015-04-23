package com.weather.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.liferay.util.portlet.PortletProps;
import com.weather.web.WeatherPortletController;

public class WeatherUtil {
	
	private static Logger LOGGER = Logger.getLogger(WeatherPortletController.class);
	private static Weather weather;

	public static Weather getWeather() {
		try {
			weather = updateWeather();
		} catch (JSONException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return weather;
	}

	public static Weather updateWeather() throws JSONException, IOException {
		Weather weather = new Weather();
		weather.setDate(new Date());
		
		JSONObject result = getWeatherResponse();
		fillMainInfo(result, weather);
		fillWeatherDescription(result, weather);
		fillWindInfo(result, weather);
		return weather;
	}
	
	private static void fillMainInfo(JSONObject result, Weather weather) throws JSONException{
		JSONObject main = result.getJSONObject("main");
		BigDecimal tempValue = new BigDecimal(main.getDouble("temp"));
		int temp = tempValue.subtract(new BigDecimal(272.15)).setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
		int pressure = main.getInt("pressure");
		int humidity = main.getInt("humidity");

		weather.setTemperature(temp);
		weather.setPressure(pressure);
		weather.setHumidity(humidity);	
	}
	private static void fillWindInfo(JSONObject result, Weather weather) throws JSONException{
		JSONObject wind = result.getJSONObject("wind");
		double windSpeed = wind.getDouble("speed");

		weather.setWindSpeed(windSpeed);
	}
	private static void fillWeatherDescription(JSONObject result, Weather weather) throws JSONException{
		JSONArray weatherParams = result.getJSONArray("weather");
		JSONObject weatherValue = weatherParams.getJSONObject(0);
		String description = weatherValue.getString("description");

		weather.setDescription(description);
	}
	
	private static JSONObject getWeatherResponse() throws JSONException, IOException{
			URL url = new URL(PortletProps.get("weather.url"));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			StringBuilder content = new StringBuilder();
			String output;
			while ((output = br.readLine()) != null) {
				content.append(output);
			}

			conn.disconnect();
			JSONObject result = new JSONObject(content.toString());
			
			return result;
	}
	
	
	
	
	public static void main(String[] args) throws JSONException, IOException {
		updateWeather();
	}

}
