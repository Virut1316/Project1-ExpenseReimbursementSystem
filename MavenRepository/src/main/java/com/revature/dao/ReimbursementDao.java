package com.revature.dao;

import java.io.Console;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.dao.util.ConnectionConfig;
import com.revature.logger.LoggerManager;
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
		String sql = "Select * from Reimbursement where reimb_id=?";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			
			LoggerManager.logger.debug(preparedStatement.toString());
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				
				User resolver = null;
				int authorId=rs.getInt(8);
				if(!rs.wasNull())
					resolver = userDao.getElement(authorId);
				
				reimbursement = new Reimbursement(rs.getInt(1), rs.getInt(2), rs.getString(3), 
												 rs.getString(4), rs.getString(5), rs.getString(6), 
												 userDao.getElement(rs.getInt(7)), resolver, getStatus(rs.getInt(9)), getType(rs.getInt(10)));
				
			}
			
			connection.close();
		}
		catch(Exception e) {
			
			reimbursement = null;
			LoggerManager.logger.debug(e);
		}
		
		
		
		return reimbursement;
	}
	

	@Override
	public List<Reimbursement> getAllElements() {
		Connection connection = config.getConnection();
		List<Reimbursement> reimbursements =new ArrayList<Reimbursement>();
		UserDao userDao = new UserDao();
		String sql = "Select * from Reimbursement";
		LoggerManager.logger.debug(sql);
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
			connection.close();
		}
		catch(Exception e) {
			reimbursements = null;
			LoggerManager.logger.debug(e);
		}
		
		return reimbursements;
	}

	@Override
	public Reimbursement insertElement(Reimbursement element) {
		
		Connection connection = config.getConnection();
		String sql = "INSERT INTO public.reimbursement "
				+ "(reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, reimb_author, reimb_resolver, "
				+ "reimb_status_id, reimb_type_id)VALUES(?, TO_TIMESTAMP(?,'MM-DD-YYYY HH24:MI:SS'), ?, ?, ?, ?, ?, ?, ?)";

		//TO_TIMESTAMP(?,'MM-DD-YYYY HH:MI:SS')
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, element.getAmount());
			preparedStatement.setString(2, element.getSubmitted());
			preparedStatement.setString(3, element.getResolved());
			preparedStatement.setString(4, element.getDescription());
			preparedStatement.setString(5, element.getReceipt());
			preparedStatement.setInt(6, element.getAuthor().getId());
			preparedStatement.setObject(7, element.getResolver().getId()==0?null:element.getResolver().getId());
			preparedStatement.setInt(8, element.getStatus().getId());
			preparedStatement.setInt(9, element.getType().getId());
			
			LoggerManager.logger.debug(preparedStatement.toString());
			int state =preparedStatement.executeUpdate();
			
			if(state == 0)
				element = new Reimbursement();
			
			connection.close();
		}
		catch(Exception e) {
			element = null;
			LoggerManager.logger.debug(e);
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
		String sql = "DELETE FROM public.reimbursement WHERE reimb_id=?";

		
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
	
	
	public ReimbursementStatus getStatus(int id) {
		

		Connection connection = config.getConnection();
		ReimbursementStatus reimbursementStatus =new ReimbursementStatus();
		String sql = "Select * from Reimbursement_status where reimb_status_id=?";
		
		try {
			PreparedStatement preparesStatement = connection.prepareStatement(sql);
			preparesStatement.setInt(1, id);
			
			LoggerManager.logger.debug(preparesStatement.toString());
			ResultSet rs = preparesStatement.executeQuery();
			
			while(rs.next()) {
				reimbursementStatus = new ReimbursementStatus(rs.getInt(1),rs.getString(2));
			}
			
			connection.close();
		}catch (Exception e) {
			reimbursementStatus = null;
			LoggerManager.logger.debug(e);
		}
		
		return reimbursementStatus;
	}

	public ReimbursementType getType(int id) {
		Connection connection = config.getConnection();
		ReimbursementType reimbursementType =new ReimbursementType();
		String sql = "Select * from Reimbursement_type where reimb_type_id=?";
		
		try {
			PreparedStatement preparesStatement = connection.prepareStatement(sql);
			preparesStatement.setInt(1, id);
			
			LoggerManager.logger.debug(preparesStatement.toString());
			ResultSet rs = preparesStatement.executeQuery();
			
			while(rs.next()) {
				reimbursementType = new ReimbursementType(rs.getInt(1),rs.getString(2));
			}

			connection.close();
		}catch (Exception e) {
			reimbursementType = null;
			LoggerManager.logger.debug(e);
			
		}
		
		
		return reimbursementType;
	}
	
	public List<Reimbursement> getAllElements(int aId) {
		Connection connection = config.getConnection();
		List<Reimbursement> reimbursements =new ArrayList<Reimbursement>();
		UserDao userDao = new UserDao();
		String sql = "Select * from Reimbursement where reimb_author=?";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, aId);
			
			LoggerManager.logger.debug(preparedStatement.toString());
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				User author = null;
				int authorId=rs.getInt(8);
				if(!rs.wasNull())
					author = userDao.getElement(authorId);
					
				
				reimbursements.add(new Reimbursement(rs.getInt(1), rs.getInt(2), rs.getString(3), 
												 rs.getString(4), rs.getString(5), rs.getString(6), 
												 userDao.getElement(rs.getInt(7)), author, getStatus(rs.getInt(9)), getType(rs.getInt(10))));
			}
			
			connection.close();
		}
		catch(Exception e) {
			reimbursements = null;
			LoggerManager.logger.debug(e);
		}
		
		return reimbursements;
	}
	
	public List<Reimbursement> getAllPendingElements(int aId) {
		Connection connection = config.getConnection();
		List<Reimbursement> reimbursements =new ArrayList<Reimbursement>();
		UserDao userDao = new UserDao();
		String sql = "Select * from Reimbursement where reimb_author=? AND reimb_status_id=1";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, aId);
			
			LoggerManager.logger.debug(preparedStatement.toString());
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				User author = null;
				int authorId=rs.getInt(8);
				if(!rs.wasNull())
					author = userDao.getElement(authorId);
					
				
				reimbursements.add(new Reimbursement(rs.getInt(1), rs.getInt(2), rs.getString(3), 
												 rs.getString(4), rs.getString(5), rs.getString(6), 
												 userDao.getElement(rs.getInt(7)), author, getStatus(rs.getInt(9)), getType(rs.getInt(10))));
			}
			connection.close();
		}
		catch(Exception e) {
			LoggerManager.logger.debug(e);
			reimbursements = null;
		}
		
		return reimbursements;
	}
	
	public List<Reimbursement> getAllResolvedElements(int aId) {
		Connection connection = config.getConnection();
		List<Reimbursement> reimbursements =new ArrayList<Reimbursement>();
		UserDao userDao = new UserDao();
		String sql = "Select * from Reimbursement where reimb_author=? AND reimb_status_id<>1";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, aId);
			
			LoggerManager.logger.debug(preparedStatement.toString());
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				User author = null;
				int authorId=rs.getInt(8);
				if(!rs.wasNull())
					author = userDao.getElement(authorId);
					
				
				reimbursements.add(new Reimbursement(rs.getInt(1), rs.getInt(2), rs.getString(3), 
												 rs.getString(4), rs.getString(5), rs.getString(6), 
												 userDao.getElement(rs.getInt(7)), author, getStatus(rs.getInt(9)), getType(rs.getInt(10))));
			}
			connection.close();
		}
		catch(Exception e) {
			LoggerManager.logger.debug(e);
			reimbursements = null;
		}
		
		return reimbursements;
	}
	
	public List<Reimbursement> getAllResolvedElements() {
		Connection connection = config.getConnection();
		List<Reimbursement> reimbursements =new ArrayList<Reimbursement>();
		UserDao userDao = new UserDao();
		String sql = "Select * from Reimbursement where reimb_status_id<>1";
		LoggerManager.logger.debug(sql);
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
			connection.close();
		}
		catch(Exception e) {
			LoggerManager.logger.debug(e);
			reimbursements = null;
		}
		
		return reimbursements;
	}
	
	public List<Reimbursement> getAllPendingElements() {
		Connection connection = config.getConnection();
		List<Reimbursement> reimbursements =new ArrayList<Reimbursement>();
		UserDao userDao = new UserDao();
		String sql = "Select * from Reimbursement where reimb_status_id=1";
		LoggerManager.logger.debug(sql);
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
			connection.close();
		}
		catch(Exception e) {
			LoggerManager.logger.debug(e);
			reimbursements = null;
		}
		
		return reimbursements;
	}

}
