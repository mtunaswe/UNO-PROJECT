package Runnable;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import gui.LoginPage;

public class Main {
	
	public static void main(String[] args) {
		
		//Create Frame and invoke it.
		SwingUtilities.invokeLater(new Runnable() {					
			public void run() {
				JFrame frame = new LoginPage();
				frame.setVisible(true);
				frame.setResizable(false);
				frame.setLocation(500, 150);
				frame.pack();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);				
			}
		});	
	}
}
