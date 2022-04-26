package com.victor.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BankDB {
	private String url = "jdbc:mysql://127.0.0.1:3306/NiiT";
	private Connection conn;
	public Statement statement;
	
	public BankDB(){
		try {
			conn = DriverManager.getConnection(url, "root", "victorsql");
			statement = conn.createStatement();
		} catch (SQLException e) {
			System.out.println("Cannot Create DB connection");
		}
		
	}
	
	public void createTable() {
		try {
			statement.executeUpdate("create if not exists table simbank (\n"
					+ "accNo varchar(11) UNIQUE PRIMARY KEY,\n"
					+ "firstName varchar(50),\n"
					+ "lastName varchar(50),\n"
					+ "email varchar(100),\n"
					+ "gender varchar(10),\n"
					+ "accBal float,\n"
					+ "password varchar(2000)\n"
					+ ")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
