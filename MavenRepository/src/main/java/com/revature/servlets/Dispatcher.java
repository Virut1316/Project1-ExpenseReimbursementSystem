package com.revature.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.controller.LoginController;
import com.revature.controller.ReimbursementController;
import com.revature.controller.SessionController;

public class Dispatcher {

	public static void process(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException{
		System.out.println("Here: "+req.getRequestURI());
		
		switch (req.getRequestURI()) {
		case "/project1-ERS/api/login":
			LoginController.login(req, res);
			break;
			
		case "/project1-ERS/api/session":
			SessionController.getSession(req, res);
			break;
		
		case "/project1-ERS/api/reimbursement/create":
			ReimbursementController.newRequest(req, res);;
			break;
			
		case "/project1-ERS/api/reimbursement/pending-requests":
			ReimbursementController.getPendingRequests(req, res);;
			break;
			
		case "/project1-ERS/api/reimbursement/resolved-requests":
			ReimbursementController.getResolvedRequests(req, res);;
			break;
			
		default:
			System.out.println("Something went wrong");
			break;
		}
		
	}
	
}
