package com.victor.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import com.victor.controller.ClientLogic;
import com.victor.models.Customer;

public class BankClientGUI extends JFrame implements ActionListener{
	Customer customer;
	ClientLogic logic;
	JPanel sidebar;
	JPanel content;
	JPanel topBarContent;
	JPanel mainContent;
	JButton withdraw;
	JButton deposit;
	JButton transfer;
	JButton quit;
	JLabel accNo;
	JLabel accBal;
	
	BankClientGUI(Customer customer, final ClientLogic logic){
		this.logic = logic;
		this.customer = customer;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout(10, 10));
		this.setTitle(customer.getFirstName()+ "'s Dashboard");
		this.getContentPane().setBackground(new Color(0x123456));
		this.setSize(900, 900);
		
		Image imag = new ImageIcon("IMG_3783.PNG").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
		ImageIcon image = new ImageIcon(imag);
		
		Image imag2 = new ImageIcon("thanku.jpg").getImage().getScaledInstance(500, 500, Image.SCALE_DEFAULT);
		ImageIcon image2 = new ImageIcon(imag2);
		
		JLabel contentMain = new JLabel("THANKS FOR BANKING WITH US", SwingConstants.CENTER);
		contentMain.setIcon(image2);
		contentMain.setFont(new Font(null, Font.BOLD, 40));
		contentMain.setHorizontalTextPosition(JLabel.CENTER);
		contentMain.setVerticalTextPosition(JLabel.BOTTOM);
		
		JLabel label = new JLabel("Welcome " + customer.getFirstName(), SwingConstants.CENTER);
		label.setIcon(image);
		label.setForeground(Color.black);
		label.setFont(new Font(null, Font.BOLD, 25));
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setVerticalTextPosition(JLabel.BOTTOM);
		
		
		sidebar = new JPanel();
		sidebar.setBackground(new Color(0xCCCCCC));
		sidebar.setLayout(new GridLayout(5, 1, 10, 10));
		sidebar.add(label);
		sidebar.setPreferredSize(new Dimension(350, 100));
		content = new JPanel();
		content.setLayout(new BorderLayout());
		content.setBackground(new Color(0xCCCCCC));
		
		withdraw = new JButton("Withdraw");
		withdraw.setFont(new Font(null, Font.BOLD, 30));
		withdraw.addActionListener(this);
		deposit = new JButton("Deposit");
		deposit.setFont(new Font(null, Font.BOLD, 25));
		deposit.addActionListener(this);
		transfer = new JButton("Transfer");
		transfer.setFont(new Font(null, Font.BOLD, 25));
		transfer.addActionListener(this);
		quit = new JButton("Quit");
		quit.setFont(new Font(null, Font.BOLD, 25));
		quit.addActionListener(this);
		sidebar.add(withdraw);
		sidebar.add(deposit);
		sidebar.add(transfer);
		sidebar.add(quit);
		
		accNo = new JLabel("Your Account Number: " + customer.getAccNo(), SwingConstants.CENTER);
		accNo.setFont(new Font(null, Font.BOLD, 30));
		accNo.setPreferredSize(new Dimension(350, 100));
		accNo.setOpaque(true);
		accNo.setBackground(new Color(0x123456));
		accNo.setForeground(Color.white);
		accBal = new JLabel("Your Account Balance: #" + customer.getAccBal(), SwingConstants.CENTER);
		accBal.setFont(new Font(null, Font.BOLD, 30));
		accBal.setPreferredSize(new Dimension(350, 100));
		accBal.setOpaque(true);
		accBal.setBackground(new Color(0x123456));
		accBal.setForeground(Color.white);
		topBarContent = new JPanel();
		topBarContent.setLayout(new GridLayout(2, 1, 10, 10));
		mainContent = new JPanel();
		mainContent.setLayout(new BorderLayout());
		mainContent.add(contentMain, BorderLayout.CENTER);
		
		
		topBarContent.add(accNo);
		topBarContent.add(accBal);
		content.add(topBarContent, BorderLayout.NORTH);
		content.add(mainContent, BorderLayout.CENTER);
		
		this.add(sidebar, BorderLayout.WEST);
		this.add(content, BorderLayout.CENTER);
		
		
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
		if (e.getSource() == quit) {
			if (logic != null && logic.success) {
				logic.close();
			}
			System.exit(0);
		} else if (e.getSource() == withdraw) {
			new Withdraw(logic, customer);
			accBal.setText("Your Account Balance: #" + customer.getAccBal());
			accBal.repaint();
			
		} else if (e.getSource() == transfer) {
			new Transfer(logic, customer);
			accBal.setText("Your Account Balance: #" + customer.getAccBal());
			accBal.repaint();
		} else if (e.getSource() == deposit) {
			new Deposit(logic, customer);
			accBal.setText("Your Account Balance: #" + customer.getAccBal());
			accBal.repaint();
		}
	}
}
