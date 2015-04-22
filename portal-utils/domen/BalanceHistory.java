package com.payment.domen;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class BalanceHistory {

	private int historyId;
	private long userId;
	private BigDecimal value;
	private Date date;
	private String operation;
	
	public int getHistoryId() {
		return historyId;
	}
	public void setHistoryId(int historyId) {
		this.historyId = historyId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
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
		BalanceHistory balanceHistory = (BalanceHistory) obj;
		return new EqualsBuilder()
				.appendSuper(super.equals(obj))
				.append(this.value, balanceHistory.getValue())
				.append(this.userId, balanceHistory.getUserId())
				.append(this.historyId, balanceHistory.getHistoryId())
				.append(this.date, balanceHistory.getDate())
				.append(this.operation, balanceHistory.getOperation())
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(this.historyId)
				.append(this.userId)
				.append(this.value)
				.append(this.date)
				.append(this.operation)
				.toHashCode();
	}
	
}
