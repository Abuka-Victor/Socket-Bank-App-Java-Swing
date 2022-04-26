package com.victor.utils;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import com.victor.models.Customer;

public class ClientHandler implements Runnable{
	private Socket client;
	private ObjectOutputStream serverOutput;
	private ObjectInputStream serverInput;
	private static Customer customer;
	private ArrayList<ClientHandler> list;
	private ArrayList<String> command = new ArrayList<String>();
	
	public ClientHandler(Socket client) {
		this.client = client;
		try {
			serverOutput = new ObjectOutputStream(client.getOutputStream());
			serverInput = new ObjectInputStream(client.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			while(true) {
				try {
					if (client.isClosed()) {
						break;
					} else {
						command = (ArrayList<String>) serverInput.readObject();
					}
				} catch (EOFException e) {
					continue;
				}
				if (command.get(0).equalsIgnoreCase("1")) {
					customer = BankServer.login(Integer.parseInt(command.get(1)), command.get(2));
					command.clear();
					if (customer != null) {
						command.add("1");
						command.add(Integer.toString(customer.getAccNo()));
						command.add(customer.getFirstName());
						command.add(customer.getLastName());
						command.add(customer.getEmail());
						command.add(customer.getGender());
						command.add(Double.toString(customer.getAccBal()));
						serverOutput.writeObject(command);
					} else {
						command.add("0");
						serverOutput.writeObject(command);
					}
				} else if (command.get(0).equalsIgnoreCase("2")) {
					customer = BankServer.register(command.get(1),command.get(2),command.get(3),command.get(4),command.get(5));
					command.clear();
					if (customer != null) {
						command.add("2");
						command.add(Integer.toString(customer.getAccNo()));
						command.add(customer.getFirstName());
						command.add(customer.getLastName());
						command.add(customer.getEmail());
						command.add(customer.getGender());
						command.add(Double.toString(customer.getAccBal()));
						serverOutput.writeObject(command);
					} else {
						command.add("0");
						serverOutput.writeObject(command);
					}
				} else if (command.get(0).equalsIgnoreCase("-1")) {
					client.close();
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			command.clear();
			command.add("0");
			try {
				serverOutput.writeObject(command);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
