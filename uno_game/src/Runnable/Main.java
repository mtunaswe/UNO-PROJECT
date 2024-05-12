package Runnable;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import gui.MainFrame;

@SuppressWarnings("unused")
public class Main {
	
	public static void main(String[] args) {
		
		//Create Frame and invoke it.
		SwingUtilities.invokeLater(new Runnable() {					
			public void run() {
				JFrame frame = new MainFrame();
				frame.setVisible(true);
				frame.setResizable(false);
				frame.setLocation(200, 100);
				frame.pack();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);				
			}
		});	
	}
}
