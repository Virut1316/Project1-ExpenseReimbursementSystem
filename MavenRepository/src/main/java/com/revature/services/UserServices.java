package com.revature.services;

import com.revature.dao.UserDao;
import com.revature.exceptions.DatabaseConnectionFailedException;
import com.revature.exceptions.IncorrectPasswordException;
import com.revature.exceptions.UserNotFoundException;
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
	
}
