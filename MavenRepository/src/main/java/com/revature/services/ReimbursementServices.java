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
	
	
	public List<Reimbursement> getResolvedReimbursement(int authorId)  throws DatabaseConnectionFailedException,NotReimbursementsException{
		
		
		ArrayList<Reimbursement> reimbursements = new ArrayList<Reimbursement>();
		
		reimbursements=(ArrayList<Reimbursement>) rDao.getAllResolvedElements(authorId);
		
		if(reimbursements==null)
			throw new DatabaseConnectionFailedException();
		else if(reimbursements.size()<=0)
			throw new NotReimbursementsException();
		
		return reimbursements;
	}
	
	public Reimbursement approveDenyReimbursement(Reimbursement reimbursement) {
		
		Reimbursement reimbursementInDB = rDao.getElement(reimbursement.getId());
		
		if(reimbursementInDB==null)
			throw new DatabaseConnectionFailedException();
		else if(reimbursementInDB.getId()==0)
			throw new NotReimbursementsException();
		else
			reimbursement = rDao.updateElement(new Reimbursement(reimbursement.getId(), reimbursementInDB.getAmount(), 
						reimbursementInDB.getSubmitted(), reimbursement.getResolved(), 
						reimbursementInDB.getDescription(), reimbursementInDB.getReceipt(), 
						reimbursementInDB.getAuthor(), reimbursement.getResolver(), 
						rDao.getStatus(reimbursement.getStatus().getId()), rDao.getType(reimbursementInDB.getType().getId())));
		
		
		
		return reimbursement;
	}
	
	public List<Reimbursement> getAllResolvedReimbursements()  throws DatabaseConnectionFailedException,NotReimbursementsException{
		
		
		ArrayList<Reimbursement> reimbursements = new ArrayList<Reimbursement>();
		
		reimbursements=(ArrayList<Reimbursement>) rDao.getAllResolvedElements();
		
		if(reimbursements==null)
			throw new DatabaseConnectionFailedException();
		else if(reimbursements.size()<=0)
			throw new NotReimbursementsException();
		
		return reimbursements;
	}
	
	public List<Reimbursement> getAllPendingReimbursements()  throws DatabaseConnectionFailedException,NotReimbursementsException{
		
		
		ArrayList<Reimbursement> reimbursements = new ArrayList<Reimbursement>();
		
		reimbursements=(ArrayList<Reimbursement>) rDao.getAllPendingElements();
		
		if(reimbursements==null)
			throw new DatabaseConnectionFailedException();
		else if(reimbursements.size()<=0)
			throw new NotReimbursementsException();
		
		return reimbursements;
	}


	public Reimbursement getReimbursement(int rId) {
		
		Reimbursement reimbursementResponse = rDao.getElement(rId);
		
		if(reimbursementResponse == null)
			throw new DatabaseConnectionFailedException();
		else if(reimbursementResponse.getAuthor() == null) 
			throw new NotReimbursementsException();
		
		return reimbursementResponse;
	}
	
	
}
