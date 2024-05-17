package gui;

import javax.swing.JFrame;
import Controller.Rules;
import Interfaces.Constants;



/**
 * The MainFrame class represents the main window of the application.
 * It initializes and sets up the game rules, listeners, and the main session panel.
 */

public class MainFrame extends JFrame implements Constants{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Session mainPanel;
    private Rules server;
    

    /**
     * Constructs the MainFrame and sets up the game session.
     */
    public MainFrame() { 
    	setBounds(0, 0, 1920, 1080);
        server = new Rules();
        CARDLISTENER.setServer(server);
        BUTTONLISTENER.setServer(server);
        
        mainPanel = server.getSession();
        add(mainPanel);
        
    }
}
