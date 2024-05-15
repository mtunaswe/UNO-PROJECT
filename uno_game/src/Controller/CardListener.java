package Controller;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import gui.ViewCard;

public class CardListener extends MouseAdapter{
	private ViewCard sourceCard;
	private static Rules rules;
	
	public void setServer(Rules rules){
		CardListener.rules =  rules;
	}
	
	public void mousePressed(MouseEvent e) {		
		sourceCard = (ViewCard) e.getSource();
		
		try{
			if(rules.canPlay)
				rules.playThisCard(sourceCard);			
			
		}catch(NullPointerException ex){
			ex.printStackTrace();
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		super.mouseEntered(e);		
		
		sourceCard = (ViewCard) e.getSource();
		Point p = sourceCard.getLocation();
		p.y -=20;
		sourceCard.setLocation(p);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		sourceCard = (ViewCard) e.getSource();
		Point p = sourceCard.getLocation();
		p.y +=20;
		sourceCard.setLocation(p);
	}	

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}	


}
