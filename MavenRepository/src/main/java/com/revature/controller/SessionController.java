package com.revature.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class SessionController {

	public static void getSession(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException{
		HttpSession session = req.getSession();
		
		ObjectMapper map = new ObjectMapper();
		
		ObjectNode sessInfo = map.createObjectNode();
		
		
		if(session.getAttribute("id") == null) {
			ObjectNode errorInfo = map.createObjectNode();
			res.setStatus(404);
			//errorInfo.put("code", 404);
			errorInfo.put("message", "User is not logged in");
			res.getWriter().write(new ObjectMapper().writeValueAsString(sessInfo));
			res.getWriter().println("User is not logged in");
			return;
		}
		System.out.print("there is a session");	
		sessInfo.put("userId", session.getAttribute("id").toString());
		res.getWriter().write(new ObjectMapper().writeValueAsString(sessInfo));
		
	}
	
	public static void logOut(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException {
		
		if(!req.getMethod().equals("GET"))
			return;
			
		ObjectMapper mapper = new ObjectMapper();

		try {
			ObjectNode info = mapper.createObjectNode();
			info.put("message", "Successful logout");
			req.getSession().invalidate();
			res.setStatus(200);
			res.getWriter().write(new ObjectMapper().writeValueAsString(info));
		} catch(Exception e) {
			ObjectNode errorInfo = mapper.createObjectNode();
			res.setStatus(403);
			errorInfo.put("code", 403);
			errorInfo.put("message", e.getMessage());
			res.getWriter().write((new ObjectMapper().writeValueAsString(errorInfo)));
		}
		
	}
	
}
