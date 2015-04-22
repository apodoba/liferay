package com.payment.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payment.dao.StatisticDao;

@Service
public class StatisticSterviceImpl implements StatisticService{
	
	@Autowired
	StatisticDao statisticDao;

	@Override
	@Transactional
	public void updateStatisticPrice(BigDecimal price, int serviceId, int month, int year) {
		statisticDao.updateStatisticPrice(price, serviceId, month, year);
	}
}
