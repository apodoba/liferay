package com.notification.scheduler;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.util.portlet.PortletProps;
import com.notification.letter.NotificationService;
import com.notification.service.PaymentService;
import com.notification.service.StatisticService;
import com.portal.domen.ServiceType;
import com.portal.utils.Helper;

public class NotificationScheduler {
	
	private static final String CABINET_ROLE_ID = "cabinet.role.id";
	private static Logger LOGGER = Logger.getLogger(NotificationScheduler.class);

	@Autowired
	PaymentService paymentService;
	
	@Autowired
	StatisticService statisticService;
	
	@Autowired
	NotificationService notificationService;

	@Scheduled(cron="${notification.scheduler.period}")
	public void sendNotification() {
		List<User> allUsers = null;
		try {
			allUsers = UserLocalServiceUtil.getRoleUsers(Integer.valueOf(PortletProps.get(CABINET_ROLE_ID)));
		} catch (NumberFormatException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (SystemException e) {
			LOGGER.error(e.getMessage(), e);
		}
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(Calendar.MONTH, -1);
		for(User user: allUsers){
			BigDecimal needToPay = paymentService.needToBePaid(user.getUserId(), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.YEAR));
			if(needToPay.compareTo(new BigDecimal(0)) == 1){
				notificationService.sendConfirmationEmail(user);
			}
		}
	}
	
	@Scheduled(cron="${database.scheduler.period}")
	public void updateStatisticPrice() {
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(Calendar.MONTH, 1);
		int year = currentDate.get(Calendar.YEAR);
		int month = currentDate.get(Calendar.MONTH);

		for (ServiceType service : ServiceType.values()) {
			BigDecimal price = Helper.getPriceOfService();
			statisticService.updateStatisticPrice(price, service.getId(), month, year);
		}
	}
}