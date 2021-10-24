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
	
}
