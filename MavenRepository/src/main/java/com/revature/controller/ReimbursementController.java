package com.revature.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.revature.dao.ReimbursementDao;
import com.revature.dao.UserDao;
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
		System.out.println(data);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode parsedObj = mapper.readTree(data);
		
		int amount = Integer.parseInt(parsedObj.get("amount").asText());
		String submittedTime = parsedObj.get("submitted").asText();
		String resolvedTime = null;
		String description = parsedObj.get("description").asText();
		String receipt = null;
		//String receipt = parsedObj.get("description").asText(); // change to implement optional
		int authorId = Integer.parseInt(parsedObj.get("authorId").asText());
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
			
			Reimbursement u =rServ.createNewReimbursmentRequest(new Reimbursement(0, amount, submittedTime, resolvedTime, description, receipt, authorUser, resolverUser, status, type));
			res.setStatus(200);
			res.getWriter().write(new ObjectMapper().writeValueAsString(u));
			
		}catch (Exception e) {
			ObjectNode errorInfo = mapper.createObjectNode();
			res.setStatus(403);
			//errorInfo.put("code", 403);
			errorInfo.put("message", e.getMessage());
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
		System.out.println(data);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode parsedObj = mapper.readTree(data);
		
		int authorId = Integer.parseInt(parsedObj.get("authorId").asText());
		
		
		try {
			
			ArrayList<Reimbursement> reimbursements =(ArrayList<Reimbursement>) rServ.getPendingReimbursement(authorId);
			res.setStatus(200);
			res.getWriter().write(new ObjectMapper().writeValueAsString(reimbursements));
			
		}catch (Exception e) {
			ObjectNode errorInfo = mapper.createObjectNode();
			res.setStatus(403);
			//errorInfo.put("code", 403);
			errorInfo.put("message", e.getMessage());
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
		System.out.println(data);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode parsedObj = mapper.readTree(data);
		
		int authorId = Integer.parseInt(parsedObj.get("authorId").asText());
		
		
		try {
			
			ArrayList<Reimbursement> reimbursements =(ArrayList<Reimbursement>) rServ.getResolvedReimbursement(authorId);
			res.setStatus(200);
			res.getWriter().write(new ObjectMapper().writeValueAsString(reimbursements));
			
		}catch (Exception e) {
			ObjectNode errorInfo = mapper.createObjectNode();
			res.setStatus(403);
			//errorInfo.put("code", 403);
			errorInfo.put("message", e.getMessage());
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
		System.out.println(data);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode parsedObj = mapper.readTree(data);
		
		int reimbursementId = Integer.parseInt(parsedObj.get("reimbursementId").asText());
		String resolvedTime = parsedObj.get("resolved").asText();
		int authorId = Integer.parseInt(parsedObj.get("resolverId").asText());
		User resolverUser = new User();
		resolverUser.setId(authorId);
		int statusId = Integer.parseInt(parsedObj.get("statusId").asText());
		ReimbursementStatus status = new ReimbursementStatus();
		status.setId(statusId);

		try {
			
			Reimbursement u =rServ.approveDenyReimbursement(new Reimbursement(reimbursementId, 0, null, resolvedTime, null, null, null, resolverUser, status, null));
			res.setStatus(200);
			res.getWriter().write(new ObjectMapper().writeValueAsString(u));
			
		}catch (Exception e) {
			ObjectNode errorInfo = mapper.createObjectNode();
			res.setStatus(403);
			//errorInfo.put("code", 403);
			errorInfo.put("message", e.getMessage());
			res.getWriter().write((new ObjectMapper().writeValueAsString(errorInfo)));
		}
	}
	
	public static void getAllPendingRequests(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException {

		if(!req.getMethod().equals("GET")) {
			return;
		}
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			
			ArrayList<Reimbursement> reimbursement = (ArrayList<Reimbursement>) rServ.getAllPendingReimbursements();
			res.setStatus(200);
			res.getWriter().write(new ObjectMapper().writeValueAsString(reimbursement));
			
		}catch (Exception e) {
			ObjectNode errorInfo = mapper.createObjectNode();
			res.setStatus(403);
			//errorInfo.put("code", 403);
			errorInfo.put("message", e.getMessage());
			res.getWriter().write((new ObjectMapper().writeValueAsString(errorInfo)));
		}
	}
	
	public static void getAllResolvedRequests(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException {

		if(!req.getMethod().equals("GET")) {
			return;
		}
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			
			ArrayList<Reimbursement> reimbursement = (ArrayList<Reimbursement>) rServ.getAllResolvedReimbursements();
			res.setStatus(200);
			res.getWriter().write(new ObjectMapper().writeValueAsString(reimbursement));
			
		}catch (Exception e) {
			ObjectNode errorInfo = mapper.createObjectNode();
			res.setStatus(403);
			//errorInfo.put("code", 403);
			errorInfo.put("message", e.getMessage());
			res.getWriter().write((new ObjectMapper().writeValueAsString(errorInfo)));
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
		System.out.println(data);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode parsedObj = mapper.readTree(data);
		
		int reimbursementId = Integer.parseInt(parsedObj.get("reimbursementId").asText());

		
		try {
			
			Reimbursement reimbursement = rServ.getReimbursement(reimbursementId);
			res.setStatus(200);
			res.getWriter().write(new ObjectMapper().writeValueAsString(reimbursement));
			
		}catch (Exception e) {
			ObjectNode errorInfo = mapper.createObjectNode();
			res.setStatus(403);
			//errorInfo.put("code", 403);
			errorInfo.put("message", e.getMessage());
			res.getWriter().write((new ObjectMapper().writeValueAsString(errorInfo)));
		}
		
	}
	
}
