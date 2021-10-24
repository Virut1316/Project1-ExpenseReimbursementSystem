package com.revature.services;

import java.util.ArrayList;
import java.util.List;

import com.revature.dao.ReimbursementDao;
import com.revature.exceptions.DatabaseConnectionFailedException;
import com.revature.exceptions.FailedToMakeRequestException;
import com.revature.exceptions.NotReimbursementsException;
import com.revature.models.Reimbursement;

public class ReimbursementServices {

	ReimbursementDao rDao;
	
	public ReimbursementServices(ReimbursementDao rDao) {
	
		this.rDao = rDao;
		
	}
	
	
	public Reimbursement createNewReimbursmentRequest(Reimbursement reimbursement) throws DatabaseConnectionFailedException, FailedToMakeRequestException {
	
		Reimbursement reimbursementResponse = rDao.insertElement(reimbursement);
		
		if(reimbursementResponse == null)
			throw new DatabaseConnectionFailedException();
		else if(reimbursementResponse.getAuthor() == null) 
			throw new FailedToMakeRequestException();
		
		return reimbursementResponse;
		
			
		
		
	}
	
	public List<Reimbursement> getPendingReimbursement(int authorId) throws DatabaseConnectionFailedException,NotReimbursementsException{
		
		ArrayList<Reimbursement> reimbursements = new ArrayList<Reimbursement>();
		
		reimbursements=(ArrayList<Reimbursement>) rDao.getAllPendingElements(authorId);
		
		if(reimbursements==null)
			throw new DatabaseConnectionFailedException();
		else if(reimbursements.size()<=0)
			throw new NotReimbursementsException();
		
		return reimbursements;
	}
	
	
	public List<Reimbursement> getResolvedReimbursement(int authorId){
		
		
		ArrayList<Reimbursement> reimbursements = new ArrayList<Reimbursement>();
		
		reimbursements=(ArrayList<Reimbursement>) rDao.getAllResolvedElements(authorId);
		
		if(reimbursements==null)
			throw new DatabaseConnectionFailedException();
		else if(reimbursements.size()<=0)
			throw new NotReimbursementsException();
		
		return reimbursements;
	}
	
	
}
