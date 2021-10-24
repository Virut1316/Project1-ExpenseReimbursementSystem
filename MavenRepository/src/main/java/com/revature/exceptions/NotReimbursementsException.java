package com.revature.exceptions;

public class NotReimbursementsException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public NotReimbursementsException() {
		super("There is not reimbursements in database");
	}

}
