package com.victor.models;

import com.victor.utils.CustomerORM;

public class Customer {
	private int accNo;
	private String firstName;
	private String lastName;
	private String email;
	private String gender;
	private double accBal;
	
	
	public Customer(int accNo, String firstName, String lastName, String email, String gender, double accBal) {
		this.accNo = accNo;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		this.accBal = accBal;
	}
	
	public Customer(String accNo, String firstName, String lastName, String email, String gender, String accBal) {
		this.accNo = Integer.parseInt(accNo);
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		this.accBal = Double.parseDouble(accBal);
	}
	
	
	public int getAccNo() {
		return accNo;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public double getAccBal() {
		return accBal;
	}
	private void setAccBal(double accBal) {
		this.accBal = accBal;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
//	Basic Customer Activity functions
//	TODO: Withdraw, Deposit, Transfer, Static Validation Method, Setup History Table, Customer close method/Quit
	
	public boolean withdraw(int amount) {
		if (this.accBal < amount)
		{
			return false;
		}
		else {
			double newAccBal = this.accBal - amount;
			CustomerORM.updateIntegerField(this.accNo, "accBal", newAccBal);
			setAccBal(newAccBal);
			return true;
		}
	}
	
	public void deposit(double amount) {
		double newAmount = this.accBal + amount;
		CustomerORM.updateIntegerField(this.accNo, "accBal", newAmount);
		setAccBal(newAmount);
	}
	
	public boolean transfer(int amount, int accNo) {
		Customer recipient = CustomerORM.getUser(accNo);
		if (this.accBal < amount)
		{
			return false;
		}
		else {
			double newAccBal = this.accBal - amount;
			System.out.println(this.accBal);
			double recipientAccBal = recipient.accBal + amount;
			CustomerORM.updateIntegerField(this.accNo, "accBal", newAccBal);
			CustomerORM.updateIntegerField(recipient.accNo, "accBal", recipientAccBal);
			setAccBal(newAccBal);
			return true;
		}
	}
	
	public static Customer createUser(String firstName, String lastName, String email, String gender, String password) {
		return CustomerORM.createUser(firstName, lastName, email, gender, password);
	}
	
	public static boolean validateUser(int accNo, String password) {
		return CustomerORM.isValid(accNo, password);
	}
}
