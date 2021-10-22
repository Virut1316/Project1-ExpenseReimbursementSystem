package com.revature.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.controller.LoginController;

public class Dispatcher {

	public static void process(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException{
		System.out.println("Here: "+req.getRequestURI());
		
		switch (req.getRequestURI()) {
		case "/project1-ERS/login":
			LoginController.login(req, res);
			break;

		default:
			System.out.println("Something went wrong");
			break;
		}
		
	}
	
}
