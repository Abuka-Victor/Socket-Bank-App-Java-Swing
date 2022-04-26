package com.victor.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.victor.models.Customer;

public class CustomerORM {
	static BankDB custdb = new BankDB();
	
//	generates random account number for each user
	private static int createAccountNumber() {
		ResultSet accountNumbers;
		try {
			accountNumbers = custdb.statement.executeQuery("SELECT accNO FROM simbank");
			List<Integer> accNos = new ArrayList<Integer>();
			while (accountNumbers.next()) {
				accNos.add(accountNumbers.getInt("accNo"));
			}
			int accNo = ThreadLocalRandom.current().nextInt(100000000, 999999999);
			while (accNos.contains(accNo))
			{
				accNo = ThreadLocalRandom.current().nextInt(100000000, 999999999);
			}
			return accNo;
		} catch (SQLException e) {
			return 0;
		}
	}
	
//	Creates a new user. Used with registration
	public static Customer createUser(String firstName, String lastName, String email, String gender, String password) {
		double accBal = 0;
		String pass = Password.hash(password);
		int accNo = createAccountNumber();
		if (accNo == 0) {
			return null;
		}
		
//		Insert Customer to the table and return customer
		try {
			String query = String.format("insert into simbank values (%d, "
					+ "'%s', '%s', '%s', '%s', %.2f, '%s')", accNo, firstName, lastName, email, gender, accBal, pass);
			
			custdb.statement.executeUpdate(query);
			
//			Create customer object
			Customer customer = new Customer(accNo, firstName, lastName, email, gender, accBal);
			return customer;
		} catch (SQLException e) {
			return null;
		}
	}
	
//	Get a user. Most important use case in transfers
	public static Customer getUser(int accNo) {
		String query = String.format("SELECT * FROM simbank where accNo = %d", accNo);
		Customer customer;
		ResultSet result;
		int accNum;
		String firstName;
		String lastName;
		String email;
		String gender;
		double accBal;
		
		try {
			result = custdb.statement.executeQuery(query);
			if (!result.next()) {
				return null;
			}
			accNum = result.getInt("accNo");
			firstName = result.getString("firstName");
			lastName = result.getString("lastName");
			email = result.getString("email");
			gender = result.getString("gender");
			accBal = result.getDouble("accBal");
			
			customer = new Customer(accNum, firstName, lastName, email, gender, accBal);
			return customer;
			
		} catch (SQLException e) {
			return null;
		}
	}
	
//	Login helper function
	public static boolean isValid(int accNo, String password) {
		String query = String.format("SELECT * FROM simbank where accNo = %d", accNo);
		ResultSet result;
		try {
			result = custdb.statement.executeQuery(query);
			if (!result.next()) {
				return false;
			}
			
			if (result.getString("password").equals(Password.hash(password))) {
				return true;
			}
			else {
				return false;
			}
		} catch (SQLException e) {
			BankServer.println(e.getMessage());
		}
		return false;
	}
	
//	TODO: User profile update functions
//	TODO: Update field function
//	TODO: Send all catches to server output
	
	public static void updateStringField(int accNo, String field, String value) {
		String query = String.format("UPDATE simbank SET %s = '%s' WHERE accNo = %d", field, value, accNo);
		try {
			custdb.statement.executeUpdate(query);
		} catch (SQLException e) {
			BankServer.println(e.getMessage());
		}
		
	}
	
	public static void updateIntegerField(int accNo, String accBal, double value) {
		String query = String.format("UPDATE simbank SET %s = %f WHERE accNo = %d", accBal, value, accNo);
		try {
			custdb.statement.executeUpdate(query);
		} catch (SQLException e) {
			BankServer.println(e.getMessage());
		}
		
	}
	
	public static void close() {
		custdb.close();
	}
}
