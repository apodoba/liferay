package com.portal.domen;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Payment {
	
	private int paymentId;
	private BigDecimal value;
	private long userId;
	private int month;
	private int year;
	
	public Payment() {
	}
	
	public Payment(int paymentId, BigDecimal value, int userId, int month, int year) {
		this.paymentId = paymentId;
		this.value = value;
		this.setMonth(month);
		this.setYear(year);
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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
		Payment payment = (Payment) obj;
		return new EqualsBuilder()
				.appendSuper(super.equals(obj))
				.append(this.value, payment.getValue())
				.append(this.userId, payment.getUserId())
				.append(this.paymentId, payment.getPaymentId())
				.append(this.month, payment.getMonth())
				.append(this.year, payment.getYear()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(this.paymentId)
				.append(this.userId)
				.append(this.value)
				.append(this.month)
				.append(this.year)
				.toHashCode();
	}

}
