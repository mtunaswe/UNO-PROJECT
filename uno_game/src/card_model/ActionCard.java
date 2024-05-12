package card_model;

import java.awt.Color;

public class ActionCard extends Card{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	enum Action{
		DrawTwo, Skip, Reverse 
	}
	
	private Action action;
	

	public ActionCard(Color color, Action action) {
		super(color);
		this.action = action;
	}

	
	
	@Override
    public boolean match(Card card) {
        if (card instanceof ActionCard) {
            ActionCard otherActionCard= (ActionCard) card;
            return (this.action == otherActionCard.action || this.color.equals(otherActionCard.color));
        }
        
        return this.color.equals(card.color);
    }



	@Override
	public int getScore() {
		return 20;
	}
	
	@Override
	public String getCardValue() {
		return action.toString();
	}
	
	@Override
	public String toString() {
        return color + " " + action;
    }
	












}
