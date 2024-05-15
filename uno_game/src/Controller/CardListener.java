package Controller;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import gui.ViewCard;

/**
 * The CardListener class is responsible for handling mouse events on the cards.
 * It extends MouseAdapter to provide specific behavior for mousePressed,
 * mouseEntered, and mouseExited events.
 */
public class CardListener extends MouseAdapter{
	private ViewCard sourceCard;
	private static Rules rules;
	
	/**
	 * Sets the rules server for this listener.
	 *
	 * @param rules the Rules object to be set as the server
	 */
	public void setServer(Rules rules){
		CardListener.rules =  rules;
	}
	
	/**
	 * Invoked when a mouse button has been pressed on a component.
	 * This method plays the card if the rules allow it.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mousePressed(MouseEvent e) {		
		sourceCard = (ViewCard) e.getSource();
		
		try{
			if(rules.canPlay)
				rules.playThisCard(sourceCard);			
			
		}catch(NullPointerException ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * Invoked when the mouse enters a component.
	 * This method moves the card upwards by 20 pixels to indicate it is being hovered over.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		super.mouseEntered(e);		
		
		sourceCard = (ViewCard) e.getSource();
		Point p = sourceCard.getLocation();
		p.y -=20;
		sourceCard.setLocation(p);
	}

	/**
	 * Invoked when the mouse exits a component.
	 * This method moves the card downwards by 20 pixels to its original position.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		sourceCard = (ViewCard) e.getSource();
		Point p = sourceCard.getLocation();
		p.y +=20;
		sourceCard.setLocation(p);
	}	

	/**
	 * Invoked when a mouse button has been released on a component.
	 * This method is currently not implemented. It is generated to 
	 * avoid errors.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}	
}
