package card_model;

import java.awt.Color;
import java.util.LinkedList;

import Controller.CardListener;
import Controller.Rules;
import gui.ViewCard;

/**
 * The Deck class represents a deck of UNO cards.
 * It initializes the deck with UNO cards, adds listeners to the cards,
 * and provides methods to access the deck.
 */
public class Deck {

    private final Color[] UNO_COLORS = Rules.getUNO_COLORS();
    private final int[] UNO_NUMBERS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
   
    // ActionCard Functions
    String REVERSE = "Reverse";
    String SKIP = "Skip";
    String DRAW2 = "2+";

    // Wild card functions
    String W_NORMAL = "W";
    String W_DRAW4 = "4+";

    String[] ActionTypes = {REVERSE, SKIP, DRAW2};

    private LinkedList<ViewCard> UNOcards;

    /**
     * Constructs a new Deck and initializes it with UNO cards.
     */
    public Deck() {
        UNOcards = new LinkedList<ViewCard>();
        CardListener CARDLISTENER = new CardListener();

        addCards();
        addCardListener(CARDLISTENER);
    }

    /**
     * Adds UNO cards to the deck.
     * This method initializes the deck with number cards, action cards, and wild cards.
     */
    private void addCards() {
        for (Color color : UNO_COLORS) {
            for (int num : UNO_NUMBERS) {
                int reps = (num == 0) ? 1 : 2;
                for (int i = 0; i < reps; i++) {
                    UNOcards.add(new NumberCard(color, Integer.toString(num), num));
                }
            }
            for (String actionType : ActionTypes) {
                for (int i = 0; i < 2; i++) {
                    UNOcards.add(new ActionCard(color, actionType, 20));
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            UNOcards.add(new WildCard(W_NORMAL, 40));  // Normal Wild card with score of 40
            UNOcards.add(new WildCard(W_DRAW4, 50));   // Draw 4 Wild card with score of 50
        }
    }

    /**
     * Adds a mouse listener to each card in the deck.
     *
     * @param listener the CardListener to be added to the cards
     */
    public void addCardListener(CardListener listener) {
        for (ViewCard card : UNOcards) {
            card.addMouseListener(listener);
        }
    }

    /**
     * Returns the list of cards in the deck.
     *
     * @return the list of cards in the deck
     */
    public LinkedList<ViewCard> getCards() {
        return UNOcards;
    }
}
