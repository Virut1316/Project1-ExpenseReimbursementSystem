package com.revature.users.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.dao.ReimbursementDao;
import com.revature.dao.UserDao;
import com.revature.exceptions.DatabaseConnectionFailedException;
import com.revature.exceptions.FailedToMakeRequestException;
import com.revature.exceptions.NotReimbursementsException;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.services.ReimbursementServices;
import com.revature.services.UserServices;

public class ReimbursementServiceTest {

	@InjectMocks
	public ReimbursementServices rServ;
	
	@Mock
	public ReimbursementDao rDao;
	
	
	@Before
	public void iniMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void createNewRequestTest(){ 
		Reimbursement r = new Reimbursement(1, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType());
		when(rDao.insertElement(any())).thenReturn(r);
		boolean result;
		try {
			
			Reimbursement r2 = rServ.createNewReimbursmentRequest(r);
			result = true;
			
		}catch (FailedToMakeRequestException e) {
			result = false;
		}catch (DatabaseConnectionFailedException e) {
			result = false;
		}


		assertEquals(result, true);
	}
	
	@Test
	public void createNewRequestFailTest(){ 
		Reimbursement r = new Reimbursement(1, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType());
		when(rDao.insertElement(any())).thenReturn(new Reimbursement());
		boolean result;
		try {
			
			Reimbursement r2 = rServ.createNewReimbursmentRequest(r);
			result = true;
			
		}catch (FailedToMakeRequestException e) {
			result = false;


		assertEquals(result, false);
		}
	}
	
	@Test
	public void createNewRequestFailDatabaseTest(){ 
		Reimbursement r = new Reimbursement(1, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType());
		when(rDao.insertElement(any())).thenReturn(null);
		boolean result;
		try {
			
			Reimbursement r2 = rServ.createNewReimbursmentRequest(r);
			result = true;
			
		}catch (DatabaseConnectionFailedException e) {
			result = false;
		}


		assertEquals(result, false);
	}
	
