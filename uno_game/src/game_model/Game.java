package game_model;

import java.util.Stack;

import Controller.GameController;

import Interfaces.Constants;

import gui.ViewCard;

/**
 * Represents the main game logic and state for the UNO game.
 * Handles player turns, card drawing, and game rules.
 */
public class Game implements Constants {
    private Player[] players;
    private boolean isOver;
    private int currentPlayerIndex;

    Direction direction;
    
    public CPUPlayer cpu;
    private DealerShuffler dealer;
    private Stack<ViewCard> cardStack;

    /**
     * Enum representing the direction of play.
     */
    public enum Direction {
        Clockwise, Counter_Clockwise;
    }

    /**
     * Constructs a new Game and sets up the initial game state.
     */
    public Game() {
        isOver = false;
        new GameController(this).setupGame();
    }

    /**
     * Retrieves a card from the dealer.
     * @return the card drawn from the dealer.
     */
    public ViewCard getCard() {
        return dealer.getCard();
    }
    
    /**
     * Removes a played card from the player's hand and handles UNO penalties.
     * @param playedCard the card that was played.
     */
    public void removePlayedCard(ViewCard playedCard) {
        for (Player p : players) {
            if (p.hasCard(playedCard)){
                p.removeCard(playedCard);
                
                if (p.getTotalCards() == 1 && !p.getSaidUNO()) {
                    infoPanel.setError(p.getName() + " Forgot to say UNO");
                    infoPanel.repaint();
                    p.obtainCard(getCard());
                    p.obtainCard(getCard()); // Draw two cards as a penalty
                } else if (p.getTotalCards() > 2) {
                    p.setSaidUNOFalse();
                }
            }            
        }
    }
    
    /**
     * Draws a card for the current player. If the player cannot play the drawn card, the turn is switched.
     * @param topCard the top card on the discard pile.
     */
    public void drawCard(ViewCard topCard) {
        boolean canPlay = false;

        for (Player p : players) {
            if (p.isMyTurn()) {
                ViewCard newCard = getCard();
                p.obtainCard(newCard);
                canPlay = p.canPlay(topCard, newCard);
                break;
            }
        }

        if (!canPlay)
            switchTurn();
    }
    
    /**
     * Switches the turn to the next player based on the direction of play.
     */
    public void switchTurn() {
    	players[currentPlayerIndex].toggleTurn();
    	

        if (direction == Direction.Clockwise) {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
        } else {
            currentPlayerIndex = (currentPlayerIndex - 1 + players.length) % players.length;
        }
        

        players[currentPlayerIndex].toggleTurn();
        
        Player np = players[currentPlayerIndex];
        
        if(np.getName().contains("CPU")) {
        	cpu = (CPUPlayer) np;
        }
        whoseTurn();
    }
    
    /**
     * Skips the turn of the next player.
     */
    public void skipTurn() {
        switchTurn();
        switchTurn(); // Skip the next player's turn
    }
    
    /**
     * Reverses the direction of play.
     */
    public void reverseDirection() {
        direction = (direction == Direction.Clockwise) ? Direction.Counter_Clockwise : Direction.Clockwise;
        infoPanel.updateDirection(direction.toString());
        infoPanel.repaint();
        switchTurn();
    }
    
    /**
     * Gets the current direction of play.
     * @return the current direction.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Draws a specified number of cards for the next player.
     * @param times the number of cards to draw.
     */
    public void drawPlus(int times) {
        Player nextPlayer = getNextPlayer();
        
        for (int i = 0; i < times; i++) {
            nextPlayer.obtainCard(getCard());
        }
        
        switchTurn();
    }
    
    /**
     * Helper method to get the next player in turn based on the direction of play.
     * @return the next player.
     */
    private Player getNextPlayer() {
        int nextPlayerIndex;
        if (direction == Direction.Clockwise) {
            nextPlayerIndex = (currentPlayerIndex + 1) % players.length;
        } else {
            nextPlayerIndex = (currentPlayerIndex - 1 + players.length) % players.length;
        }
         
        return players[nextPlayerIndex];
    }

    /**
     * Updates the InfoPanel to show whose turn it is.
     */
    public void whoseTurn() {
        for (Player p : players) {
            if (p.isMyTurn()) {
                infoPanel.updateText(p.getName() + "'s Turn");
                System.out.println(p.getName() + "'s Turn");
            	}
        }
        infoPanel.setRemaining(remainingCards());
        infoPanel.repaint();
    }
    
    /**
     * Calculates the total score of cards left in all CPUs' hands.
     * @return the total score of cards in CPUs' hands.
     */
    public int calculateTotalCpuScore() {
        int totalScore = 0;
        for (Player player : players) {
            if (player instanceof CPUPlayer) {
                totalScore += player.calculateTotalScore();
            }
        }
        return totalScore;
    }
    
    /**
     * Gets the current player.
     * @return the current player.
     */
    public Player getCurrentPlayer() {
        return players[currentPlayerIndex];
    }


    /**
     * Checks if the game is over.
     * @return true if the game is over, false otherwise.
     */
    public boolean isOver() {
        if (cardStack.isEmpty()) {
            isOver = true;
            return isOver;
        }

        for (Player p : players) {
            if (!p.hasCards()) {
                isOver = true;
                break;
            }
        }

        return isOver;
    }

    /**
     * Gets the number of remaining cards in the deck.
     * @return the number of remaining cards.
     */
    public int remainingCards() {
        return cardStack.size();
    }

    /**
     * Gets the number of cards played by each player.
     * @return an array with the number of cards played by each player.
     */
    public int[] playedCardsSize() {
        int[] nr = new int[players.length];
        int i = 0;
        for (Player p : players) {
            nr[i] = p.totalPlayedCards();
            i++;
        }
        return nr;
    }

    /**
     * Sets the "UNO" status for the player who has said UNO.
     */
    public void setSaidUNO() {
        for (Player p : players) {
            if (p.isMyTurn()) {
                if (p.getTotalCards() == 2) {
                    p.saysUNO();
                    infoPanel.setError(p.getName() + " said UNO");
                    infoPanel.repaint();
                }
            }
        }
    }
    
    /**
     * Checks if it is the CPU's turn to play.
     * @return true if it is the CPU's turn, false otherwise.
     */
    public boolean isPCsTurn() {
        return cpu.isMyTurn();
    }

    /**
     * Plays a card for the CPU.
     * @param topCard the top card on the discard pile.
     * @return the card played by the CPU, or null if no card was played.
     */
    public ViewCard playPC(ViewCard topCard) {
        if (cpu.isMyTurn()) {
            boolean playable = cpu.play(topCard);
            System.out.println(cpu.getName() + " played.");
            System.out.println(cpu.getTotalCards() + " left");
            if (!playable) {
                drawCard(topCard);
                return null;
            }
            return topCard;
        }
        return null;
    }

    // Getters and Setters for private fields
    
    public Player[] getPlayers() {
        return players;
    }

    public CPUPlayer getCpu() {
		return cpu;
	}

	public void setCpu(CPUPlayer cpu) {
		this.cpu = cpu;
	}

	public void setPlayers(Player[] players) {
        this.players = players;
    }

    public void setDealer(DealerShuffler dealer) {
        this.dealer = dealer;
    }

    public DealerShuffler getDealer() {
        return dealer;
    }

    public void setCardStack(Stack<ViewCard> cardStack) {
        this.cardStack = cardStack;
    }

    public Stack<ViewCard> getCardStack() {
        return cardStack;
    }

    public void setCurrentPlayerIndex(int index) {
        currentPlayerIndex = index;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

}
