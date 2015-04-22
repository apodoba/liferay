package com.payment.domen;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Statistic {

	private int statisticId;
	private long userId;
	private double value;
	private Service service;
	private int month;
	private int year;
	private BigDecimal price;

	public Statistic() {
	}

	public Statistic(int statisticId, long userId, double value, int month, int year, BigDecimal price) {
		this.statisticId = statisticId;
		this.value = value;
		this.month = month;
		this.year = year;
		this.price = price;
	}

	public int getStatisticId() {
		return statisticId;
	}

	public void setStatisticId(int statisticId) {
		this.statisticId = statisticId;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		Statistic statistic = (Statistic) obj;
		return new EqualsBuilder()
				.appendSuper(super.equals(obj))
				.append(this.value, statistic.getValue())
				.append(this.userId, statistic.getUserId())
				.append(this.getService().getId(), statistic.getService().getId())
				.append(this.month, statistic.getMonth())
				.append(this.getYear(), statistic.getYear())
				.append(this.getPrice(), statistic.getPrice()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(this.statisticId)
				.append(this.userId)
				.append(this.value)
				.append(this.month)
				.append(this.year)
				.append(this.price)
				.toHashCode();
	}

}
