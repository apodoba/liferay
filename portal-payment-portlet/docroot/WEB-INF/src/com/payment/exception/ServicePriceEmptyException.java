package com.payment.exception;

public class ServicePriceEmptyException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7451566211164970099L;
	private static String ERROR = "Price is zero";
	
	public ServicePriceEmptyException() {
		super(ERROR);
	}

}
