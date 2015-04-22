package com.counter.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.counter.dao.StatisticDao;
import com.counter.exception.EditStaticException;
import com.counter.exception.EmptyStatisticException;
import com.portal.domen.Statistic;

@Service
public class StatisticSterviceImpl implements StatisticService{
	
	@Autowired
	StatisticDao statisticDao;

	@Override
	public List<Statistic> getAllStatisticByUser(long userId) {
		return statisticDao.getAllStatisticByUser(userId);
	}
	
	@Override
	public List<Statistic> getAllStatisticByPeriod(long userId, int month, int year) {
		if(month == 0 && year == 0){
			return statisticDao.getAllStatisticByUser(userId);
		}else{
			return statisticDao.getAllStatisticByPeriod(userId, month, year);
		}
	}

	@Override
	public void addStatistic(Statistic statistic) throws EmptyStatisticException, EditStaticException{
		if(statistic == null || statistic.getMonth() == 0  || statistic.getYear() ==0 || statistic.getValue() <= 0 || statistic.getService() == null){
			throw new EmptyStatisticException();
		}
		if(!isEditable(statistic)){
			throw new EditStaticException();
		}
		Statistic existStatistic = statisticDao.getStatistic(statistic.getService(), statistic.getUserId(), statistic.getYear(), statistic.getMonth());
		if(existStatistic == null){
			statisticDao.addStatistic(statistic);
		}else{
			existStatistic.setValue(statistic.getValue());
			existStatistic.setPrice(statistic.getPrice());
			statisticDao.updateStatisticValue(existStatistic);
		}
	}
	
	private boolean isEditable(Statistic statistic){
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(Calendar.MONTH, 1);
		if(statistic.getYear() < currentDate.get(Calendar.YEAR) 
				|| (currentDate.get(Calendar.YEAR) == statistic.getYear() && currentDate.get(Calendar.MONTH) > statistic.getMonth())
				|| (currentDate.get(Calendar.YEAR) == statistic.getYear() && currentDate.get(Calendar.MONTH) == statistic.getMonth() && currentDate.get(Calendar.DATE)>=20)){
			return false;
		}else{
			return true;
		}
	}
}
