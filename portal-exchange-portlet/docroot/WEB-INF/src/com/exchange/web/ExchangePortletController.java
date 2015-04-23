package com.exchange.web;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.exchange.cache.ExchangeRate;
import com.liferay.portal.kernel.servlet.SessionErrors;

@Controller
@RequestMapping("VIEW")
public class ExchangePortletController {
	
	private static final String EXCHANGE_RATES = "exchangeRates";
	private static final String ERROR = "error";
	
	private static Logger LOGGER = Logger.getLogger(ExchangePortletController.class);
	
	@RenderMapping
	public String handleViewRequest(RenderRequest renderRequest, RenderResponse renderResponse) {
		try {
			renderRequest.setAttribute(EXCHANGE_RATES, ExchangeRate.getExchangeRates());
		} catch (SOAPException e) {
			SessionErrors.add(renderRequest, ERROR);
			LOGGER.error(e.getMessage(), e);
		}
		return "view";
	}
}
