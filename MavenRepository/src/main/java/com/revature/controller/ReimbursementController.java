package com.revature.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.revature.dao.ReimbursementDao;
import com.revature.dao.UserDao;
import com.revature.logger.LoggerManager;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.models.User;
import com.revature.services.ReimbursementServices;
import com.revature.services.UserServices;

public class ReimbursementController {

	private static ReimbursementDao rDao = new ReimbursementDao();
	private static ReimbursementServices rServ = new ReimbursementServices(rDao);
	private static UserDao uDao = new UserDao();
	private static UserServices uServ = new UserServices(uDao);
	
	public static void newRequest(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException{
		
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
		
		//Catching current local/servlet date and time https://stackabuse.com/how-to-get-current-date-and-time-in-java/
		SimpleDateFormat format= new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		
		int amount = Integer.parseInt(parsedObj.get("amount").asText());
		String submittedTime = format.format(date); // parsedObj.get("submitted").asText(); used to get date as a parameter from html
		String resolvedTime = null;
		String description = parsedObj.get("description").asText();
		String receipt = null;
		//String receipt = parsedObj.get("description").asText(); // change to implement optional
		int authorId = Integer.parseInt(parsedObj.get("userId").asText());
		User authorUser = new User();
		authorUser.setId(authorId);
		User resolverUser = new User();
		int statusId = 1;
		ReimbursementStatus status = new ReimbursementStatus();
		status.setId(statusId);
		int typeId = Integer.parseInt(parsedObj.get("typeId").asText());
		ReimbursementType type = new ReimbursementType();
		type.setId(typeId);
		
		
		
		try {
			LoggerManager.logger.info("In the reimbursement controller: create new");
			Reimbursement u =rServ.createNewReimbursmentRequest(new Reimbursement(0, amount, submittedTime, resolvedTime, description, receipt, authorUser, resolverUser, status, type));
			res.setStatus(200);
			res.getWriter().write(new ObjectMapper().writeValueAsString(u));
			LoggerManager.logger.debug(u+" Reimbursement created");
			
		}catch (Exception e) {
			ObjectNode errorInfo = mapper.createObjectNode();
			res.setStatus(403);
			errorInfo.put("code", 403);
			errorInfo.put("message", e.getMessage());
			LoggerManager.logger.info("Problem while creating request"+e);
			res.getWriter().write((new ObjectMapper().writeValueAsString(errorInfo)));
		}
		
	}
	
	public static void getPendingRequests(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException{
		
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
		
		int authorId = Integer.parseInt(parsedObj.get("authorId").asText());
		
		
		try {
			LoggerManager.logger.info("In the reimbursement controller: get pending from user");
			ArrayList<Reimbursement> reimbursements =(ArrayList<Reimbursement>) rServ.getPendingReimbursement(authorId);
			res.setStatus(200);
			res.getWriter().write(new ObjectMapper().writeValueAsString(reimbursements));
			LoggerManager.logger.debug(reimbursements+" got from pending requests");
			
		}catch (Exception e) {
			ObjectNode errorInfo = mapper.createObjectNode();
			res.setStatus(403);
			//errorInfo.put("code", 403);
			errorInfo.put("message", e.getMessage());
			LoggerManager.logger.info("Problem while retriving pending requests "+e);
			res.getWriter().write((new ObjectMapper().writeValueAsString(errorInfo)));
		}
		
		
	}
	
	public static void getResolvedRequests(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException{
		
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
		
		int authorId = Integer.parseInt(parsedObj.get("authorId").asText());

		try {
			LoggerManager.logger.info("In the reimbursement controller: get resolved from user");
			ArrayList<Reimbursement> reimbursements =(ArrayList<Reimbursement>) rServ.getResolvedReimbursement(authorId);
			res.setStatus(200);
			res.getWriter().write(new ObjectMapper().writeValueAsString(reimbursements));
			LoggerManager.logger.debug(reimbursements+" got from resolved requests");
			
		}catch (Exception e) {
			ObjectNode errorInfo = mapper.createObjectNode();
			res.setStatus(403);
			//errorInfo.put("code", 403);
			errorInfo.put("message", e.getMessage());
			LoggerManager.logger.info("Problem while retriving pending resolved "+e);
			res.getWriter().write((new ObjectMapper().writeValueAsString(errorInfo)));
		}
		
		
	}
	
	public static void approveDenyRequest(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException {
		
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
		
		int reimbursementId = Integer.parseInt(parsedObj.get("reimbursementId").asText());
		//Catching current local/servlet date and time https://stackabuse.com/how-to-get-current-date-and-time-in-java/
		SimpleDateFormat format= new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		
		String resolvedTime = format.format(date) ;
		int authorId = Integer.parseInt(parsedObj.get("resolverId").asText());
		User resolverUser = new User();
		resolverUser.setId(authorId);
		int statusId = Integer.parseInt(parsedObj.get("statusId").asText());
		ReimbursementStatus status = new ReimbursementStatus();
		status.setId(statusId);

		try {
			LoggerManager.logger.info("In the reimbursement controller: Approve deny");
			Reimbursement u =rServ.approveDenyReimbursement(new Reimbursement(reimbursementId, 0, null, resolvedTime, null, null, null, resolverUser, status, null));
			res.setStatus(200);
			res.getWriter().write(new ObjectMapper().writeValueAsString(u));
			LoggerManager.logger.debug(u+" as response from approveDeny");

		}catch (Exception e) {
			ObjectNode errorInfo = mapper.createObjectNode();
			res.setStatus(403);
			errorInfo.put("code", 403);
			errorInfo.put("message", e.getMessage());
			LoggerManager.logger.info("Problem while updating reimbursement "+e);
			res.getWriter().write((new ObjectMapper().writeValueAsString(errorInfo)));
		}
	}
	
	public static void getAllPendingRequests(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException {

		if(!req.getMethod().equals("GET")) {
			return;
		}
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			LoggerManager.logger.info("In the reimbursement controller: Get all pending requests");
			ArrayList<Reimbursement> reimbursement = (ArrayList<Reimbursement>) rServ.getAllPendingReimbursements();
			res.setStatus(200);
			res.getWriter().write(new ObjectMapper().writeValueAsString(reimbursement));
			LoggerManager.logger.debug(reimbursement+" got from all pending requests");
			
		}catch (Exception e) {
			ObjectNode errorInfo = mapper.createObjectNode();
			res.setStatus(403);
			//errorInfo.put("code", 403);
			errorInfo.put("message", e.getMessage());
			res.getWriter().write((new ObjectMapper().writeValueAsString(errorInfo)));
			LoggerManager.logger.info("Problem while getting all reimbursements "+e);

		}
	}
	
	public static void getAllResolvedRequests(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException {

		if(!req.getMethod().equals("GET")) {
			return;
		}
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			LoggerManager.logger.info("In the reimbursement controller: get all resolved requests");
			ArrayList<Reimbursement> reimbursement = (ArrayList<Reimbursement>) rServ.getAllResolvedReimbursements();
			res.setStatus(200);
			res.getWriter().write(new ObjectMapper().writeValueAsString(reimbursement));
			LoggerManager.logger.debug(reimbursement+" got from all resolved requests");

		}catch (Exception e) {
			ObjectNode errorInfo = mapper.createObjectNode();
			res.setStatus(403);
			//errorInfo.put("code", 403);
			errorInfo.put("message", e.getMessage());
			res.getWriter().write((new ObjectMapper().writeValueAsString(errorInfo)));
			LoggerManager.logger.info("Problem while getting all reimbursements "+e);

		}
	}

	public static void getReimbursement(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException{
		
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
		
		int reimbursementId = Integer.parseInt(parsedObj.get("reimbursementId").asText());

		
		try {
			LoggerManager.logger.info("In the reimbursement controller: get reimbursement with Id");
			Reimbursement reimbursement = rServ.getReimbursement(reimbursementId);
			res.setStatus(200);
			res.getWriter().write(new ObjectMapper().writeValueAsString(reimbursement));
			LoggerManager.logger.debug(reimbursement+" reimbursement got from db");

			
		}catch (Exception e) {
			ObjectNode errorInfo = mapper.createObjectNode();
			res.setStatus(403);
			//errorInfo.put("code", 403);
			errorInfo.put("message", e.getMessage());
			res.getWriter().write((new ObjectMapper().writeValueAsString(errorInfo)));
			LoggerManager.logger.info("Problem while getting reimbursement "+e);

		}
		
	}
	
}
