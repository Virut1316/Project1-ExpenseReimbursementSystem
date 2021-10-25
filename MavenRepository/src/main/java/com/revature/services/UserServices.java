package com.revature.services;

import java.util.ArrayList;
import java.util.List;

import com.revature.dao.UserDao;
import com.revature.exceptions.DatabaseConnectionFailedException;
import com.revature.exceptions.FailedToMakeRequestException;
import com.revature.exceptions.IncorrectPasswordException;
import com.revature.exceptions.NotReimbursementsException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Reimbursement;
import com.revature.models.User;

public class UserServices {

	UserDao userDao;
	
	public UserServices(UserDao userDao) {
		this.userDao = userDao;
	}
	
	
	public User login(String username, String password) throws DatabaseConnectionFailedException,IncorrectPasswordException,UserNotFoundException{
		
		User user = userDao.getElement(username);
		
		if(user==null)
			throw new DatabaseConnectionFailedException();
		else if (user.getPassword()==null)
			throw new UserNotFoundException();
		else if (!user.getPassword().equals(password))
			throw  new IncorrectPasswordException();
		
		return user;
		
	}


	public ArrayList<User> getAllEmployees() throws DatabaseConnectionFailedException, UserNotFoundException{
		
		ArrayList<User> employees = new ArrayList<User>();
		
		employees=(ArrayList<User>) userDao.getAllEmployees();
		
		if(employees==null)
			throw new DatabaseConnectionFailedException();
		else if(employees.size()<=0)
			throw new UserNotFoundException();
		
		return employees;
		
	}


	public User updateUserInfo(User user) throws DatabaseConnectionFailedException,UserNotFoundException{
		
		User userInDB =  userDao.getElement(user.getId());
		
		user= userDao.updateElement(new User(user.getId(),user.getUsername(),userInDB.getPassword(),user.getFirstName(),user.getLastName(),user.getEmail(),userInDB.getUserRole()));
		
		if(user==null)
			throw new DatabaseConnectionFailedException();
		else if(user.getUsername()==null)
			throw new FailedToMakeRequestException();
		
		return user;
		
	}
	
	public User updateUserPassword(User user) throws DatabaseConnectionFailedException,UserNotFoundException{
		
		User userInDB =  userDao.getElement(user.getId());
		
		user= userDao.updateElement(new User(userInDB.getId(),userInDB.getUsername(),user.getPassword(),userInDB.getFirstName(),userInDB.getLastName(),userInDB.getEmail(),userInDB.getUserRole()));
		
		if(user==null)
			throw new DatabaseConnectionFailedException();
		else if(user.getUsername()==null)
			throw new FailedToMakeRequestException();
		
		return user;
		
	}
	
	public User getUser(int id) throws DatabaseConnectionFailedException,UserNotFoundException{
		
		User user =  userDao.getElement(id);
				
		if(user==null)
			throw new DatabaseConnectionFailedException();
		else if(user.getUsername()==null)
			throw new UserNotFoundException();
		
		return user;
		
	}
	
	
	public User getUser(String username) throws DatabaseConnectionFailedException,UserNotFoundException{
		
		User user =  userDao.getElement(username);
				
		if(user==null)
			throw new DatabaseConnectionFailedException();
		else if(user.getUsername()==null)
			throw new UserNotFoundException();
		
		return user;
		
	}
	
	public User getUserByEmail(String email) throws DatabaseConnectionFailedException,UserNotFoundException{
		
		User user =  userDao.getUserByEmail(email);
				
		if(user==null)
			throw new DatabaseConnectionFailedException();
		else if(user.getUsername()==null)
			throw new UserNotFoundException();
		
		return user;
		
	}
	
	
}
