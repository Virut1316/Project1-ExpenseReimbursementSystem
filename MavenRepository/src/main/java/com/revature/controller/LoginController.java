package com.revature.controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.Dao;
import com.revature.dao.UserDao;
import com.revature.models.User;
import com.revature.services.UserServices;

public class LoginController {

	private static UserDao userDao = new UserDao();
	private static UserServices userServices = new UserServices(userDao);
	
	public static void login(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException {
		
		//To read in stringified JSON data from a POST request is a little more complicated than reading form data
		StringBuilder buffer = new StringBuilder();
		
		//The buffered reader will read the json data line by line
		BufferedReader reader = req.getReader();
		
		String line;
		while((line = reader.readLine()) != null) {
			buffer.append(line);
			buffer.append(System.lineSeparator());
		}
		
		String data = buffer.toString();
		System.out.println(data);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode parsedObj = mapper.readTree(data);
		
		String username = parsedObj.get("username").asText();
		String password = parsedObj.get("password").asText();
		
		try {
			System.out.println("In the post handler");
			User u = userServices.login(username, password);
			System.out.println(u);
			//We will keep track of if a user is signed in by storing their id in the session
			req.getSession().setAttribute("id", u.getId());
			res.setStatus(200);
			res.getWriter().write(new ObjectMapper().writeValueAsString(u));
		} catch(Exception e) {
			res.setStatus(403);
			res.getWriter().println("Username or password incorrect");
		}
		
	}
	
}
