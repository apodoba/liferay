package com.notification.scheduler;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.notification.letter.NotificationService;
import com.notification.service.PaymentService;
import com.notification.service.StatisticService;
import com.portal.domen.Service;
import com.portal.utils.Helper;

public class NotificationScheduler {
	
	private static final int CABINET_ROLE_ID = 18888;

	@Autowired
	PaymentService paymentService;
	
	@Autowired
	StatisticService statisticService;
	
	@Autowired
	NotificationService notificationService;

	@Scheduled(cron = "0 00 00 16 * ?")
	public void sendNotification() throws SystemException {
		List<User> allUsers = UserLocalServiceUtil.getRoleUsers(CABINET_ROLE_ID);
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(Calendar.MONTH, -1);
		for(User user: allUsers){
			BigDecimal needToPay = paymentService.needToBePaid(user.getUserId(), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.YEAR));
			if(needToPay.compareTo(new BigDecimal(0)) == 1){
				notificationService.sendConfirmationEmail(user);
			}
		}
	}

	@Scheduled(cron = "0 00 00 20 * ?")
	public void updateStatisticPrice() {
		System.out.println("Scheduler");
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(Calendar.MONTH, 1);
		int year = currentDate.get(Calendar.YEAR);
		int month = currentDate.get(Calendar.MONTH);

		for (Service service : Service.values()) {
			BigDecimal price = Helper.getPriceOfService();
			statisticService.updateStatisticPrice(price, service.getId(), month, year);
		}
	}
}