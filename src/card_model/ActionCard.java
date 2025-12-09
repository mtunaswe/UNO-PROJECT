package card_model;

import java.awt.Color;

/**
 * The ActionCard class represents a special card in the game
 * that has an action associated with it, such as Skip or Reverse.
 */
public class ActionCard extends Card {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an ActionCard with the specified color, value, and score.
	 *
	 * @param cardColor the color of the card
	 * @param cardValue the value of the card
	 * @param score     the score of the card
	 */
	public ActionCard(Color cardColor, String cardValue, int score) {
		super(cardColor, ACTION, cardValue, score);		
	}

	/**
	 * Determines if this card matches the specified card.
	 * An action card matches another card if they have the same value
	 * or the same color.
	 *
	 * @param card the card to compare with
	 * @return true if the cards match, false otherwise
	 */
	@Override
	public boolean match(Card card) {
		if (card instanceof ActionCard) {
			ActionCard otherActionCard = (ActionCard) card;
			return (this.value.equals(otherActionCard.getCardValue()) || this.getColor().equals(otherActionCard.getColor()));
		}

		return this.getColor().equals(card.getColor());
	}

	/**
	 * Returns the score of the action card.
	 *
	 * @return the score of the action card
	 */
	@Override
	public int getScore() {
		return 20;
	}

	/**
	 * Returns the value of the action card.
	 *
	 * @return the value of the action card
	 */
	@Override
	public String getCardValue() {
		return value;
	}

	/**
	 * Returns a string representation of the action card.
	 *
	 * @return a string representation of the action card
	 */
	@Override
	public String toString() {
		return cardColor + " " + value;
	}
}
