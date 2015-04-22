package com.weather.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherUtil {

	private static Weather weather;

	public static Weather getWeather() throws JSONException, IOException {
		updateWeather();
		return weather;
	}

	public static void updateWeather() throws JSONException, IOException {
		weather = new Weather();
		weather.setDate(new Date());
		
		JSONObject result = getWeatherResponse();
		fillMainInfo(result);
		fillWeatherDescription(result);
		fillWindInfo(result);
	}
	
	private static void fillMainInfo(JSONObject result) throws JSONException{
		JSONObject main = result.getJSONObject("main");
		BigDecimal tempValue = new BigDecimal(main.getDouble("temp"));
		int temp = tempValue.subtract(new BigDecimal(272.15)).setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
		int pressure = main.getInt("pressure");
		int humidity = main.getInt("humidity");

		weather.setTemperature(temp);
		weather.setPressure(pressure);
		weather.setHumidity(humidity);	
	}
	private static void fillWindInfo(JSONObject result) throws JSONException{
		JSONObject wind = result.getJSONObject("wind");
		double windSpeed = wind.getDouble("speed");

		weather.setWindSpeed(windSpeed);
	}
	private static void fillWeatherDescription(JSONObject result) throws JSONException{
		JSONArray weather = result.getJSONArray("weather");
		JSONObject weatherValue = weather.getJSONObject(0);
		String description = weatherValue.getString("description");

		WeatherUtil.weather.setDescription(description);
	}
	
	private static JSONObject getWeatherResponse() throws JSONException, IOException{
			/* TODO : I would recommend to put current location into properties file. */
			URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=Minsk,by");
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
			System.out.println(result);
			
			return result;
	}
	
	
	
	
	public static void main(String[] args) throws JSONException, IOException {
		updateWeather();
	}

}