	@Test
	public void getPendingReimbursementTest(){ 
		ArrayList<Reimbursement> r = new ArrayList<Reimbursement>();
				
		r.add(new Reimbursement(1, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(2, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(3, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(4, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(5, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));

		
		when(rDao.getAllPendingElements(anyInt())).thenReturn(r);
		boolean result;
		try {
			
			ArrayList<Reimbursement> r2 = (ArrayList<Reimbursement>) rServ.getPendingReimbursement(1);
			result = true;
			
		}catch (FailedToMakeRequestException e) {
			result = false;
		}catch (DatabaseConnectionFailedException e) {
			result = false;
		}


		assertEquals(result, true);
	}
	
	@Test
	public void getPendingReimbursementFailTest(){ 
		ArrayList<Reimbursement> r = new ArrayList<Reimbursement>();
				
		r.add(new Reimbursement(1, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(2, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(3, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(4, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(5, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));

		
		when(rDao.getAllPendingElements(anyInt())).thenReturn(new ArrayList<Reimbursement>());
		boolean result;
		try {
			
			ArrayList<Reimbursement> r2 = (ArrayList<Reimbursement>) rServ.getPendingReimbursement(1);
			result = true;
			
		}catch (NotReimbursementsException e) {
			result = false;



		assertEquals(result, false);
		}	
	}
	
	@Test
	public void getPendingReimbursementFailDatabaseTest(){ 
		ArrayList<Reimbursement> r = new ArrayList<Reimbursement>();
				
		r.add(new Reimbursement(1, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(2, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(3, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(4, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(5, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));

		
		when(rDao.getAllPendingElements(anyInt())).thenReturn(null);
		boolean result;
		try {
			
			ArrayList<Reimbursement> r2 = (ArrayList<Reimbursement>) rServ.getPendingReimbursement(1);
			result = true;
			
		}catch (DatabaseConnectionFailedException e) {
			result = false;
		}



		assertEquals(result, false);
		}	
	
	
	@Test
	public void getResolvedReimbursementTest(){ 
		ArrayList<Reimbursement> r = new ArrayList<Reimbursement>();
				
		r.add(new Reimbursement(1, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(2, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(3, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(4, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(5, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));

		
		when(rDao.getAllResolvedElements(anyInt())).thenReturn(r);
		boolean result;
		try {
			
			ArrayList<Reimbursement> r2 = (ArrayList<Reimbursement>) rServ.getResolvedReimbursement(1);
			result = true;
			
		}catch (FailedToMakeRequestException e) {
			result = false;
		}catch (DatabaseConnectionFailedException e) {
			result = false;
		}


		assertEquals(result, true);
	}
	
	@Test
	public void getResolvedReimbursementFailTest(){ 
		ArrayList<Reimbursement> r = new ArrayList<Reimbursement>();
				
		r.add(new Reimbursement(1, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(2, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(3, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(4, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(5, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));

		
		when(rDao.getAllResolvedElements(anyInt())).thenReturn(new ArrayList<Reimbursement>());
		boolean result;
		try {
			
			ArrayList<Reimbursement> r2 = (ArrayList<Reimbursement>) rServ.getResolvedReimbursement(1);
			result = true;
			
		}catch (NotReimbursementsException e) {
			result = false;



		assertEquals(result, false);
		}	
	}
	
	@Test
	public void getResolvedReimbursementFailDatabaseTest(){ 
		ArrayList<Reimbursement> r = new ArrayList<Reimbursement>();
				
		r.add(new Reimbursement(1, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(2, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(3, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(4, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(5, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));

		
		when(rDao.getAllResolvedElements(anyInt())).thenReturn(null);
		boolean result;
		try {
			
			ArrayList<Reimbursement> r2 = (ArrayList<Reimbursement>) rServ.getResolvedReimbursement(1);
			result = true;
			
		}catch (DatabaseConnectionFailedException e) {
			result = false;
		}



		assertEquals(result, false);
		}
	
	@Test
	public void approveDenyReimbTest(){ 
		Reimbursement r = new Reimbursement(1, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType());
		when(rDao.getElement(anyInt())).thenReturn(r);
		when(rDao.updateElement(any())).thenReturn(r);
		when(rDao.getStatus(anyInt())).thenReturn(new ReimbursementStatus(1,"Pending"));
		when(rDao.getType(anyInt())).thenReturn(new ReimbursementType(1,"Lodging"));
		boolean result;
		try {
			
			Reimbursement r2 = rServ.approveDenyReimbursement(r);
			result = true;
			
		}catch (NotReimbursementsException e) {
			result = false;
		}catch (DatabaseConnectionFailedException e) {
			result = false;
		}


		assertEquals(result, true);
	}
	
	@Test
	public void approveDenyReimbFailTest(){ 
		Reimbursement r = new Reimbursement(1, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType());
		when(rDao.getElement(anyInt())).thenReturn(new Reimbursement());
		when(rDao.updateElement(any())).thenReturn(r);
		when(rDao.getStatus(anyInt())).thenReturn(new ReimbursementStatus(1,"Pending"));
		when(rDao.getType(anyInt())).thenReturn(new ReimbursementType(1,"Lodging"));
		boolean result;
		try {
			
			Reimbursement r2 = rServ.approveDenyReimbursement(r);
			result = true;
			
		}catch (NotReimbursementsException e) {
			result = false;
		}catch (DatabaseConnectionFailedException e) {
			result = false;
		}


		assertEquals(result, false);
	}
	
	@Test
	public void approveDenyReimbFailDatabaseTest(){ 
		Reimbursement r = new Reimbursement(1, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType());
		when(rDao.getElement(anyInt())).thenReturn(null);
		when(rDao.updateElement(any())).thenReturn(r);
		when(rDao.getStatus(anyInt())).thenReturn(new ReimbursementStatus(1,"Pending"));
		when(rDao.getType(anyInt())).thenReturn(new ReimbursementType(1,"Lodging"));
		boolean result;
		try {
			
			Reimbursement r2 = rServ.approveDenyReimbursement(r);
			result = true;
			
		}catch (NotReimbursementsException e) {
			result = false;
		}catch (DatabaseConnectionFailedException e) {
			result = false;
		}


		assertEquals(result, false);
	}
	
	@Test
	public void getAllResolvedReimbTest(){ 
		ArrayList<Reimbursement> r = new ArrayList<Reimbursement>();
		
		r.add(new Reimbursement(1, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(2, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(3, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(4, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(5, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));


		when(rDao.getAllResolvedElements()).thenReturn(r);

		boolean result;
		try {
			
			ArrayList<Reimbursement> r2 = (ArrayList<Reimbursement>) rServ.getAllResolvedReimbursements();
			result = true;
			
		}catch (NotReimbursementsException e) {
			result = false;
		}catch (DatabaseConnectionFailedException e) {
			result = false;
		}


		assertEquals(result, true);
	}
	
	@Test
	public void getAllResolvedReimbFailTest(){ 
		ArrayList<Reimbursement> r = new ArrayList<Reimbursement>();
		

		when(rDao.getAllResolvedElements()).thenReturn(r);

		boolean result;
		try {
			
			ArrayList<Reimbursement> r2 = (ArrayList<Reimbursement>) rServ.getAllResolvedReimbursements();
			result = true;
			
		}catch (NotReimbursementsException e) {
			result = false;



		assertEquals(result, false);
	}}
	
	@Test
	public void getAllResolvedReimbFailDatabaseTest(){ 
		ArrayList<Reimbursement> r = null;
		

		when(rDao.getAllResolvedElements()).thenReturn(r);

		boolean result;
		try {
			
			ArrayList<Reimbursement> r2 = (ArrayList<Reimbursement>) rServ.getAllResolvedReimbursements();
			result = true;
			
		}catch (DatabaseConnectionFailedException e) {
			result = false;



		assertEquals(result, false);
	}
	}
	
	@Test
	public void getAllPendingReimbTest(){ 
		ArrayList<Reimbursement> r = new ArrayList<Reimbursement>();
		
		r.add(new Reimbursement(1, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(2, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(3, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(4, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));
		r.add(new Reimbursement(5, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType()));


		when(rDao.getAllPendingElements()).thenReturn(r);

		boolean result;
		try {
			
			ArrayList<Reimbursement> r2 = (ArrayList<Reimbursement>) rServ.getAllPendingReimbursements();
			result = true;
			
		}catch (NotReimbursementsException e) {
			result = false;
		}catch (DatabaseConnectionFailedException e) {
			result = false;
		}


		assertEquals(result, true);
	}

	@Test
	public void getAllPendingReimbFailTest(){ 
		ArrayList<Reimbursement> r = new ArrayList<Reimbursement>();
		

		when(rDao.getAllPendingElements()).thenReturn(r);

		boolean result;
		try {
			
			ArrayList<Reimbursement> r2 = (ArrayList<Reimbursement>) rServ.getAllPendingReimbursements();
			result = true;
			
		}catch (NotReimbursementsException e) {
			result = false;



		assertEquals(result, false);
	}
	}
	
	@Test
	public void getAllPendingReimbFailDatabaseTest(){ 
		ArrayList<Reimbursement> r = null;

		when(rDao.getAllPendingElements()).thenReturn(r);

		boolean result;
		try {
			
			ArrayList<Reimbursement> r2 = (ArrayList<Reimbursement>) rServ.getAllPendingReimbursements();
			result = true;

		}catch (DatabaseConnectionFailedException e) {
			result = false;
		}


		assertEquals(result, false);
	}
	
	@Test
	public void getReimbursementTest(){ 
		Reimbursement r = new Reimbursement(1, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType());
		when(rDao.getElement(anyInt())).thenReturn(r);
		boolean result;
		try {
			
			Reimbursement r2 = rServ.getReimbursement(r.getId());
			result = true;
			
		}catch (FailedToMakeRequestException e) {
			result = false;
		}catch (DatabaseConnectionFailedException e) {
			result = false;
		}


		assertEquals(result, true);
	}
	
	@Test
	public void getReimbursementFailTest(){ 
		Reimbursement r = new Reimbursement(1, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType());
		when(rDao.getElement(anyInt())).thenReturn(new Reimbursement());
		boolean result;
		try {
			
			Reimbursement r2 = rServ.getReimbursement(r.getId());
			result = true;
			
		}catch (NotReimbursementsException e) {
			result = false;



		assertEquals(result, false);
	}
	}

	@Test
	public void getReimbursementFailDatabaseTest(){ 
		Reimbursement r = new Reimbursement(1, 50000, "today", "tomorrow", "description", "receip", new User(), new User(),new ReimbursementStatus() , new ReimbursementType());
		when(rDao.getElement(anyInt())).thenReturn(null);
		boolean result;
		try {
			
			Reimbursement r2 = rServ.getReimbursement(r.getId());
			result = true;
			
		}catch (DatabaseConnectionFailedException e) {
			result = false;



		assertEquals(result, false);
	}
	}
}
