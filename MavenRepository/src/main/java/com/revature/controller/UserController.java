package com.revature.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.revature.dao.UserDao;
import com.revature.logger.LoggerManager;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.services.UserServices;

public class UserController {

	private static UserDao userDao = new UserDao();
	private static UserServices userServices = new UserServices(userDao);
	
	public static void getAllEmployees(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException{
		

		if(!req.getMethod().equals("GET")) {
			return;
		}
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			LoggerManager.logger.info("In the user controller: get all employees");
			ArrayList<User> users = userServices.getAllEmployees();
			res.setStatus(200);
			res.getWriter().write(new ObjectMapper().writeValueAsString(users));
			LoggerManager.logger.debug(users+" all employees from db");
		}catch (Exception e) {
			ObjectNode errorInfo = mapper.createObjectNode();
			res.setStatus(403);
			//errorInfo.put("code", 403);
			errorInfo.put("message", e.getMessage());
			res.getWriter().write((new ObjectMapper().writeValueAsString(errorInfo)));
			LoggerManager.logger.info("Problem while proccessing request"+e);
		}
	}
		
		public static void updateUserInfo(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException{
			
			StringBuilder buffer = new StringBuilder();
			BufferedReader reader = req.getReader();
			
			String line;
			while((line = reader.readLine()) != null) {
				buffer.append(line);
				buffer.append(System.lineSeparator());
			}
			
			String data = buffer.toString();
			LoggerManager.logger.info(data + "got from request");
			
			ObjectMapper mapper = new ObjectMapper();
			JsonNode parsedObj = mapper.readTree(data);
			
			int userId = Integer.parseInt(parsedObj.get("userId").asText());
			String username = parsedObj.get("username").asText();
			String firstName = parsedObj.get("first-name").asText();
			String lastName = parsedObj.get("last-name").asText();
			String email = parsedObj.get("email").asText();
			
						
			try {
				LoggerManager.logger.info("In the user controller: update user info");
				User user = userServices.updateUserInfo(new User(userId,username,null,firstName,lastName,email,null));
				res.setStatus(200);
				res.getWriter().write(new ObjectMapper().writeValueAsString(user));
				LoggerManager.logger.debug(user+"  info updated from user");
				
			}catch (Exception e) {
				ObjectNode errorInfo = mapper.createObjectNode();
				res.setStatus(403);
				errorInfo.put("code", 403);
				errorInfo.put("message", e.getMessage());
				res.getWriter().write((new ObjectMapper().writeValueAsString(errorInfo)));
				LoggerManager.logger.info("Problem while proccessing request"+e);
			}
		
	}
	
		public static void updatePassword(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException{
			
			StringBuilder buffer = new StringBuilder();
			BufferedReader reader = req.getReader();
			
			String line;
			while((line = reader.readLine()) != null) {
				buffer.append(line);
				buffer.append(System.lineSeparator());
			}
			
			String data = buffer.toString();
			LoggerManager.logger.info(data + "got from request");
			
			ObjectMapper mapper = new ObjectMapper();
			JsonNode parsedObj = mapper.readTree(data);
			
			int userId = Integer.parseInt(parsedObj.get("userId").asText());
			String newPassword = parsedObj.get("new-password").asText();
			
						
			try {
				LoggerManager.logger.info("In the user controller: update password");
				User user = userServices.updateUserPassword(new User(userId,null,newPassword,null,null,null,null));
				res.setStatus(200);
				res.getWriter().write(new ObjectMapper().writeValueAsString(user));
				LoggerManager.logger.debug(user+" password updated");
				
			}catch (Exception e) {
				ObjectNode errorInfo = mapper.createObjectNode();
				res.setStatus(403);
				errorInfo.put("code", 403);
				errorInfo.put("message", e.getMessage());
				res.getWriter().write((new ObjectMapper().writeValueAsString(errorInfo)));
				LoggerManager.logger.info("Problem while proccessing request"+e);
			}
		
	}
		
		
		
		public static void getUserById(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException{
			
			StringBuilder buffer = new StringBuilder();
			BufferedReader reader = req.getReader();
			
			String line;
			while((line = reader.readLine()) != null) {
				buffer.append(line);
				buffer.append(System.lineSeparator());
			}
			
			String data = buffer.toString();
			LoggerManager.logger.info(data + "got from request");
			
			ObjectMapper mapper = new ObjectMapper();
			JsonNode parsedObj = mapper.readTree(data);
			
			int userId = Integer.parseInt(parsedObj.get("userId").asText());			
						
			try {
				LoggerManager.logger.info("In the user controller: get user by id");
				User user = userServices.getUser(userId);
				res.setStatus(200);
				res.getWriter().write(new ObjectMapper().writeValueAsString(user));
				LoggerManager.logger.debug(user+" got with id");
				
			}catch (Exception e) {
				ObjectNode errorInfo = mapper.createObjectNode();
				res.setStatus(403);
				//errorInfo.put("code", 403);
				errorInfo.put("message", e.getMessage());
				res.getWriter().write((new ObjectMapper().writeValueAsString(errorInfo)));
				LoggerManager.logger.info("Problem while proccessing request"+e);
			}
		
	}
		
		public static void getUserByUsername(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException{
			
			StringBuilder buffer = new StringBuilder();
			BufferedReader reader = req.getReader();
			
			String line;
			while((line = reader.readLine()) != null) {
				buffer.append(line);
				buffer.append(System.lineSeparator());
			}
			
			String data = buffer.toString();
			LoggerManager.logger.info(data + "got from request");
			
			ObjectMapper mapper = new ObjectMapper();
			JsonNode parsedObj = mapper.readTree(data);
			
			String username = parsedObj.get("username").asText();			
						
			try {
				LoggerManager.logger.info("In the user controller: get user by username");
				User user = userServices.getUser(username);
				res.setStatus(200);
				res.getWriter().write(new ObjectMapper().writeValueAsString(user));
				LoggerManager.logger.debug(user+" got with username");
				
			}catch (Exception e) {
				ObjectNode errorInfo = mapper.createObjectNode();
				res.setStatus(403);
				//errorInfo.put("code", 403);
				errorInfo.put("message", e.getMessage());
				res.getWriter().write((new ObjectMapper().writeValueAsString(errorInfo)));
				LoggerManager.logger.info("Problem while proccessing request"+e);
			}
		
	}
		
		public static void getUserByEmail(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException{
			
			StringBuilder buffer = new StringBuilder();
			BufferedReader reader = req.getReader();
			
			String line;
			while((line = reader.readLine()) != null) {
				buffer.append(line);
				buffer.append(System.lineSeparator());
			}
			
			String data = buffer.toString();
			LoggerManager.logger.info(data + "got from request");
			
			ObjectMapper mapper = new ObjectMapper();
			JsonNode parsedObj = mapper.readTree(data);
			
			String email = parsedObj.get("email").asText();			
						
			try {
				LoggerManager.logger.info("In the user controller: get user by email");
				User user = userServices.getUser(email);
				res.setStatus(200);
				res.getWriter().write(new ObjectMapper().writeValueAsString(user));
				LoggerManager.logger.debug(user+" got with email");
				
			}catch (Exception e) {
				ObjectNode errorInfo = mapper.createObjectNode();
				res.setStatus(403);
				//errorInfo.put("code", 403);
				errorInfo.put("message", e.getMessage());
				res.getWriter().write((new ObjectMapper().writeValueAsString(errorInfo)));
				LoggerManager.logger.info("Problem while proccessing request"+e);
			}
		
	}
	
	
}
