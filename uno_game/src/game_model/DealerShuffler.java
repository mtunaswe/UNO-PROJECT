package game_model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Stack;
import card_model.Deck;
import gui.ViewCard;

/**
 * The DealerShuffler class is responsible for managing the deck of cards,
 * shuffling them, and distributing them to players.
 */
public class DealerShuffler {
    
    private Deck cardDeck;
    private Stack<ViewCard> cardStack;

    /**
     * Constructs a DealerShuffler with a new deck of cards.
     */
    public DealerShuffler() {
        this.cardDeck = new Deck();
        this.cardStack = new Stack<>();
    }

    /**
     * Shuffles the deck of cards and returns a stack of shuffled cards.
     * 
     * @return a stack of shuffled cards
     */
    public Stack<ViewCard> shuffle() {
        LinkedList<ViewCard> deckOfCards = cardDeck.getCards();  // Retrieve the deck of cards
        Collections.shuffle(deckOfCards);  // Use Collections.shuffle to shuffle the deck
        cardStack.clear();  // Clear the stack if it is not empty
        cardStack.addAll(deckOfCards);  // Add all shuffled cards to the stack
        return cardStack;
    }
    
    /**
     * Distributes cards to players, giving 7 cards to each player.
     * 
     * @param players an array of Player objects to receive cards
     */
    public void spreadCards(Player[] players) {
        for (int i = 1; i <= 7; i++) {
            for (Player p : players) {
                p.obtainCard(cardStack.pop());
            }
        }
    }
    
    /**
     * Retrieves a card from the top of the stack.
     * 
     * @return the top card from the stack
     */
    public ViewCard getCard() {
        return cardStack.pop();
    }
}
