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
		String sql = "Select * from Reimbursment where reimb_id=?";
		
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
		
		return reimbursements;
	}

	@Override
	public Reimbursement insertElement(Reimbursement element) {
		
		Connection connection = config.getConnection();
		String sql = "INSERT INTO public.reimbursement "
				+ "(reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, reimb_author, reimb_resolver, "
				+ "reimb_status_id, reimb_type_id)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, element.getAmount());
			preparedStatement.setString(2, element.getSubmitted());
			preparedStatement.setString(3, element.getResolved());
			preparedStatement.setString(4, element.getDescription());
			preparedStatement.setString(5, element.getReceipt());
			preparedStatement.setInt(6, element.getAuthor().getId());
			preparedStatement.setInt(7, element.getResolver().getId()==0?null:element.getResolver().getId());
			preparedStatement.setInt(8, element.getStatus().getId());
			preparedStatement.setInt(9, element.getType().getId());
			
			preparedStatement.executeUpdate();
						
		}
		catch(Exception e) {
			element = null;
		}
		return element;
		
	}

	@Override
	public Reimbursement updateElement(Reimbursement element) {
		Connection connection = config.getConnection();
		String sql = "UPDATE public.reimbursement "
					+ "SET reimb_amount=?, reimb_submitted=?, reimb_resolved=?, reimb_description=?, "
					+ "reimb_receipt=?, reimb_author=?, reimb_resolver=?, reimb_status_id=?, reimb_type_id=? "
					+ "WHERE reimb_id=?";

		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, element.getAmount());
			preparedStatement.setString(2, element.getSubmitted());
			preparedStatement.setString(3, element.getResolved());
			preparedStatement.setString(4, element.getDescription());
			preparedStatement.setString(5, element.getReceipt());
			preparedStatement.setInt(6, element.getAuthor().getId());
			preparedStatement.setInt(7, element.getResolver().getId()==0?null:element.getResolver().getId());
			preparedStatement.setInt(8, element.getStatus().getId());
			preparedStatement.setInt(9, element.getType().getId());
			preparedStatement.setInt(10, element.getId());
			
			
			preparedStatement.executeUpdate();
						
		}
		catch(Exception e) {
			element = null;
		}
		return element;

	}

	@Override
	public boolean deleteElement(int id) {
		Connection connection = config.getConnection();
		boolean success=true;
		String sql = "DELETE FROM public.reimbursement WHERE reimb_id=?";

		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			
			
			preparedStatement.executeUpdate();
						
		}
		catch(Exception e) {
			success = false;
		}
		return success;
	}
	
	
	private ReimbursementStatus getStatus(int id) {
		

		Connection connection = config.getConnection();
		ReimbursementStatus reimbursementStatus =new ReimbursementStatus();
		String sql = "Select * from Reimbursment_status where reimb_status_id=?";
		
		try {
			PreparedStatement preparesStatement = connection.prepareStatement(sql);
			preparesStatement.setInt(1, id);
			
			ResultSet rs = preparesStatement.executeQuery();
			
			while(rs.next()) {
				reimbursementStatus = new ReimbursementStatus(rs.getInt(1),rs.getString(2));
			}
			
		}catch (Exception e) {
			reimbursementStatus = null;
			
		}
		
		return reimbursementStatus;
	}

	private ReimbursementType getType(int id) {
		Connection connection = config.getConnection();
		ReimbursementType reimbursementType =new ReimbursementType();
		String sql = "Select * from Reimbursment_type where reimb_type_id=?";
		
		try {
			PreparedStatement preparesStatement = connection.prepareStatement(sql);
			preparesStatement.setInt(1, id);
			
			ResultSet rs = preparesStatement.executeQuery();
			
			while(rs.next()) {
				reimbursementType = new ReimbursementType(rs.getInt(1),rs.getString(2));
			}

			
		}catch (Exception e) {
			reimbursementType = null;
			
		}
		
		return reimbursementType;
	}
	


}
