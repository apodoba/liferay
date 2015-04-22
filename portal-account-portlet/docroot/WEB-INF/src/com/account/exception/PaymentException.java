package com.account.exception;

public class PaymentException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -991898722870255156L;
	private static String ERROR = "Could not pay for future period";
	
	public PaymentException() {
		super(ERROR);
	}

}
