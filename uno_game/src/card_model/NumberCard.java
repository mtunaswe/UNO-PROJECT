package card_model;

import java.awt.Color;

public class NumberCard extends Card{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int number;

	public NumberCard(Color color, int number) {
		super(color);
		this.number = number;
	}
	 
	
	@Override
	public boolean match(Card card) {
		if(card instanceof NumberCard) {
			NumberCard otherNumberCard = (NumberCard) card;
			return this.color.equals(card.color) || this.number == otherNumberCard.number;
		}
		
		return this.color == card.color;
		
	}
	
	@Override
	public int getScore() {
		return number;
	}
	
	@Override
    public String toString() {
        return color + " " + number;
    }
	
	@Override
	public String getCardValue() {
		return null;
		
	}

}
