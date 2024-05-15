package card_model;

import java.awt.Color;

/**
 * The NumberCard class represents a numbered card in the UNO game.
 * It extends the Card class and provides specific functionality
 * for numbered cards, including matching logic and score retrieval.
 */
public class NumberCard extends Card {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a NumberCard with the specified color, value, and score.
     *
     * @param cardColor the color of the card
     * @param cardValue the value of the card as a string
     * @param score the score of the card
     */
    public NumberCard(Color cardColor, String cardValue, int score) {
        super(cardColor, NUMBERS, cardValue, score);
    }

    /**
     * Determines if this card matches another card.
     * A NumberCard matches another card if they have the same color or the same score.
     *
     * @param card the card to be compared with this card
     * @return true if the cards match, false otherwise
     */
    @Override
    public boolean match(Card card) {
        if (card instanceof NumberCard) {
            NumberCard otherNumberCard = (NumberCard) card;
            return this.getColor().equals(card.getColor()) || this.score == otherNumberCard.getScore();
        }
        
        return this.cardColor == card.cardColor;
    }

    /**
     * Gets the score of the card.
     *
     * @return the score of the card
     */
    @Override
    public int getScore() {
        return score;
    }

    /**
     * Returns a string representation of the card, including its color and score.
     *
     * @return a string representation of the card
     */
    @Override
    public String toString() {
        return cardColor + " " + score;
    }

    /**
     * Gets the value of the card as a string.
     *
     * @return the value of the card as a string
     */
    @Override
    public String getCardValue() {
        return Integer.toString(score);
    }
}
