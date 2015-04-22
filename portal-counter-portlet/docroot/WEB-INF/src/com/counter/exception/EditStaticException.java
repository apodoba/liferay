package com.counter.exception;

public class EditStaticException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6803806377593845998L;
	private static String ERROR = "Could not edit statistic in the past";

	public EditStaticException() {
		super(ERROR);
	}

}