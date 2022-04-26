package com.victor.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

public class Login extends JFrame implements ActionListener{

	JLabel label;
	JButton login;
	JButton register;
	JPanel form;
	JTextField accNoField;
	JTextField passwordField;
	JLabel accNoLabel;
	JLabel passwordLabel;
	String accNo;
	String password;
	ClientLogic logic;
	
	Login() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout(50, 50));
		this.getContentPane().setBackground(new Color(0x123456));
		this.setSize(500, 500);
		this.setTitle("Login");
		this.setResizable(false);
		
		label = new JLabel("Welcome to SIMBANK", SwingConstants.CENTER);
		label.setForeground(Color.white);
		label.setFont(new Font(null, Font.BOLD, 40));
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setVerticalTextPosition(JLabel.CENTER);
		this.add(label, BorderLayout.NORTH);
		
		form = new JPanel();
		form.setBackground(new Color(0x123456));
		form.setLayout(new GridLayout(4, 1, 10, 10));
		form.setPreferredSize(new Dimension(250, 250));
		this.add(form, BorderLayout.CENTER);
		
		accNoLabel = new JLabel("Enter a accNo");
		accNoLabel.setFont(new Font(null, Font.BOLD, 20));
		accNoLabel.setForeground(Color.white);
		accNoField = new JTextField();
		passwordLabel = new JLabel("Enter a password");
		passwordLabel.setFont(new Font(null, Font.BOLD, 20));
		passwordLabel.setForeground(Color.white);
		passwordField = new JTextField();
		
		login = new JButton("Login");
		login.setFont(new Font(null, Font.PLAIN, 30));
		register = new JButton("Register");
		register.setFont(new Font(null, Font.PLAIN, 30));
		
		accNoField.setPreferredSize(new Dimension(50, 50));
		passwordField.setPreferredSize(new Dimension(200, 50));
		accNoField.setFont(new Font(null, Font.BOLD, 30));
		passwordField.setFont(new Font(null, Font.BOLD, 30));
		login.setSize(100, 100);
		login.addActionListener(this);
		register.setSize(100, 100);
		register.addActionListener(this);
		
		form.add(accNoLabel);
		form.add(accNoField);
		form.add(passwordLabel);
		form.add(passwordField);
		form.add(login);
		form.add(register);
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (logic != null && logic.success) {
					logic.close();
				}
				System.exit(0);
				
			}
		});
		
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == login) {
			accNo = accNoField.getText().trim();
			password = passwordField.getText().trim();
			logic = new ClientLogic();
			if (logic.success) {
				Customer customer = logic.login(accNo, password);
				if (customer == null) {
					JOptionPane.showMessageDialog(null, "Wrong Input Details or Customer does not exist", "404 Not Found", JOptionPane.ERROR_MESSAGE);
				} else {
					new BankClientGUI(customer, logic);
					this.dispose();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Error Connecting to Server", "500 Server Error", JOptionPane.ERROR_MESSAGE);
			}
			
		} else if (e.getSource() == register) {
			new Register();
			this.dispose();
		}
	}

}
