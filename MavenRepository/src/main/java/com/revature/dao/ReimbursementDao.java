package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.dao.util.ConnectionConfig;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.models.User;

public class ReimbursementDao implements Dao<Reimbursement>{

	private ConnectionConfig config = ConnectionConfig.getInstance();
	
	@Override
	public Reimbursement getElement(int id) {
		
		Connection connection = config.getConnection();
		Reimbursement reimbursement =new Reimbursement();
		UserDao userDao = new UserDao();
		String sql = "Select * from Reimbursment where id=?";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				
				User author = null;
				int authorId=rs.getInt(8);
				if(!rs.wasNull())
					author = userDao.getElement(authorId);
				
				reimbursement = new Reimbursement(rs.getInt(1), rs.getInt(2), rs.getString(3), 
												 rs.getString(4), rs.getString(5), rs.getString(6), 
												 userDao.getElement(rs.getInt(7)), author, getStatus(rs.getInt(9)), getType(rs.getInt(10)));
				

			}
			
			
		}
		catch(Exception e) {
			reimbursement = null;
		}
		
		
		
		return reimbursement;
	}
	

	@Override
	public List<Reimbursement> getAllElements() {
		
		Connection connection = config.getConnection();
		List<Reimbursement> reimbursements =new ArrayList<Reimbursement>();
		UserDao userDao = new UserDao();
		String sql = "Select * from Reimbursment";
		
		try {
			Statement statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(sql);
			
			while(rs.next()) {
				
				User author = null;
				int authorId=rs.getInt(8);
				if(!rs.wasNull())
					author = userDao.getElement(authorId);
					
				
				reimbursements.add(new Reimbursement(rs.getInt(1), rs.getInt(2), rs.getString(3), 
												 rs.getString(4), rs.getString(5), rs.getString(6), 
												 userDao.getElement(rs.getInt(7)), author, getStatus(rs.getInt(9)), getType(rs.getInt(10))));
				
			}
			
			
		}
		catch(Exception e) {
			reimbursements = null;
		}
		
		return null;
	}

	@Override
	public Reimbursement insertElement(Reimbursement element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reimbursement updateElement(Reimbursement element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteElement(int id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	private ReimbursementStatus getStatus(int int1) {
		// TODO Auto-generated method stub
		return null;
	}

	private ReimbursementType getType(int int1) {
		// TODO Auto-generated method stub
		return null;
	}
	


}
