package com.revature.services;

import com.revature.dao.ReimbursementDao;
import com.revature.exceptions.DatabaseConnectionFailedException;
import com.revature.exceptions.FailedToMakeRequestException;
import com.revature.models.Reimbursement;

public class ReimbursementServices {

	ReimbursementDao rDao;
	
	public ReimbursementServices(ReimbursementDao rDao) {
	
		this.rDao = rDao;
		
	}
	
	
	public Reimbursement createNewReimbursmentRequest(Reimbursement reimbursement) throws DatabaseConnectionFailedException {
	
		Reimbursement reimbursementResponse = rDao.insertElement(reimbursement);
		
		if(reimbursementResponse == null)
			throw new DatabaseConnectionFailedException();
		else if(reimbursementResponse.getAuthor() == null) 
			throw new FailedToMakeRequestException();
		
		return reimbursementResponse;
		
			
		
		
	}
	
	
}
