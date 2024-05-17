package Controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import javax.swing.JOptionPane;

import Interfaces.Constants;
import card_model.NumberCard;
import card_model.WildCard;
import game_model.CPUPlayer;
import game_model.Game;
import game_model.Player;
import game_model.UserFileHandler;
import game_model.UserInfo;
import game_model.UserSession;
import gui.Session;
import gui.ViewCard;

public class Rules implements Constants {
    private Game game;
    private Session session;
    private Stack<ViewCard> discardCards;
    
    public boolean canPlay;

    /**
     * Constructs the Rules controller for the game, initializing the game state,
     * setting up the first card, and preparing the game session.
     */
    public Rules() {
        game = new Game();
        discardCards = new Stack<ViewCard>();

        // First Card
        ViewCard firstCard = game.getCard();
        modifyFirstCard(firstCard);

        discardCards.add(firstCard);
        session = new Session(game, firstCard);

        game.whoseTurn();
        canPlay = true;
    }

    /**
     * Custom settings for the first card, if it 
     * is a WildCard.
     *
     * @param firstCard the first card to be modified
     */
    private void modifyFirstCard(ViewCard firstCard) {
        firstCard.removeMouseListener(CARDLISTENER);
        if (firstCard instanceof WildCard) {
            int random = new Random().nextInt() % 4;
            try {
                ((WildCard) firstCard).useWildColor(UNO_COLORS[Math.abs(random)]);
            } catch (Exception ex) {
                System.out.println("something wrong with modifyFirstcard");
            }
        }
    }
    
    /**
     * Returns the main game session.
     *
     * @return the current session
     */
    public Session getSession() {
        return this.session;
    }
    
    /**
     * Requests to play a card and performs necessary checks and actions.
     *
     * @param clickedCard the card to be played
     */
    public void playThisCard(ViewCard clickedCard) {
    	String currentPlayerName = game.getCurrentPlayer().getName(); // Capture the current player's name
        // Check player's turn
        if (!isHisTurn(clickedCard)) {
            infoPanel.setError("It's not your turn");
            infoPanel.repaint();
        } else {
            // Card validation
            if (isValidMove(clickedCard)) {
                clickedCard.removeMouseListener(CARDLISTENER);
                discardCards.add(clickedCard);
                game.removePlayedCard(clickedCard);
                
                // function cards
                switch (clickedCard.getClass().getSimpleName()) {
                    case "ActionCard":
                        performAction(clickedCard);
                        break;
                    case "WildCard":
                        performWild((WildCard) clickedCard);
                        break;
                    default:
                        break;
                }

                game.switchTurn();
                session.updatePanel(clickedCard);
                
                if(clickedCard instanceof NumberCard) {
                	  logNumberPlayEvent(clickedCard, currentPlayerName);
                	
                }else {
                	logFunctionPlayEvent(clickedCard, currentPlayerName);
                }
             
                checkResults();
            } else {
                infoPanel.setError("Invalid move");
                infoPanel.repaint();
            }
        }
        
        
        if (canPlay) {
            if (game.isPCsTurn()) {
                ViewCard playedCard = game.playPC(peekTopCard());
                if (playedCard != null) {
                    game.removePlayedCard(playedCard);
                    session.updatePanel(clickedCard);
                    
                }
            }
        }
    }
    

    private void logNumberPlayEvent(ViewCard playedCard, String currentPlayerName) {
        String cardDetails = playedCard.getColorName() + " " + playedCard.getCardValue();
        String event = currentPlayerName + " played " + cardDetails;
        session.logEvent(event);
    }

    
    private void logFunctionPlayEvent(ViewCard playedCard, String currentPlayerName) {
        String functionDetails = playedCard.getColorName() + " " + playedCard.getCardValue();
        if (playedCard instanceof WildCard) {
            functionDetails += " and chose " + ((WildCard) playedCard).getWildColorName() + " color";
        }
        String event =  currentPlayerName + " played " + functionDetails;
        session.logEvent(event);
    }

    /**
     * Checks if the game is over and sums the scores of the remaining cards in CPUs' decks.
     */
    
   
	private void checkResults() {
        if (game.isOver()) {
            canPlay = false;
            infoPanel.updateText("GAME OVER");
            infoPanel.repaint();

            // Read user data and add them all to users List. 
            List<String[]> users = UserFileHandler.readUserFile();
                   

            for (Player player : game.getPlayers()) {
                if (!player.hasCards()) {
                    infoPanel.updateText(player.getName() + " wins!");
                    logWon(player);
                 
                    UserInfo currentUser = UserSession.getCurrentUser();
                    int wins = currentUser.getWins();
                    int losses = currentUser.getLosses();
                    int totalScore = currentUser.getTotalScore();
                    int totalGames = currentUser.getTotalGames();
                    

                    //update the data for the current user after the session
                    for (String[] userDetails : users) {
                    	
                        if (userDetails[0].equals(UserSession.getCurrentUser().getNickname())) {
                
                            if (!(player instanceof CPUPlayer)) {
                            	
                                int totalCpuScore = game.calculateTotalCpuScore();
                                totalScore += totalCpuScore;
                                wins++;
                                //System.out.println("Total CPU Score: " + totalCpuScore);
                                infoPanel.updateScore(totalCpuScore);
                                infoPanel.repaint();
                                logScore(player, totalScore);
                            } else {
                                losses++;
                            }

                            totalGames++;
                            userDetails[2] = String.valueOf(wins);
                            userDetails[3] = String.valueOf(losses);
                            userDetails[4] = String.valueOf(totalScore);
                            userDetails[5] = String.valueOf(totalGames);

                            break;
                        }
                    }

                    // Write the updated data back to the file
                    UserFileHandler.writeUserFile(users);

                    break;
                }
            }
        }
    }
	
