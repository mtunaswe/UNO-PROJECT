package card_model;

import java.awt.Color;

import gui.ViewCard;

public abstract class Card extends ViewCard{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Color color;

	public Card(Color color) {
		this.color = color;
		
	 }

	public Color getColor() {
		return color;
	 }
	 

	public void setColor(Color color) {
		this.color = color;
	}

	public abstract boolean match(Card card);
	 
	public abstract int getScore();
	
	public abstract String getCardValue();
	
	/*public int compareTo(Card other) {
        int colorDiff = this.color.ordinal() - other.color.ordinal();
        if (colorDiff != 0) {
            return colorDiff;
        }
        return this.getCardValue() - other.getCardValue();
    }*/
	 
}


