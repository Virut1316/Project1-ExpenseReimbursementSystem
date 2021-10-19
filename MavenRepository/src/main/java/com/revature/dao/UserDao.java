package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.dao.util.ConnectionConfig;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.models.UserRole;

public class UserDao implements Dao<User>{

	private ConnectionConfig config = ConnectionConfig.getInstance();
	
	@Override
	public User getElement(int id) { //Should return null if no data is returned
		
		Connection connection = config.getConnection();
		User user =new User();
		String sql = "Select * from Users where id=?";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), getRole(rs.getInt(6)));
			}
			
			
		}
		catch(Exception e) {
			user = null;
		}
		
		return user;
	}


	@Override
	public List<User> getAllElements() {

		Connection connection = config.getConnection();
		List<User> user =new ArrayList<User>();
		String sql = "Select * from Users";
		
		try {
			Statement statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(sql);
			
			while(rs.next()) {
				user.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), getRole(rs.getInt(6))));
			}
			
			
		}
		catch(Exception e) {
			user = null;
		}
		
		return user;

	}
	
	@Override
	public User insertElement(User element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User updateElement(User element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteElement(int id) {
		// TODO Auto-generated method stub
		return false;
	}


	private UserRole getRole(int int1) {
		// TODO Auto-generated method stub
		return null;
	}


}
