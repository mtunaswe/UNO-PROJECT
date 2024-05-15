package gui;

import javax.swing.JFrame;
import Controller.Rules;
import Controller.ButtonListener;
import Controller.CardListener;

/**
 * The MainFrame class represents the main window of the application.
 * It initializes and sets up the game rules, listeners, and the main session panel.
 */

public class MainFrame extends JFrame {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Session mainPanel;
    private Rules server;
    private CardListener CARDLISTENER = new CardListener();
    private ButtonListener BUTTONLISTENER = new ButtonListener();

    /**
     * Constructs the MainFrame and sets up the game session.
     */
    public MainFrame() {    
        server = new Rules();
        CARDLISTENER.setServer(server);
        BUTTONLISTENER.setServer(server);
        
        mainPanel = server.getSession();
        add(mainPanel);
    }
}
