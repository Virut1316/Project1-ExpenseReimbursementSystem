package com.revature.controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.revature.dao.Dao;
import com.revature.dao.UserDao;
import com.revature.logger.LoggerManager;
import com.revature.models.User;
import com.revature.services.UserServices;

public class LoginController {

	private static UserDao userDao = new UserDao();
	private static UserServices userServices = new UserServices(userDao);
	
	public static void login(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException {
		
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
		String password = parsedObj.get("password").asText();
		
		try {
			LoggerManager.logger.info("In the log in controller");
			User u = userServices.login(username, password);
			LoggerManager.logger.debug(u+" sign in");
			//We will keep track of if a user is signed in by storing their id in the session
			req.getSession().setAttribute("id", u.getId());
			res.setStatus(200);
			res.getWriter().write(new ObjectMapper().writeValueAsString(u));
		} catch(Exception e) {
			ObjectNode errorInfo = mapper.createObjectNode();
			res.setStatus(403);
			errorInfo.put("code", 403);
			errorInfo.put("message", e.getMessage());
			LoggerManager.logger.info("Problem while signing in"+e);
			res.getWriter().write((new ObjectMapper().writeValueAsString(errorInfo)));
		}
		
	}
	
	

	
}
