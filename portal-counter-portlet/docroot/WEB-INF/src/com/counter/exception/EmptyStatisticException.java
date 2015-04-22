package com.counter.exception;

public class EmptyStatisticException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1568992766403887526L;
	private static String ERROR = "Fill all statistic fields";
	
	public EmptyStatisticException() {
		super(ERROR);
	}

}
