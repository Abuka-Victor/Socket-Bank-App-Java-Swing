package com.victor.view;

import javax.swing.SwingUtilities;

public class BankApp {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				
				new Login();

			}
		});
	}
}
