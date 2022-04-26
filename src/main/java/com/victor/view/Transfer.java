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

public class Transfer extends JFrame implements ActionListener{
	JLabel label;
	JPanel form;
	JTextField amountField;
	JLabel amountLabel;
	JTextField accountField;
	JLabel accountLabel;
	int amount;
	int account;
	ClientLogic logic;
	JButton transfer;
	JButton quit;
	Customer customer;

	Transfer(final ClientLogic logic, Customer customer){
		this.customer = customer;
		this.logic = logic;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout(30, 30));
		this.getContentPane().setBackground(new Color(0x123456));
		this.setSize(550, 550);
		this.setTitle("Transfer");
		this.setResizable(false);
		
		label = new JLabel("Transfer", SwingConstants.CENTER);
		label.setForeground(Color.white);
		label.setFont(new Font(null, Font.BOLD, 40));
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setVerticalTextPosition(JLabel.CENTER);
		this.add(label, BorderLayout.NORTH);
		
		
		form = new JPanel();
		form.setBackground(new Color(0x123456));
		form.setLayout(new GridLayout(6, 2, 10 ,10));
		this.add(form, BorderLayout.CENTER);
		
		amountField = new JTextField();
		amountField.setFont(new Font(null, Font.BOLD, 20));
		accountField = new JTextField();
		accountField.setFont(new Font(null, Font.BOLD, 20));
		
		amountLabel = new JLabel("Amount");
		amountLabel.setForeground(Color.white);
		amountLabel.setFont(new Font(null, Font.BOLD, 30));
		accountLabel = new JLabel("Account Number");
		accountLabel.setForeground(Color.white);
		accountLabel.setFont(new Font(null, Font.BOLD, 30));
		
		
		transfer = new JButton("Transfer");
		transfer.addActionListener(this);
		transfer.setFont(new Font(null, Font.BOLD, 30));
		quit = new JButton("Quit");
		quit.addActionListener(this);
		quit.setFont(new Font(null, Font.BOLD, 30));
		
		form.add(amountLabel);
		form.add(amountField);
		form.add(accountLabel);
		form.add(accountField);
		form.add(quit);
		form.add(transfer);
		
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == quit) {
			this.dispose();
		} else if (e.getSource() == transfer) {
			if (!logic.serverOpen()) {
				try {
					amount = Integer.parseInt(amountField.getText());
					account =  Integer.parseInt(accountField.getText());
					if (customer.getAccNo() == account) {JOptionPane.showMessageDialog(null, "Same Account Details Entered", "Bad Info", JOptionPane.ERROR_MESSAGE);}
					else {
						if (amount > 0) {
							if (logic.transfer(amount, account, customer)) {
								JOptionPane.showMessageDialog(null, "Success", "200 Successful", JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(null, "Insufficient Funds", "Bad Info", JOptionPane.ERROR_MESSAGE);
							}
						} else {JOptionPane.showMessageDialog(null, "You cannot transfer a negative number", "Bad Info", JOptionPane.ERROR_MESSAGE);}
					}
				} catch (NumberFormatException n) {
					JOptionPane.showMessageDialog(null, "Please input a number", "Bad Info", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Error Connecting to Server", "500 Server Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}

