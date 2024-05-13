package card_model;

import java.awt.Color;

import gui.ViewCard;

public abstract class Card extends ViewCard{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int score;

	public Card(Color cardColor, int cardType, String cardValue, int score) {
        super(cardColor, cardType, cardValue);  		// Pass parameters to ViewCard's constructor
        this.score = score;
    }

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public abstract boolean match(Card card);
	 
	public abstract String getCardValue();
	
	/*public int compareTo(Card other) {
        int colorDiff = this.color.ordinal() - other.color.ordinal();
        if (colorDiff != 0) {
            return colorDiff;
        }
        return this.getCardValue() - other.getCardValue();
    }*/
	 
}


