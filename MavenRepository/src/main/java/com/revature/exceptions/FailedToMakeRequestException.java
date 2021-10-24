package com.revature.exceptions;

public class FailedToMakeRequestException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FailedToMakeRequestException() {
		super("The operation failed, please try again later");
	}
	
}
