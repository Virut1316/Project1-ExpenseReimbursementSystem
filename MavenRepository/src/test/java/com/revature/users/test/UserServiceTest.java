package com.revature.users.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.dao.UserDao;
import com.revature.exceptions.DatabaseConnectionFailedException;
import com.revature.exceptions.FailedToMakeRequestException;
import com.revature.exceptions.IncorrectPasswordException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.services.UserServices;

public class UserServiceTest {
		
	@InjectMocks
	public UserServices uServ;
	
	@Mock
	public UserDao uDao;
	
	
	@Before
	public void iniMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void correctLoginTest(){ 
		User u = new User(1, "usernameT", "passwordT", "firstnameT", "lastnameT", "email@test.com", new UserRole(1,"role to test"));
		
		when(uDao.getElement(anyString())).thenReturn(u);
		
		User userR = uServ.login("usernameT", "passwordT");
		
		boolean result;
		
		if(userR == null)
			result = false;
		else if (userR.getUsername()==null)
			result = false;
		else if (userR.getPassword() == u.getPassword())
			result = true;
		else 
			result = false;


		assertEquals(result, true);
	}
	
	@Test
	public void incorrectLoginTest(){ 
		User u = new User(1, "usernameT", "passwordT", "firstnameT", "lastnameT", "email@test.com", new UserRole(1,"role to test"));
		
		when(uDao.getElement(anyString())).thenReturn(u);
		User userR;
		boolean result = false;
		
		try {
			userR = uServ.login("usernameT", "passwordT2");	
		}
		catch(IncorrectPasswordException e) {
			result = true;
		}
		


		assertEquals(result, true);
	}

	@Test
	public void DatasbaseConnectionTest(){ 
	
		when(uDao.getElement(anyString())).thenReturn(null);
		User userR;
		boolean result = false;
		
		try {
			userR = uServ.login("usernameT", "passwordT2");	
		}
		catch(DatabaseConnectionFailedException e) {
			result = true;
		}
		


		assertEquals(result, true);
	}
	
	@Test
	public void UserDoesntExistTest(){ 
	
		when(uDao.getElement(anyString())).thenReturn(new User());
		User userR;
		boolean result = false;
		
		try {
			userR = uServ.login("usernameT", "passwordT2");	
		}
		catch(UserNotFoundException e) {
			result = true;
		}
		


		assertEquals(result, true);
	}
	
	@Test
	public void getAllEmployeesTest(){ 
	
		ArrayList<User> users = new ArrayList<User>();
		
		users.add(new User(1, "usernameT1", "passwordT", "firstnameT", "lastnameT", "email@test.com", new UserRole(1,"role to test")));
		users.add(new User(1, "usernameT2", "passwordT", "firstnameT", "lastnameT", "email@test.com", new UserRole(1,"role to test")));
		users.add(new User(1, "usernameT3", "passwordT", "firstnameT", "lastnameT", "email@test.com", new UserRole(1,"role to test")));
		users.add(new User(1, "usernameT4", "passwordT", "firstnameT", "lastnameT", "email@test.com", new UserRole(1,"role to test")));
		users.add(new User(1, "usernameT5", "passwordT", "firstnameT", "lastnameT", "email@test.com", new UserRole(1,"role to test")));
		
		
		
		when(uDao.getAllEmployees()).thenReturn(users);
		ArrayList<User> usersR;
		boolean result = false;
		
		try {
			usersR = uServ.getAllEmployees();
			if(usersR.size()>=1)
				result = true;
		}
		catch(Exception e) {
			result = false;
		}
		


		assertEquals(result, true);
	}
	
	
	@Test
	public void getAllEmployeesFailTest(){ 
	
		ArrayList<User> users = new ArrayList<User>();
		
		
		when(uDao.getAllEmployees()).thenReturn(users);
		ArrayList<User> usersR;
		boolean result = false;
		
		try {
			usersR = uServ.getAllEmployees();
			if(usersR.size()>=1)
				result = true;
		}
		catch(Exception e) {
			result = false;
		}
		


		assertEquals(result, false);
	}
	
