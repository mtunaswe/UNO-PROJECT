package card_model;

import java.awt.Color;

public class ActionCard extends Card{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	public ActionCard(Color cardColor, String cardValue,int score){
		super(cardColor,ACTION, cardValue,score);		
	}
	

	@Override
    public boolean match(Card card) {
        if (card instanceof ActionCard) {
            ActionCard otherActionCard= (ActionCard) card;
            return (this.value.equals(otherActionCard.getCardValue()) || this.getColor().equals(otherActionCard.getColor()));
        }
        
        return this.getColor().equals(card.getColor());
    }



	@Override
	public int getScore() {
		return 20;
	}
	
	@Override
	public String getCardValue() {
		return value;
	}
	
	@Override
	public String toString() {
        return cardColor + " " + value;
    }
	

}
