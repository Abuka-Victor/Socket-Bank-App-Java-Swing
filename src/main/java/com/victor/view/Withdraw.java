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

public class Withdraw extends JFrame implements ActionListener{
	JLabel label;
	JPanel form;
	JTextField amountField;
	JLabel amountLabel;
	int amount;
	ClientLogic logic;
	JButton withdraw;
	JButton quit;
	Customer customer;

	Withdraw(final ClientLogic logic, Customer customer){
		this.customer = customer;
		this.logic = logic;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout(30, 30));
		this.getContentPane().setBackground(new Color(0x123456));
		this.setSize(500, 500);
		this.setTitle("Withdraw");
		this.setResizable(false);
		
		label = new JLabel("Withdraw", SwingConstants.CENTER);
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
		
		
		amountLabel = new JLabel("Amount");
		amountLabel.setForeground(Color.white);
		amountLabel.setFont(new Font(null, Font.BOLD, 30));
		
		
		withdraw = new JButton("Withdraw");
		withdraw.addActionListener(this);
		withdraw.setFont(new Font(null, Font.BOLD, 30));
		quit = new JButton("Quit");
		quit.addActionListener(this);
		quit.setFont(new Font(null, Font.BOLD, 30));
		
		form.add(amountLabel);
		form.add(amountField);
		form.add(quit);
		form.add(withdraw);
		
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == quit) {
			this.dispose();
		} else if (e.getSource() == withdraw) {
			if (!logic.serverOpen()) {
				try {
					amount = Integer.parseInt(amountField.getText());
					if (amount > 0) {
						if (logic.withdraw(amount, customer)) {
							JOptionPane.showMessageDialog(null, "Success! Have your #"+amount+" funds", "200 Successful", JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, "Insufficient Funds", "Bad Info", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Negative Number Inputed", "Bad Info", JOptionPane.ERROR_MESSAGE);
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

