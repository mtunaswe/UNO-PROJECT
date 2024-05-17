package card_model;

import java.awt.Color;

/**
 * The WildCard class represents a wild card in the UNO game.
 * It extends the Card class and provides specific functionality
 * for wild cards, including color selection and score retrieval.
 */
public class WildCard extends Card {
    private static final long serialVersionUID = 1L;
    private Color colorSelected;

    /**
     * Constructs a WildCard with the specified value and score.
     * The color of the card is set to black.
     *
     * @param cardValue the value of the card as a string
     * @param score the score of the card
     */
    public WildCard(String cardValue, int score) {
        super(Color.BLACK, WILD, cardValue, score);
    }

    /**
     * Sets the color chosen for the wild card.
     *
     * @param wildColor the chosen color
     */
    public void useWildColor(Color wildColor) {
        colorSelected = wildColor;
    }

    /**
     * Gets the color chosen for the wild card.
     *
     * @return the chosen color
     */
    public Color getWildColor() {
        return colorSelected;
    }

    /**
     * Determines if this card matches another card.
     * A WildCard matches any card.
     *
     * @param other the card to be compared with this card
     * @return true if the cards match, false otherwise
     */
    @Override
    public boolean match(Card other) {
        return true;
    }

    /**
     * Gets the score of the card.
     * A Wild Draw Four card has a score of 50, while a normal Wild card has a score of 40.
     *
     * @return the score of the card
     */
    @Override
    public int getScore() {
        return this.getCardValue().equals("+4") ? 50 : 40; // Wild Draw Four is 50, Normal Wild is 40
    }

    /**
     * Gets the value of the card as a string.
     *
     * @return the value of the card as a string
     */
    @Override
    public String getCardValue() {
        return value;
    }

    /**
     * Returns a string representation of the card, including its type and value.
     *
     * @return a string representation of the card
     */
    @Override
    public String toString() {
        return "Wild " + value;
    }
    
    public String getWildColorName() {
        return colorMap.getOrDefault(colorSelected,"Unknown Color");
    }
}
