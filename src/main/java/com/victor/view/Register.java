package com.victor.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.victor.controller.ClientLogic;
import com.victor.models.Customer;

public class Register extends JFrame implements ActionListener{
	JLabel label;
	JPanel form;
	JTextField firstNameField;
	JLabel firstNameLabel;
	JTextField lastNameField;
	JLabel lastNameLabel;
	JTextField emailField;
	JLabel emailLabel;
	JTextField genderField;
	JLabel genderLabel;
	JTextField passwordField;
	JLabel passwordLabel;
	JButton register;
	JButton login;
	String firstname;
	String lastname;
	String email;
	String gender;
	String password;
	ClientLogic logic;
	Register(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout(30, 30));
		this.getContentPane().setBackground(new Color(0x123456));
		this.setSize(500, 500);
		this.setTitle("Register");
		this.setResizable(false);
		
		label = new JLabel("Registration", SwingConstants.CENTER);
		label.setForeground(Color.white);
		label.setFont(new Font(null, Font.BOLD, 40));
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setVerticalTextPosition(JLabel.CENTER);
		this.add(label, BorderLayout.NORTH);
		
		
		form = new JPanel();
		form.setBackground(new Color(0x123456));
		form.setLayout(new GridLayout(6, 2, 10 ,10));
		this.add(form, BorderLayout.CENTER);
		
		firstNameField = new JTextField();
		lastNameField = new JTextField();
		emailField = new JTextField();
		genderField = new JTextField();
		passwordField= new JTextField();
		
		firstNameLabel = new JLabel("First Name");
		firstNameLabel.setForeground(Color.white);
		firstNameLabel.setFont(new Font(null, Font.BOLD, 30));
		lastNameLabel = new JLabel("Last Name");
		lastNameLabel.setForeground(Color.white);
		lastNameLabel.setFont(new Font(null, Font.BOLD, 30));
		emailLabel = new JLabel("Email");
		emailLabel.setForeground(Color.white);
		emailLabel.setFont(new Font(null, Font.BOLD, 30));
		genderLabel = new JLabel("Gender");
		genderLabel.setForeground(Color.white);
		genderLabel.setFont(new Font(null, Font.BOLD, 30));
		passwordLabel = new JLabel("Password");
		passwordLabel.setForeground(Color.white);
		passwordLabel.setFont(new Font(null, Font.BOLD, 30));
		
		register = new JButton("Register");
		register.addActionListener(this);
		register.setFont(new Font(null, Font.BOLD, 30));
		login = new JButton("Login");
		login.addActionListener(this);
		login.setFont(new Font(null, Font.BOLD, 30));
		
		form.add(firstNameLabel);
		form.add(firstNameField);
		form.add(lastNameLabel);
		form.add(lastNameField);
		form.add(emailLabel);
		form.add(emailField);
		form.add(genderLabel);
		form.add(genderField);
		form.add(passwordLabel);
		form.add(passwordField);
		form.add(login);
		form.add(register);
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (logic != null) {
					logic.close();
				}
				System.exit(0);
				
			}
		});
		
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == login) {
			this.dispose();
			new Login();
		} else if(e.getSource() == register) {
			logic = new ClientLogic();
			firstname = firstNameField.getText().trim();
			lastname = lastNameField.getText().trim();
			email = emailField.getText().trim();
			if (!(email.contains("@yahoo.com") || email.contains("@gmail.com"))) {
				JOptionPane.showMessageDialog(null, "Enter Appropriate Email", "Bad Info", JOptionPane.ERROR_MESSAGE);
				logic.pass = false;
			}
			gender = genderField.getText().trim();
			if (!(gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female"))) {
				JOptionPane.showMessageDialog(null, "Enter Appropriate Gender", "Bad Info", JOptionPane.ERROR_MESSAGE);
				logic.pass = false;
			}
			password = passwordField.getText().trim();
			if (logic.success && logic.pass) {
				Customer customer = logic.register(firstname, lastname, email, gender, password);
				if (customer == null) {
					JOptionPane.showMessageDialog(null, "Error Registering User, Please Try Again", "500 Internal Server Error", JOptionPane.ERROR_MESSAGE);
				} else {
					new BankClientGUI(customer, logic);
					this.dispose();
				}
			} else if (!logic.success){
				JOptionPane.showMessageDialog(null, "Error Connecting to Server", "500 Server Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
