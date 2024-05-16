package game_model;

import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.SwingUtilities;
import card_model.WildCard;
import gui.ViewCard;

/**
 * The CPUPlayer class represents a computer-controlled player in the UNO game.
 * It extends the Player class and provides specific functionality for the CPU player,
 * including card playing logic and probability-based actions.
 */
public class CPUPlayer extends Player {
    
    private Random random = new Random();  // Random object for probability checks
    private static final double UNO_PROBABILITY = 0.8;  // 80% chance for CPU to remember to say Uno
    private static final int DELAY_MS = 300;  // Delay in milliseconds for simulating mouse events

    /**
     * Constructs a CPUPlayer with the specified name.
     * 
     * @param name the name of the CPU player
     */
    public CPUPlayer(String name) {
        super(name);  // Call the superclass constructor with the name
        super.setCards();
    }

    /**
     * Copy constructor for CPUPlayer.
     * 
     * @param player the Player object to copy
     */
    public CPUPlayer(Player player) {
        // No implementation provided in the original code
    }
    
    /**
     * Simulates the CPU playing a card.
     * 
     * @param topCard the top card on the discard pile
     * @return true if the CPU played a card, false otherwise
     */
    public boolean play(ViewCard topCard) {
        boolean done = false;
        
        for (ViewCard card : getAllCards()) {
            if (!(card instanceof WildCard)) {
                if (super.canPlay(topCard, card)) {
                    simulateMouseEventWithDelay(card, MouseEvent.MOUSE_PRESSED, DELAY_MS);
                    simulateMouseEventWithDelay(card, MouseEvent.MOUSE_RELEASED, DELAY_MS);
                    done = true;
                    break;
                }
            }
        }
        
        // If no regular card was found, play a wild card
        if (!done) {
            for (ViewCard card : getAllCards()) {
                if (card instanceof WildCard) {
                    simulateMouseEventWithDelay(card, MouseEvent.MOUSE_PRESSED, DELAY_MS);
                    simulateMouseEventWithDelay(card, MouseEvent.MOUSE_RELEASED, DELAY_MS);
                    done = true;
                    break;
                }
            }
        }

        randomlySayUno();
        return done;
    }
    
    /**
     * Simulates the CPU player saying "Uno" with a certain probability.
     */
    private void randomlySayUno() {
        if (getTotalCards() == 1 || getTotalCards() == 2) {
            if (random.nextDouble() < UNO_PROBABILITY) {
                saysUNO();
            }
        }
    }
    
    /**
     * Helper method to dispatch a mouse event with a delay.
     * 
     * @param card the card on which to simulate the mouse event
     * @param eventType the type of mouse event to simulate
     * @param delay the delay in milliseconds before dispatching the event
     */
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
