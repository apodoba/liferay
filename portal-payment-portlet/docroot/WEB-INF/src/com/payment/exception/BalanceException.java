package com.payment.exception;

public class BalanceException  extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3109529242879162772L;
	private static String ERROR = "Not enough money on balance";
	
	public BalanceException() {
		super(ERROR);
	}

}
