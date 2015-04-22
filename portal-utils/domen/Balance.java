package com.payment.domen;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Balance {

	private int balanceId;
	private long userId;
	private BigDecimal value;
	
	public Balance() {
	}
	
	public Balance(int balanceId, long userId, BigDecimal value) {
		this.setBalanceId(balanceId);
		this.setUserId(userId);
		this.setValue(value);
	}

	public int getBalanceId() {
		return balanceId;
	}

	public void setBalanceId(int balanceId) {
		this.balanceId = balanceId;
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
		Balance balance = (Balance) obj;
		return new EqualsBuilder()
				.appendSuper(super.equals(obj))
				.append(this.value, balance.getValue())
				.append(this.userId, balance.getUserId())
				.append(this.balanceId, balance.getBalanceId()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(this.balanceId)
				.append(this.userId)
				.append(this.value)
				.toHashCode();
	}
	
}
