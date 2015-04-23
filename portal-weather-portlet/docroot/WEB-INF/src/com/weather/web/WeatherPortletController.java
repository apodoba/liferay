package com.weather.web;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.weather.util.Weather;
import com.weather.util.WeatherUtil;

@Controller
@RequestMapping("VIEW")
public class WeatherPortletController {
	
	private static final String WEATHER = "weather";
	
	@RenderMapping
	public String handleViewRequest(RenderRequest renderRequest, RenderResponse renderResponse) {
		Weather weather = WeatherUtil.getWeather();
		if(weather != null){
			renderRequest.setAttribute(WEATHER, WeatherUtil.getWeather());
		}else{
			SessionErrors.add(renderRequest, "error");
		}
		return "view";
	}
}
