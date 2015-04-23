package com.account.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

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

import com.account.exception.EmptyFieldException;
import com.account.service.BalanceService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.portal.domen.BalanceHistory;
import com.portal.domen.Payment;
import com.portal.utils.SqlUtil;

@Controller
@RequestMapping("VIEW")
public class AccountPortletController {

	private static final String VIEW = "view";
	private static final String HISTORY = "history";
	private static final String BALANCE = "balance";
	
	private static Logger LOGGER = Logger.getLogger(AccountPortletController.class);

	@Autowired
	private BalanceService balanceService;
	
	@RenderMapping
	public String handleViewRequest(RenderRequest renderRequest, RenderResponse renderResponse) {
		List<BalanceHistory> balanceHistories = balanceService.getAllHistory(getUser(renderRequest).getUserId());
		BigDecimal balance = balanceService.getBalanceValue(getUser(renderRequest).getUserId());

		renderRequest.setAttribute(HISTORY, balanceHistories);
		renderRequest.setAttribute(BALANCE, balance);
		return VIEW;
	}

	@ActionMapping(params = "action=addBalance")
	public void addBalance(ActionRequest actionRequest, ActionResponse actionResponse, Model model) throws IOException, PortletException {
		Payment payment = new Payment();
		payment.setUserId(getUser(actionRequest).getUserId());
		payment.setValue(BigDecimal.valueOf(ParamUtil.get(actionRequest, SqlUtil.FIELD_VALUE, 0)));
		try {
			balanceService.addBalance(payment);
			SessionMessages.add(actionRequest, "success");
			LOGGER.info("Balance successfully increased");
		} catch (EmptyFieldException e) {
			SessionErrors.add(actionRequest, "emptyError");
			LOGGER.error(e.getMessage(), e);
		}
	}

	
	private static User getUser(PortletRequest request) {
		return (User) request.getAttribute(WebKeys.USER);
	}

}
