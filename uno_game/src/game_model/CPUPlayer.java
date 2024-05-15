package game_model;

import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.SwingUtilities;

import card_model.WildCard;
import gui.ViewCard;

public class CPUPlayer extends Player {
	
	private Random random = new Random();  					// Random object for probability checks
    private static final double UNO_PROBABILITY = 0.8;  	// 80% chance for CPU to remember to say Uno
    private static final int DELAY_MS = 500;

    public CPUPlayer(String name) {
        super(name);  									    // Call the superclass constructor with the name
        super.setCards();
    }

	public CPUPlayer(Player player) {
		
	}
	
	//CPU plays a card
	public boolean play(ViewCard topCard) {

		boolean done = false;
		
		for (ViewCard card : getAllCards()) {
			if(!(card instanceof WildCard)) {
				if(super.canPlay(topCard, card)) {
					simulateMouseEventWithDelay(card, MouseEvent.MOUSE_PRESSED,DELAY_MS);
	                simulateMouseEventWithDelay(card, MouseEvent.MOUSE_RELEASED,DELAY_MS);
					
					done = true;
					break;
					
				}
			}
			
		}
		
		// if no card was found, play wild card
		if(!done) {
			for (ViewCard card : getAllCards()) {
				if (card instanceof WildCard) {
					simulateMouseEventWithDelay(card, MouseEvent.MOUSE_PRESSED,DELAY_MS);
                    simulateMouseEventWithDelay(card, MouseEvent.MOUSE_RELEASED,DELAY_MS);
					
					done = true;
					break;
				}
			}
		}

	
		randomlySayUno();
		
		return done;
	}
	
	 // Check if the CPU says Uno
    private void randomlySayUno() {
        if (getTotalCards() == 1 || getTotalCards() == 2) {
            if (random.nextDouble() < UNO_PROBABILITY) {
                saysUNO();
            }
        }
    }
	
    // Helper method to dispatch a mouse event with a delay
    private void simulateMouseEventWithDelay(ViewCard card, int eventType, int delay) {
        SwingUtilities.invokeLater(() -> {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            MouseEvent mouseEvent = new MouseEvent(card, eventType, System.currentTimeMillis(),
                                                   (int) MouseEvent.MOUSE_EVENT_MASK, 5, 5, 1, true);
            card.dispatchEvent(mouseEvent);
        });
    }
}
