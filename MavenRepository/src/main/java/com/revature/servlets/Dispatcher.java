package com.revature.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.controller.LoginController;
import com.revature.controller.ReimbursementController;
import com.revature.controller.SessionController;
import com.revature.controller.UserController;

public class Dispatcher {

	public static void process(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException{
		System.out.println("Here: "+req.getRequestURI());
		
		switch (req.getRequestURI()) {
		case "/project1-ERS/api/login": //gets an user if its on DB or an error message on exceptions
			LoginController.login(req, res);
			break;
			
		case "/project1-ERS/api/session": //gets session user id
			SessionController.getSession(req, res);
			break;
		
		case "/project1-ERS/api/log-out": //loggs out session
			SessionController.logOut(req, res);
			break;
			
		case "/project1-ERS/api/reimbursement": //Creates a new reimbursement, returns message exception if not
			ReimbursementController.getReimbursement(req, res);;
			break;
			
		case "/project1-ERS/api/reimbursement/create": //Creates a new reimbursement, returns message exception if not
			ReimbursementController.newRequest(req, res);;
			break;
			
		case "/project1-ERS/api/reimbursement/pending-requests": // gets all pending requests of a User
			ReimbursementController.getPendingRequests(req, res);;
			break;
			
		case "/project1-ERS/api/reimbursement/resolved-requests": // gets all resolved requests of a User
			ReimbursementController.getResolvedRequests(req, res);;
			break;
			
		case "/project1-ERS/api/reimbursement/approve-or-deny": // Updates a reimbursement to a resolved, sets deny or approve
			ReimbursementController.approveDenyRequest(req, res);;
			break;
			
		case "/project1-ERS/api/reimbursement/all-pending": //gets all pending reimbursements of all users
			ReimbursementController.getAllPendingRequests(req, res);;
			break;
			
		case "/project1-ERS/api/reimbursement/all-resolved": //gets all resolved reimbursements of all users
			ReimbursementController.getAllResolvedRequests(req, res);;
			break;
			
		case "/project1-ERS/api/user/all-employees": //gets all employees
			UserController.getAllEmployees(req, res);;
			break;
			
		case "/project1-ERS/api/user/update-info": //changes all info except password
			UserController.updateUserInfo(req, res);;
			break;
			
		case "/project1-ERS/api/user/update-password": //changes password
			UserController.updatePassword(req, res);
			break;

		case "/project1-ERS/api/user/get-username": //changes password
			UserController.getUserByUsername(req, res);
			break;
		
		case "/project1-ERS/api/user/get-user-id": //changes password
			UserController.getUserById(req, res);
			break;
			
		case "/project1-ERS/api/user/get-email": //changes password
			UserController.getUserByEmail(req, res);
			break;
		default:
			System.out.println("Something went wrong");
			break;
		}
		
	}
	
}
