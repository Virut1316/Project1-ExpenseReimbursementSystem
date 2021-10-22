package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MasterServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

		public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
			Dispatcher.process(req, res);
		}
		
		public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
			Dispatcher.process(req, res);
		}
	
}
