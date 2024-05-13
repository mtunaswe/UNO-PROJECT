package card_model;

import java.awt.Color;

public class NumberCard extends Card{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NumberCard(Color cardColor, String cardValue, int score){
		super(cardColor, NUMBERS, cardValue, score);		
	}
	
	@Override
	public boolean match(Card card) {
		if(card instanceof NumberCard) {
			NumberCard otherNumberCard = (NumberCard) card;
			return this.cardColor.equals(card.cardColor) || this.score == otherNumberCard.getScore();
		}
		
		return this.cardColor == card.cardColor;
		
	}
	
	@Override
	public int getScore() {
		return score;
	}
	
	@Override
    public String toString() {
        return cardColor + " " + score;
    }
	
	@Override
	public String getCardValue() {
		return Integer.toString(score);
		
	}

}
