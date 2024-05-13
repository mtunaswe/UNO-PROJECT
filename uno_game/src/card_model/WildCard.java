package card_model;

import java.awt.Color;

public class WildCard extends Card{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Color colorSelected;
	
	public WildCard(String cardValue, int score){
		super(Color.BLACK, WILD, cardValue, score);		
	}
	
	
	public void useWildColor(Color wildColor){
		colorSelected = wildColor;
	}
	
	public Color getWildColor(){
		return colorSelected;
	}
	
	@Override
	public boolean match(Card other) {
		return true;
		
	}
	
	@Override
    public int getScore() {
        return this.getCardValue().equals("+4") ? 50 : 40; 		// Wild Draw Four is 50, Normal Wild is 40
    }
	
	@Override
	public String getCardValue() {
		return value;
	}

    @Override
    public String toString() {
        return "Wild " + value;
    }

	
}
