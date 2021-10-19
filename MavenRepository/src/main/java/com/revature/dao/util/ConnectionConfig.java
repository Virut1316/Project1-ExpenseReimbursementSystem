package com.revature.dao.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;



public class ConnectionConfig {
	private static ConnectionConfig config;
	private static Properties properties = new Properties();
	
	public ConnectionConfig() {
		// Singleton
	}
	
	public static synchronized ConnectionConfig getInstance() {
		if(config == null)
		{
			config = new ConnectionConfig();
			return config;
		}
		else 
			return config; //The instance exists so we return it
	}
	
	
	public Connection getConnection() {
		//We create a inputStream and take the .properties to read the parameters
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream InputStream = classLoader.getResourceAsStream("jdbc.properties");
		String url= "",port= "",password= "",username = "",database = "";
		
		try {
			//We read the .properties and extract the data
			properties.load(InputStream);
			url = (String)properties.getProperty("url");
			port = (String)properties.getProperty("port");
			database = properties.getProperty("database");
			username = (String)properties.getProperty("username");
			password = (String)properties.getProperty("password");
		} catch(Exception e) {
			System.out.print("Database connection failed");
			e.printStackTrace();
		}
		
		
		try {
			Connection connection = DriverManager.getConnection(url+":"+port+"/"+database, username, password); //We get the actual connection and return it
			return connection;
		} catch(Exception e) {
			//System.out.print("Database connection failed");
			//e.printStackTrace(); print this to the logger
		}
		return null; // if we are not able to stablish a connection with the db we return null

		
	}
	
	
}
