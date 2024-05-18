package Runnable;

/************** Pledge of Honor ******************************************
I hereby certify that I have completed this programming project on my own
without any help from anyone else. The effort in the project thus belongs
completely to me. I did not search for a solution, or I did not consult any
program written by others or did not copy any program from other sources. I
read and followed the guidelines provided in the project description.
READ AND SIGN BY WRITING YOUR NAME SURNAME AND STUDENT ID
SIGNATURE: <Mert Tuna, 80363>
*************************************************************************/

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
