package game_model;

import java.util.LinkedList;

import card_model.Card;
import card_model.WildCard;
import gui.ViewCard;

/**
 * The Player class represents a player in the game, managing the player's 
 * cards, turn status, and actions.
 */
public class Player {
    private String name = null;
    private boolean isMyTurn = false;
    private boolean saidUNO = false;
    private LinkedList<ViewCard> cards;
    private int playedCards = 0;

    /**
     * Constructs a new Player object with an empty list of cards.
     */
    public Player() {
        cards = new LinkedList<ViewCard>();
    }

    /**
     * Constructs a new Player object with a given name.
     * 
     * @param player the name of the player
     */
    public Player(String player) {
        setName(player);
        cards = new LinkedList<ViewCard>();
    }

    /**
     * Sets the player's name.
     * 
     * @param newName the new name of the player
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * Gets the player's name.
     * 
     * @return the player's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Adds a card to the player's hand.
     * 
     * @param card the card to be added
     */
    public void obtainCard(ViewCard card) {
        cards.add(card);
    }

    /**
     * Gets all the cards in the player's hand.
     * 
     * @return a LinkedList of ViewCard objects representing the player's hand
     */
    public LinkedList<ViewCard> getAllCards() {
        return cards;
    }

    /**
     * Gets the total number of cards in the player's hand.
     * 
     * @return the number of cards in the player's hand
     */
    public int getTotalCards() {
        return cards.size();
    }

    /**
     * Checks if the player has a specific card.
     * 
     * @param thisCard the card to check for
     * @return true if the player has the card, false otherwise
     */
    public boolean hasCard(ViewCard thisCard) {
        return cards.contains(thisCard);
    }

    /**
     * Removes a specific card from the player's hand and increments the played card count.
     * 
     * @param thisCard the card to be removed
     */
    public void removeCard(ViewCard thisCard) {
        cards.remove(thisCard);
        playedCards++;
    }

    /**
     * Checks if a new card can be played based on the top card of the discard pile.
     * 
     * @param topCard the top card of the discard pile
     * @param newCard the new card to be played
     * @return true if the new card can be played, false otherwise
     */
    public boolean canPlay(ViewCard topCard, ViewCard newCard) {
        // Color or value matches
        if (topCard.getColor().equals(newCard.getColor()) || topCard.getCardValue().equals(newCard.getCardValue())) {
            return true;
        }
        // If chosen wild card color matches
        else if (topCard instanceof WildCard) {
            return ((WildCard) topCard).getWildColor().equals(newCard.getColor());
        }
        // Suppose the new card is a wild card
        else if (newCard instanceof WildCard) {
            return true;
        }
        // Otherwise
        return false;
    }
    
    /**
     * Calculates the total score of cards left in the player's hand.
     * @return the total score of the cards in the player's hand.
     */
    public int calculateTotalScore() {
        int totalScore = 0;
        for (ViewCard card : cards) {
            if (card instanceof Card) {
                totalScore += ((Card) card).getScore();
            }
        }
        return totalScore;
    }

    /**
     * Gets the total number of cards played by the player.
     * 
     * @return the total number of played cards
     */
    public int totalPlayedCards() {
        return playedCards;
    }

    /**
     * Toggles the player's turn status.
     */
    public void toggleTurn() {
        isMyTurn = !isMyTurn;
    }

    /**
     * Checks if it is the player's turn.
     * 
     * @return true if it is the player's turn, false otherwise
     */
    public boolean isMyTurn() {
        return isMyTurn;
    }

    /**
     * Checks if the player has any cards.
     * 
     * @return true if the player has cards, false otherwise
     */
    public boolean hasCards() {
        return !cards.isEmpty();
    }

    /**
     * Gets the status of whether the player has said UNO.
     * 
     * @return true if the player has said UNO, false otherwise
     */
    public boolean getSaidUNO() {
        return saidUNO;
    }

    /**
     * Sets the status of the player saying UNO to true.
     */
    public void saysUNO() {
        saidUNO = true;
    }

    /**
     * Sets the status of the player saying UNO to false.
     */
    public void setSaidUNOFalse() {
        saidUNO = false;
    }

    /**
     * Resets the player's cards to an empty list.
     */
    public void setCards() {
        cards = new LinkedList<ViewCard>();
    }
    
    
}
