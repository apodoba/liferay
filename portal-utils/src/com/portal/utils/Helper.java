package com.portal.utils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

import com.portal.domen.Statistic;

public class Helper {

	public static SortedSet<Integer> getListOfYears(int period){
		SortedSet<Integer> years = new TreeSet<Integer>();

		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		for(int i=period; i>=0; i--){
			years.add(currentYear-i);
		}
		for(int i=1; i<=period; i++){
			years.add(currentYear+i);
		}
		
		return years;
	}
	
	public static int getCurrentYear(){
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	
	public static BigDecimal getPriceOfService(){
		Random random = new Random();
		BigDecimal result = new BigDecimal(random.nextInt(50000 - 5000) + 5000);
		return result;
	}
	
	public static BigDecimal getTotalPrice(List<Statistic> statistics){
		BigDecimal price = new BigDecimal(0);
		for(Statistic statistic: statistics){
			price = price.add(statistic.getPrice());
		}
		
		return price;
	}
}
