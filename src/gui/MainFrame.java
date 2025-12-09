package gui;

import javax.swing.JFrame;
import Controller.Rules;
import Interfaces.Constants;
import game_model.Game;



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
    private Game game;

    

    /**
     * Constructs the MainFrame and sets up the game session.
     * 
     * @param game the Game object representing the current game session
     */
    public MainFrame(Game game) { 
    	this.setGame(game);

    	setBounds(0, 0, 1800, 900);
        server = new Rules(game);
        
        CARDLISTENER.setServer(server);
        BUTTONLISTENER.setServer(server);
        
        mainPanel = server.getSession();
        add(mainPanel);
        
    }



	public Game getGame() {
		return game;
	}



	public void setGame(Game game) {
		this.game = game;
	}



}
