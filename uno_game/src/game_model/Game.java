package game_model;

import java.util.Stack;
import Controller.GameController;
import gui.InfoPanel;
import gui.ViewCard;

/**
 * The Game class manages the overall state and rules of the game, including 
 * player turns, the direction of play, and card interactions.
 */
public class Game {
    private Player[] players;
    private boolean isOver;
    public static InfoPanel infoPanel;
    private int currentPlayerIndex;
    Direction direction;
    private CPUPlayer cpu;
    private DealerShuffler dealer;
    private Stack<ViewCard> cardStack;

    /**
     * Enum to represent the direction of the game.
     */
    public enum Direction {
        Clockwise, Counter_Clockwise;
    }

    /**
     * Constructs a new Game object and initializes the game setup.
     */
    public Game() {
        infoPanel = new InfoPanel();
        isOver = false;
        new GameController(this).setupGame();
    }

    /**
     * Retrieves a card from the dealer.
     * 
     * @return a ViewCard object
     */
    public ViewCard getCard() {
        return dealer.getCard();
    }

    /**
     * Removes a played card from the player's hand and checks for penalties.
     * 
     * @param playedCard the card that was played
     */
    public void removePlayedCard(ViewCard playedCard) {
        for (Player p : players) {
            if (p.hasCard(playedCard)) {
                p.removeCard(playedCard);

                if (p.getTotalCards() == 1 && !p.getSaidUNO()) {
                    infoPanel.setError(p.getName() + " Forgot to say UNO");
                    p.obtainCard(getCard());
                    p.obtainCard(getCard()); // Draw two cards as a penalty
                } else if (p.getTotalCards() > 2) {
                    p.setSaidUNOFalse();
                }
            }
        }
    }

    /**
     * Allows a player to draw a card and checks if they can play it.
     * 
     * @param topCard the card on the top of the discard pile
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

        if (!canPlay) {
            switchTurn();
        }
    }

    /**
     * Switches the turn to the next player based on the current direction.
     */
    public void switchTurn() {
        players[currentPlayerIndex].toggleTurn();

        if (direction == Direction.Clockwise) {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
        } else {
            currentPlayerIndex = (currentPlayerIndex - 1 + players.length) % players.length;
        }

        players[currentPlayerIndex].toggleTurn();
        whoseTurn();
    }

    /**
     * Skips the next player's turn.
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
        switchTurn();
    }

    /**
     * Gets the current direction of the game.
     * 
     * @return the current direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Draws a specified number of cards for the next player.
     * 
     * @param times the number of cards to draw
     */
    public void drawPlus(int times) {
        Player nextPlayer = getNextPlayer();

        for (int i = 0; i < times; i++) {
            nextPlayer.obtainCard(getCard());
        }
    }

    /**
     * Helper method to get the next player in turn based on the game direction.
     * 
     * @return the next player
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
     * Displays whose turn it is and updates the InfoPanel.
     */
    public void whoseTurn() {
        for (Player p : players) {
            if (p.isMyTurn()) {
                infoPanel.updateText(p.getName() + "'s Turn");
                System.out.println(p.getName() + "'s Turn");
                if (p.getName().contains("CPU")) {
                    cpu = (CPUPlayer) p;
                }
            }
        }
        infoPanel.setDetail(remainingCards());
        infoPanel.repaint();
    }

    /**
     * Checks if the game is over.
     * 
     * @return true if the game is over, false otherwise
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
     * Gets the number of remaining cards in the stack.
     * 
     * @return the number of remaining cards
     */
    public int remainingCards() {
        return cardStack.size();
    }

    /**
     * Gets the number of played cards for each player.
     * 
     * @return an array of the number of played cards for each player
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
     * Sets the said UNO status for the current player.
     */
    public void setSaidUNO() {
        for (Player p : players) {
            if (p.isMyTurn()) {
                if (p.getTotalCards() == 2) {
                    p.saysUNO();
                    infoPanel.setError(p.getName() + " said UNO");
                }
            }
        }
    }

    /**
     * Checks if it's the CPU's turn.
     * 
     * @return true if it's the CPU's turn, false otherwise
     */
    public boolean isPCsTurn() {
        return cpu.isMyTurn();
    }

    /**
     * Plays the turn for the CPU if it's the CPU's turn.
     * 
     * @param topCard the card on the top of the discard pile
     */
    public void playPC(ViewCard topCard) {
        if (cpu.isMyTurn()) {
            boolean playable = cpu.play(topCard);
            System.out.println(cpu.getName() + " played.");
            System.out.println(cpu.getTotalCards() + " left");
            if (!playable) {
                drawCard(topCard);
            }
        }
    }

    // Getters and Setters for private fields

    public Player[] getPlayers() {
        return players;
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

    public InfoPanel getInfoPanel() {
        return infoPanel;
    }
}