	@Test
	public void getAllEmployeesDatabaseFailTest(){ 
	
		ArrayList<User> users = new ArrayList<User>();
		
		
		when(uDao.getAllEmployees()).thenReturn(null);
		ArrayList<User> usersR;
		boolean result = false;
		
		try {
			usersR = uServ.getAllEmployees();
			if(usersR.size()>=1)
				result = true;
		}
		catch(DatabaseConnectionFailedException e) {
			result = false;
		}
		


		assertEquals(result, false);
	}
	
	@Test
	public void updateUserInfoTest(){ 
		User u = new User(1, "usernameT", "passwordT", "firstnameT", "lastnameT", "email@test.com", new UserRole(1,"role to test"));
		User updated = new User(1, "usernameT", "passwordT", "firstnameT", "lastnameT", "email@test.com", new UserRole(1,"role to test"));

		when(uDao.getElement(anyInt())).thenReturn(u);
		when(uDao.updateElement(any())).thenReturn(updated);
		
		User userR ;
		
		boolean result;
		
		try {
			userR = uServ.updateUserInfo(u);
			result = true;
		}catch(Exception e) {
			result = false;
		}



		assertEquals(result, true);
	}
	
	@Test
	public void updateUserInfoFailTest(){ 
		User u = new User(1, "usernameT", "passwordT", "firstnameT", "lastnameT", "email@test.com", new UserRole(1,"role to test"));
		when(uDao.getElement(anyInt())).thenReturn(u);
		when(uDao.updateElement(any())).thenReturn(new User());
		
		User userR ;
		
		boolean result;
		
		try {
			userR = uServ.updateUserInfo(u);
			result = true;
		}catch(FailedToMakeRequestException e) {
			result = false;
		}



		assertEquals(result, false);
	}
	
	@Test
	public void updateUserInfoDatabaseFailTest(){ 
		User u = new User(1, "usernameT", "passwordT", "firstnameT", "lastnameT", "email@test.com", new UserRole(1,"role to test"));
		when(uDao.getElement(anyInt())).thenReturn(u);
		when(uDao.updateElement(any())).thenReturn(null);
		
		User userR ;
		
		boolean result;
		
		try {
			userR = uServ.updateUserInfo(u);
			result = true;
		}catch(DatabaseConnectionFailedException e) {
			result = false;
		}



		assertEquals(result, false);
	}
	
	@Test
	public void updateUserPasswordTest(){ 
		User u = new User(1, "usernameT", "passwordT", "firstnameT", "lastnameT", "email@test.com", new UserRole(1,"role to test"));
		User updated = new User(1, "usernameT", "passwordT", "firstnameT", "lastnameT", "email@test.com", new UserRole(1,"role to test"));

		when(uDao.getElement(anyInt())).thenReturn(u);
		when(uDao.updateElement(any())).thenReturn(updated);
		
		User userR ;
		
		boolean result;
		
		try {
			userR = uServ.updateUserPassword(u);
			result = true;
		}catch(Exception e) {
			result = false;
		}



		assertEquals(result, true);
	}
	
	@Test
	public void updateUserPasswordFailTest(){ 
		User u = new User(1, "usernameT", "passwordT", "firstnameT", "lastnameT", "email@test.com", new UserRole(1,"role to test"));
		when(uDao.getElement(anyInt())).thenReturn(u);
		when(uDao.updateElement(any())).thenReturn(new User());
		
		User userR ;
		
		boolean result;
		
		try {
			userR = uServ.updateUserPassword(u);
			result = true;
		}catch(FailedToMakeRequestException e) {
			result = false;
		}



		assertEquals(result, false);
	}
	
	@Test
	public void updateUserPasswordDatabaseFailTest(){ 
		User u = new User(1, "usernameT", "passwordT", "firstnameT", "lastnameT", "email@test.com", new UserRole(1,"role to test"));
		when(uDao.getElement(anyInt())).thenReturn(u);
		when(uDao.updateElement(any())).thenReturn(null);
		
		User userR ;
		
		boolean result;
		
		try {
			userR = uServ.updateUserPassword(u);
			result = true;
		}catch(DatabaseConnectionFailedException e) {
			result = false;
		}



		assertEquals(result, false);
	}
	
