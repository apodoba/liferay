package com.counter.dao;

import java.math.BigDecimal;
import java.util.List;

import com.portal.domen.ServiceType;
import com.portal.domen.Statistic;

public interface StatisticDao {

	public List<Statistic> getAllStatisticByUser(long userId);
	
	public List<Statistic> getAllStatisticByPeriod(long userId, int month, int year);
		
	public void addStatistic(Statistic statistic);
	
	public void updateStatisticValue(Statistic statistic);
	
	public Statistic getStatistic(ServiceType service, long userId, int year, int month);
	
	public BigDecimal getPeriodPrice(long userId, int year, int month);
}
