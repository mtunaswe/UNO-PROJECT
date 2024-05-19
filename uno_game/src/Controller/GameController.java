package Controller;

import java.util.Stack;
import javax.swing.JOptionPane;

import Interfaces.Constants;
import game_model.CPUPlayer;
import game_model.DealerShuffler;
import game_model.Game;
import game_model.Player;
import game_model.UserSession;
import gui.ViewCard;

public class GameController implements Constants{
    private Game game;

    /**
     * Constructs a GameController for the specified game.
     *
     * @param game the game instance to control
     */
    public GameController() {
    	game = new Game();
    
    }

	/**
     * Sets up the game by initializing players, shuffling and distributing cards,
     * and setting the initial game state.
     */

	public void setupGame() {

        int playerCount = getPlayerCount();

        if (playerCount == 0) {
            return; 
        }
        
        String sessionName = JOptionPane.showInputDialog("Enter session name: ");
        if (sessionName == null) {
        	return;
        }

        game.setSessionName(sessionName);

        Player[] players = new Player[playerCount];
        
        // Create players
        // First player is a human player
        
        
        players[0] = new Player(UserSession.getCurrentUser().getNickname());
        //players[0] = new Player(UserSession.getCurrentUser().getNickname());
        

        // The rest are CPU players
        for (int i = 1; i < playerCount; i++) {
            String cpuName = "CPU " + i;
            players[i] = new CPUPlayer(cpuName);
        }

        // Set players in the game
        game.setPlayers(players);
        game.setDealer(new DealerShuffler());
        Stack<ViewCard> cardStack = game.getDealer().shuffle();
        game.setCardStack(cardStack);
        game.getDealer().spreadCards(players);
        players[0].toggleTurn(); // Start game with the human player
        game.setCurrentPlayerIndex(0);
        game.setDirection(Game.Direction.Clockwise);
        
        
       
    }
    
    /**
     * Prompts the user to input the number of players and validates the input.
     *
     * @return the number of players (between 2 and 10)
     */
	private int getPlayerCount() {
		int count;
	    while (true) {
	        try {
	            String input = JOptionPane.showInputDialog("How many players? (2-10)");

	            // Check if the input is null (user pressed cancel)
	            if (input == null) {
	                return 0; 
	            }
	            
	            count = Integer.parseInt(input);

	            if (count >= 2 && count <= 10) {
	                return count;
	            } else {
	                JOptionPane.showMessageDialog(null, "Please enter a number between 2 and 10.");
	            }
	        } catch (NumberFormatException e) {
	            // Handle invalid input
	            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number between 2 and 10.");
	        }
	    }
	}
    
    
    
    public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}


	
}
    

