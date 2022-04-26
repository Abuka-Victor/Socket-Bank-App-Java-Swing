package com.victor.utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.victor.models.Customer;

public class BankServer {
	private static ServerSocket server;
	private static Socket client;
	private static ExecutorService exec = Executors.newCachedThreadPool();
	private static ArrayList<ClientHandler> connections = new ArrayList<ClientHandler>();
	public static final int PORT = 9999;
//	public static BankDB db = new BankDB();
	
	public static void main(String[] args) {
//		db.createTable();
		try {
			server = new ServerSocket(PORT);
			System.out.println("Simbank Server Started");
			
			while (true) {
				System.out.println("Waiting for connection");
				if (server.isClosed()) {
					break;
				}
				client = server.accept();
				System.out.println("Connection established with ".concat(client.toString()));
				ClientHandler clientThread = new ClientHandler(client);
				connections.add(clientThread);
				
				exec.execute(clientThread);
			}
			
			
			
		} catch (IOException e) {
			System.out.println("Error Starting Server");
			e.printStackTrace();
		}
	}
	
	public static void println(String args) {
		System.out.println(args);
	}
	
	public static Customer login(int accNo, String password) {
		if (Customer.validateUser(accNo, password)) {
			return CustomerORM.getUser(accNo);
		} else {
			return null;
		}
	}
	
	public static Customer register(String firstName, String lastName, String email, String gender, String password) {
		return Customer.createUser(firstName, lastName, email, gender, password);
	}
	
	public static boolean isOpen() {
		return !server.isClosed();
	}
	
	
	
//	public static void close() {
//		CustomerORM.close();
//		try {
//			server.close();
//			System.out.println("Server Has Been ShutDown");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