	@Test
	public void getUserTest(){ 
		User u = new User(1, "usernameT", "passwordT", "firstnameT", "lastnameT", "email@test.com", new UserRole(1,"role to test"));
		when(uDao.getElement(anyInt())).thenReturn(u);
		when(uDao.getElement(anyString())).thenReturn(u);
		when(uDao.getUserByEmail(anyString())).thenReturn(u);
		
		User userR,userR2,userR3;
		
		boolean result;
		
		try {
			userR = uServ.getUserByEmail(u.getEmail());
			userR2 = uServ.getUser(u.getId());
			userR3 = uServ.getUser(u.getUsername());
			
			if(userR==userR2&&userR==userR3)
				result = true;
			else
				result = false;
		}catch(Exception e) {
			result = false;
		}



		assertEquals(result, true);
	}
	
	@Test
	public void getUserIdFailTest(){ 
		User u = new User(1, "usernameT", "passwordT", "firstnameT", "lastnameT", "email@test.com", new UserRole(1,"role to test"));
		when(uDao.getElement(anyInt())).thenReturn(new User());

		
		User userR;
		
		boolean result;
		
		try {
			userR = uServ.getUser(u.getId());
			result = true;
		}catch(UserNotFoundException e) {
			result = false;
		}



		assertEquals(result, false);
	}
	
	@Test
	public void getUserUsernameFailTest(){ 
		User u = new User(1, "usernameT", "passwordT", "firstnameT", "lastnameT", "email@test.com", new UserRole(1,"role to test"));
		when(uDao.getElement(anyString())).thenReturn(new User());


		User userR;
		
		boolean result;
		
		try {
			userR = uServ.getUser(u.getUsername());
			result = true;
		}catch(UserNotFoundException e) {
			result = false;
		}




		assertEquals(result, false);
	}
	
	@Test
	public void getUserEmailFailTest(){ 
		User u = new User(1, "usernameT", "passwordT", "firstnameT", "lastnameT", "email@test.com", new UserRole(1,"role to test"));
		when(uDao.getUserByEmail(anyString())).thenReturn(new User());
		
		User userR;
		
		boolean result;
		
		try {
			userR = uServ.getUserByEmail(u.getEmail());
			
			result = true;
		}catch(UserNotFoundException e) {
			result = false;
		}


		assertEquals(result, false);
	}
	
	@Test
	public void getUserIdFailDatabaseTest(){ 
		User u = new User(1, "usernameT", "passwordT", "firstnameT", "lastnameT", "email@test.com", new UserRole(1,"role to test"));
		when(uDao.getElement(anyInt())).thenReturn(null);
		User userR;		
		boolean result;
		
		try {
			userR = uServ.getUser(u.getId());
			result = true;
		}catch(DatabaseConnectionFailedException e) {
			result = false;
		}



		assertEquals(result, false);
	}
	
	@Test
	public void getUserUsernameFailDatabaseTest(){ 
		User u = new User(1, "usernameT", "passwordT", "firstnameT", "lastnameT", "email@test.com", new UserRole(1,"role to test"));
		when(uDao.getElement(anyString())).thenReturn(null);


		User userR;
		
		boolean result;
		
		try {
			userR = uServ.getUser(u.getUsername());
			result = true;
		}catch(DatabaseConnectionFailedException e) {
			result = false;
		}




		assertEquals(result, false);
	}
	
	@Test
	public void getUserEmailFailDatabaseTest(){ 
		User u = new User(1, "usernameT", "passwordT", "firstnameT", "lastnameT", "email@test.com", new UserRole(1,"role to test"));
		when(uDao.getUserByEmail(anyString())).thenReturn(null);
		
		User userR;
		
		boolean result;
		
		try {
			userR = uServ.getUserByEmail(u.getEmail());
			result = true;
		}catch(DatabaseConnectionFailedException e) {
			result = false;
		}


		assertEquals(result, false);
	}
	
	
}
