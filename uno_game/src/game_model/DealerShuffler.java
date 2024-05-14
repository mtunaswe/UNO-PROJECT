package game_model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Stack;

import card_model.Deck;
import gui.ViewCard;

public class DealerShuffler {
    
    private Deck cardDeck;
    private Stack<ViewCard> cardStack;

    public DealerShuffler() {
        this.cardDeck = new Deck();
        this.cardStack = new Stack<>();
    }

    // Shuffle cards and return a stack of shuffled cards
    public Stack<ViewCard> shuffle() {
        
        LinkedList<ViewCard> deckOfCards = cardDeck.getCards();			// Retrieve the deck of cards
        
        
        Collections.shuffle(deckOfCards); 							// Use Collections.shuffle to shuffle the deck
        
        
        cardStack.clear();											// Clear the stack if it is not empty
        
        
        cardStack.addAll(deckOfCards);								// Add all shuffled cards to the stack	
        
        return cardStack;
    }
    
    // Spread cards to players - 7 each
    public void spreadCards(Player[] players) {        
        for (int i = 1; i <= 7; i++) {
            for (Player p : players) {
                p.obtainCard(cardStack.pop());
            }
        }        
    }
    
    public ViewCard getCard() {
        return cardStack.pop();
    }
}