	private void logScore(Player player, int totalScore) {
		String CPUscore = "Total Score of "+ player.getName() + "is" + totalScore;
	
	    session.logEvent(CPUscore);
	        
	}
	
	private void logWon(Player player) {
		String event = player.getName() + " won the UNO game!!! ";
		
		session.logEvent(event);
		
	}
    
    
    

    /**
     * Checks if it's the current player's turn to play the clicked card.
     *
     * @param clickedCard the card clicked by the player
     * @return true if it is the player's turn, false otherwise
     */
    public boolean isHisTurn(ViewCard clickedCard) {
        for (Player p : game.getPlayers()) {
            if (p.hasCard(clickedCard) && p.isMyTurn()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks if the move is valid according to the game rules.
     *
     * @param playedCard the card played by the player
     * @return true if the move is valid, false otherwise
     */
    private boolean isValidMove(ViewCard playedCard) {
        ViewCard topCard = peekTopCard();
        return playedCard.getColor().equals(topCard.getColor()) ||
               playedCard.getCardValue().equals(topCard.getCardValue()) ||
               playedCard instanceof WildCard ||
               (topCard instanceof WildCard && ((WildCard) topCard).getWildColor().equals(playedCard.getColor()));
    }

    /**
     * Performs the action of the action card played.
     *
     * @param actionCard the action card to be played
     */
    private void performAction(ViewCard actionCard) {
        switch (actionCard.getCardValue()) {
            case "2+":
                game.drawPlus(2);
                break;
            case "Skip":
                game.skipTurn();
                break;
            case "Reverse":
                game.reverseDirection();
                break;
        }
    }

    /**
     * Performs the action of the wild card played.
     *
     * @param functionCard the wild card to be played
     */
    private void performWild(WildCard functionCard) {
    	Color chosenColor;
        if (game.isPCsTurn()) {
        	chosenColor = getMostCommonColor((CPUPlayer) game.getCurrentPlayer());
           
            functionCard.useWildColor(chosenColor);    
        } else {
            ArrayList<String> colors = new ArrayList<>();
            colors.add("RED");
            colors.add("BLUE");
            colors.add("GREEN");
            colors.add("YELLOW");

            String selectedColor = (String) JOptionPane.showInputDialog(null,
                    "Choose a color", "Wild Card Color",
                    JOptionPane.DEFAULT_OPTION, null, colors.toArray(), null);

            functionCard.useWildColor(UNO_COLORS[colors.indexOf(selectedColor)]);
        }

        if (functionCard.getCardValue().equals("4+")) {
            game.drawPlus(4);
          
        }
        
        
    }
    
    private Color getMostCommonColor(CPUPlayer cpu) {
        int[] colorCounts = new int[UNO_COLORS.length];
        
        for (ViewCard card : cpu.getAllCards()) {
            Color cardColor = card.getColor();
            for (int i = 0; i < UNO_COLORS.length; i++) {
                if (cardColor.equals(UNO_COLORS[i])) {
                    colorCounts[i]++;
                }
            }
        }

        int maxCountIndex = 0;
        for (int i = 1; i < colorCounts.length; i++) {
            if (colorCounts[i] > colorCounts[maxCountIndex]) {
                maxCountIndex = i;
            }
        }

        return UNO_COLORS[maxCountIndex];
    }


    /**
     * Requests a card to be drawn from the deck.
     */
    public void requestCard() {
        game.drawCard(peekTopCard());

        if (canPlay) {
            if (game.isPCsTurn()) {
                ViewCard playedCard = game.playPC(peekTopCard());
                if (playedCard != null) {
                	game.removePlayedCard(playedCard);
                }
            }
        }
        session.refreshPanel();
    }

    /**
     * Peeks at the top card on the discard stack.
     *
     * @return the top card on the discard stack
     */
    public ViewCard peekTopCard() {
        return discardCards.peek();
    }

    /**
     * Submits the action of saying "UNO" by the player.
     */
    public void submitSaidUNO() {
        game.setSaidUNO();
    }
}
