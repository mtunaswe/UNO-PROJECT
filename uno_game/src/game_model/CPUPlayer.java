package game_model;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Random;

import card_model.WildCard;
import game_model.Player;
import gui.ViewCard;

public class CPUPlayer extends Player {
	
	private Random random = new Random();  					// Random object for probability checks
    private static final double UNO_PROBABILITY = 0.8;  	// 80% chance for CPU to remember to say Uno

    public CPUPlayer(String name) {
        super(name);  									    // Call the superclass constructor with the name
        super.setCards();
    }

	public CPUPlayer(Player player) {
		
	}
	
	//CPU plays a card
	public boolean play(ViewCard topCard) {

		boolean done = false;

		Color color = topCard.getColor();
		String value = topCard.getCardValue();
		
		if(topCard instanceof WildCard){
			color = ((WildCard) topCard).getWildColor();			
		}

		for (ViewCard card : getAllCards()) {

			if (card.getColor().equals(color) || card.getCardValue().equals(value)) {
				
				simulateMouseEvent(card, MouseEvent.MOUSE_PRESSED);
                simulateMouseEvent(card, MouseEvent.MOUSE_RELEASED);
				
				done = true;
				break;
			}
		}

		// if no card was found, play wild card
		if (!done) {
			for (ViewCard card : getAllCards()) {
				if (card instanceof WildCard) {
					simulateMouseEvent(card, MouseEvent.MOUSE_PRESSED);
                    simulateMouseEvent(card, MouseEvent.MOUSE_RELEASED);
					
					done = true;
					break;
				}
			}
		}
		
		if(getTotalCards()==1 || getTotalCards()==2){
			if(random.nextDouble() < UNO_PROBABILITY) {
				saysUNO();
				
			}
		}
		
		return done;
	}
	
	// Helper method to dispatch a mouse event
    private void simulateMouseEvent(ViewCard card, int eventType) {
        MouseEvent mouseEvent = new MouseEvent(card, eventType, System.currentTimeMillis(),
                                               (int) MouseEvent.MOUSE_EVENT_MASK, 5, 5, 1, true);
        card.dispatchEvent(mouseEvent);
    }
}
