package com.victor.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import com.victor.models.Customer;
import com.victor.utils.BankServer;



public class ClientLogic {
	static Socket client;
	static ObjectOutputStream clientOutput;
	static ObjectInputStream clientInput;
//	If the constructor is successful connecting to the server this remains true else false
	public boolean success = true;
	public boolean pass = true;
	ArrayList<String> command = new ArrayList<String>();
	
//	TODO: Turn this to a constructor when commencing GUI
	public ClientLogic() {
		try {
			client = new Socket("127.0.0.1", BankServer.PORT);
			clientOutput = new ObjectOutputStream(client.getOutputStream());
			clientInput = new ObjectInputStream(client.getInputStream());
		} catch (IOException e) {
			success = false;
		}
	}
	
	public Customer login(String accNo, String password) {
		command.clear();
		command.add("1");
		command.add(accNo);
		command.add(password);
		try {
			clientOutput.writeObject(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
		command.clear();
		try {
			command = (ArrayList<String>) clientInput.readObject();
			if (command.get(0).equalsIgnoreCase("1")) {
				return new Customer(
						command.get(1), 
						command.get(2), 
						command.get(3), 
						command.get(4), 
						command.get(5), 
						command.get(6));
			}
		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
			System.out.println("Error finding class");
		} catch (IOException e) {
			System.out.println("IOException at clientLogic login");
//			e.printStackTrace();
		}
		return null;
	}
	
	public Customer register(String firstName, String lastName, String email, String gender, String password) {
		command.clear();
		command.add("2");
		command.add(firstName);
		command.add(lastName);
		command.add(email);
		command.add(gender);
		command.add(password);
		try {
			clientOutput.writeObject(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
		command.clear();
		try {
			command = (ArrayList<String>) clientInput.readObject();
			if (command.get(0).equalsIgnoreCase("2")) {
				return new Customer(
						command.get(1), 
						command.get(2), 
						command.get(3), 
						command.get(4), 
						command.get(5), 
						command.get(6));
			}
		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
			System.out.println("Error finding class");
		} catch (IOException e) {
			System.out.println("IOException at clientLogic register");
//			e.printStackTrace();
		}
		return null;
	}
	
	public boolean withdraw(int amount, Customer customer) {
		return customer.withdraw(amount);
	}
	
	public void deposit(double amount, Customer customer) {
		customer.deposit(amount);
	}
	
	public boolean transfer(int amount, int accNo, Customer customer) {
		return customer.transfer(amount, accNo);
	}
	
	public boolean serverOpen() {
		try {
			return BankServer.isOpen();
		} catch (NullPointerException e) {
			return false;
		}
	}
	
	public void close() {
		command.clear();
		command.add("-1");
		try {
			clientOutput.writeObject(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
