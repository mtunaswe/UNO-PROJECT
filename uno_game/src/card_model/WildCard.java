package card_model;

import java.awt.Color;

public class WildCard extends Card{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	enum Type{
		NormalWild, DrawFour;
	}

	private Type type;
	private Color colorSelected;
	
	public WildCard(Type type) {
		super(null);
		this.type = type;
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
        return type == Type.DrawFour ? 50 : 40; // Wild Draw Four is 50, Normal Wild is 40
    }
	
	@Override
	public String getCardValue() {
		return type.toString();
	}

    @Override
    public String toString() {
        return "Wild " + type;
    }

	
}
