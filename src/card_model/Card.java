package card_model;

import java.awt.Color;
import gui.ViewCard;

/**
 * The abstract class Card represents a generic card in the game.
 * It extends the ViewCard class and adds attributes and methods 
 * specific to the card model.
 */
public abstract class Card extends ViewCard {
	
	private static final long serialVersionUID = 1L;
	protected int score;

	/**
	 * Constructs a Card with the specified color, type, value, and score.
	 *
	 * @param cardColor the color of the card
	 * @param cardType  the type of the card
	 * @param cardValue the value of the card
	 * @param score     the score of the card
	 */
	public Card(Color cardColor, int cardType, String cardValue, int score) {
		super(cardColor, cardType, cardValue); // Pass parameters to ViewCard's constructor
		this.score = score;
	}

	/**
	 * Returns the score of the card.
	 *
	 * @return the score of the card
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Sets the score of the card.
	 *
	 * @param score the new score of the card
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Determines if this card matches the specified card.
	 *
	 * @param card the card to compare with
	 * @return true if the cards match, false otherwise
	 */
	public abstract boolean match(Card card);

	/**
	 * Returns the value of the card.
	 *
	 * @return the value of the card
	 */
	public abstract String getCardValue();

	/*
	 * This method is not implemented well yet,
	 * it is meant to sort cards on the hand.
	 * 
	public int compareTo(Card other) {
		int colorDiff = this.color.ordinal() - other.color.ordinal();
		if (colorDiff != 0) {
			return colorDiff;
		}
		return this.getCardValue() - other.getCardValue();
	}
	*/
}


