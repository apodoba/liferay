package com.payment.web;

import java.io.IOException;
import java.math.BigDecimal;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.payment.exception.BalanceException;
import com.payment.exception.EmptyFieldException;
import com.payment.exception.PaymentException;
import com.payment.exception.ServicePriceEmptyException;
import com.payment.notification.NotificationService;
import com.payment.service.PaymentService;
import com.portal.domen.Payment;
import com.portal.utils.SqlUtil;

@Controller
@RequestMapping("VIEW")
public class PaymentPortletController {

	private static final String VIEW = "view";
	private static final String SUCCEESS = "success";
	private static final String PAYMENT_ERROR = "paymentError";
	private static final String EMPTY_ERROR = "emptyError";
	private static final String BALANCE_ERROR = "balanceError";
	private static final String SERVICE_PRICE_ERROR = "servicePriceError";
	
	private static Logger LOGGER = Logger.getLogger(PaymentPortletController.class);
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private NotificationService notificationService;

	@RenderMapping
	public String handleViewRequest(RenderRequest renderRequest, RenderResponse renderResponse) {
		return VIEW;
	}

	@ActionMapping(params = "action=addPayment")
	public void addPayment(ActionRequest actionRequest, ActionResponse actionResponse, Model model) throws IOException, PortletException {
		Payment payment = new Payment();
		payment.setMonth(ParamUtil.get(actionRequest, SqlUtil.FIELD_MONTH, 0));
		payment.setYear(ParamUtil.get(actionRequest, SqlUtil.FIELD_YEAR, 0));
		payment.setUserId(getUser(actionRequest).getUserId());
		payment.setValue(BigDecimal.valueOf(ParamUtil.get(actionRequest, SqlUtil.FIELD_VALUE, 0)));
		try {
			paymentService.addPayment(payment);
			notificationService.sendPaymentEmail(getUser(actionRequest), payment);
			LOGGER.info("Successful payment");
			SessionMessages.add(actionRequest, SUCCEESS);
		} catch (PaymentException e) {
			SessionErrors.add(actionRequest, PAYMENT_ERROR);
			LOGGER.error(e.getMessage(), e);
		} catch (EmptyFieldException e) {
			SessionErrors.add(actionRequest, EMPTY_ERROR);
			LOGGER.error(e.getMessage(), e);
		} catch (BalanceException e) {
			SessionErrors.add(actionRequest, BALANCE_ERROR);
			LOGGER.error(e.getMessage(), e);
		} catch (ServicePriceEmptyException e) {
			SessionErrors.add(actionRequest, SERVICE_PRICE_ERROR);
			LOGGER.error(e.getMessage(), e);
		}
	}

	
	private static User getUser(PortletRequest request) {
		return (User) request.getAttribute(WebKeys.USER);
	}

}
