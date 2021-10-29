package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.dao.util.ConnectionConfig;
import com.revature.logger.LoggerManager;
import com.revature.models.User;
import com.revature.models.UserRole;

public class UserDao implements Dao<User>{

	private ConnectionConfig config = ConnectionConfig.getInstance();
	
	@Override
	public User getElement(int id) { //Should return null if no data is returned
		
		Connection connection = config.getConnection();
		User user =new User();
		String sql = "Select * from Users where users_id=?";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			
			LoggerManager.logger.debug(preparedStatement.toString());
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), getRole(rs.getInt(7)));
			}
			connection.close();
			
		}
		catch(Exception e) {
			user = null;
			LoggerManager.logger.debug(e);
		}
		
		return user;
	}


	@Override
	public List<User> getAllElements() {

		Connection connection = config.getConnection();
		List<User> user =new ArrayList<User>();
		String sql = "Select * from Users";
		LoggerManager.logger.debug(sql);
		
		try {
			Statement statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(sql);
			
			while(rs.next()) {
				user.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6), getRole(rs.getInt(7))));
			}
			
			connection.close();
		}
		catch(Exception e) {
			user = null;
			LoggerManager.logger.debug(e);
		}
		
		return user;

	}
	
	@Override
	public User insertElement(User element) {
		
		Connection connection = config.getConnection();
		String sql = "INSERT INTO public.users (username, \"password\", user_first_name, user_last_name, user_email, user_role_id) "
				+ "VALUES(?, ?, ?, ?, ?, ?)";

		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, element.getUsername());
			preparedStatement.setString(2, element.getPassword());
			preparedStatement.setString(3, element.getFirstName());
			preparedStatement.setString(4, element.getLastName());
			preparedStatement.setString(5, element.getEmail());
			preparedStatement.setInt(6, element.getUserRole().getId());
			
			LoggerManager.logger.debug(preparedStatement.toString());
			preparedStatement.executeUpdate();
						
			connection.close();
		}
		catch(Exception e) {
			element = null;
			LoggerManager.logger.debug(e);
		}
		return element;
	}

	@Override
	public User updateElement(User element) {
		Connection connection = config.getConnection();
		String sql = "UPDATE public.users SET username=?, \"password\"=?, user_first_name=?, "
				+ "user_last_name=?, user_email=?, user_role_id=? "
				+ "WHERE users_id=?";

		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, element.getUsername());
			preparedStatement.setString(2, element.getPassword());
			preparedStatement.setString(3, element.getFirstName());
			preparedStatement.setString(4, element.getLastName());
			preparedStatement.setString(5, element.getEmail());
			preparedStatement.setInt(6, element.getUserRole().getId());
			preparedStatement.setInt(7, element.getId());
			
			LoggerManager.logger.debug(preparedStatement.toString());
			preparedStatement.executeUpdate();
						
			connection.close();
		}
		catch(Exception e) {
			element = null;
			LoggerManager.logger.debug(e);
		}
		return element;
	}

	@Override
	public boolean deleteElement(int id) {
		Connection connection = config.getConnection();
		boolean success=true;
		String sql = "DELETE FROM public.users WHERE users_id=?";

		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			
			
			LoggerManager.logger.debug(preparedStatement.toString());
			preparedStatement.executeUpdate();
					
			connection.close();
		}
		catch(Exception e) {
			success = false;
			LoggerManager.logger.debug(e);
		}
		return success;

	}


	public UserRole getRole(int id) {
		Connection connection = config.getConnection();
		UserRole userRole =new UserRole();
		String sql = "Select * from user_roles where user_role_id=?";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			
			LoggerManager.logger.debug(preparedStatement.toString());
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				userRole = new UserRole(rs.getInt(1),rs.getString(2));
			}
			
			connection.close();
		}
		catch(Exception e) {
			userRole = null;
			LoggerManager.logger.debug(e);
		}
		
		return userRole;
	}
	
	public User getElement(String username) { //Should return null if no data is returned
		
		Connection connection = config.getConnection();
		User user =new User();
		String sql = "Select * from Users where username=?";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			
			LoggerManager.logger.debug(preparedStatement.toString());
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), getRole(rs.getInt(7)));
			}
			
			connection.close();
		}
		catch(Exception e) {
			user = null;
			LoggerManager.logger.debug(e);
		}
		
		return user;
	}


	public List<User> getAllEmployees() {
		
		Connection connection = config.getConnection();
		List<User> user =new ArrayList<User>();
		String sql = "Select * from Users where user_role_id=1";
		LoggerManager.logger.debug(sql);
		try {
			Statement statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(sql);
			
			while(rs.next()) {
				user.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6), getRole(rs.getInt(7))));
			}
			
			connection.close();
		}
		catch(Exception e) {
			user = null;
			LoggerManager.logger.debug(e);
		}
		
		return user;

	}


	public User getUserByEmail(String email) {
		Connection connection = config.getConnection();
		User user =new User();
		String sql = "Select * from Users where user_email=?";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, email);
			
			LoggerManager.logger.debug(preparedStatement.toString());
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), getRole(rs.getInt(7)));
			}
			
			connection.close();
		}
		catch(Exception e) {
			user = null;
			LoggerManager.logger.debug(e);
		}
		
		return user;
	}



}
