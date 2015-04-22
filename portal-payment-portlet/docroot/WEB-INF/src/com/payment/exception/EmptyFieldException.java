package com.payment.exception;

public class EmptyFieldException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1568992766403887526L;
	private static String ERROR = "Fill all statistic fields";
	
	public EmptyFieldException() {
		super(ERROR);
	}

}
