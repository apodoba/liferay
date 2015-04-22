package com.counter.service;

import java.util.List;

import com.counter.exception.EditStaticException;
import com.counter.exception.EmptyStatisticException;
import com.portal.domen.Statistic;

public interface StatisticService {

	public List<Statistic> getAllStatisticByUser(long userId);
	
	public List<Statistic> getAllStatisticByPeriod(long userId, int month, int year);
	
	public void addStatistic(Statistic statistic) throws EmptyStatisticException, EditStaticException;
}
