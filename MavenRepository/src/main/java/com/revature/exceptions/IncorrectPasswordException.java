package com.revature.exceptions;

public class IncorrectPasswordException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IncorrectPasswordException() {
		super("Incorrect password for user");
	}
}
