package card_model;

import java.awt.Color;
import java.util.LinkedList;

import Controller.CardListener;
import gui.ViewCard;  // Assuming ViewCard extends JComponent and wraps a Card

public class Deck {
    
    private final Color[] UNO_COLORS = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
    private final int[] UNO_NUMBERS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    Character charREVERSE = (char) 8634;							//reverse symbol	 "↺"
	Character charSKIP    = (char) Integer.parseInt("2718",16); 	//pass symbol	 "✘"
	
	//ActionCard Functions
	String REVERSE = charREVERSE.toString();
	String SKIP	= charSKIP.toString();
	String DRAW2 = "2+";
	
	//Wild card functions
	String W_NORMAL = "W";
	String W_DRAW4 = "4+";	
	
	String[] ActionTypes = {REVERSE,SKIP,DRAW2};	


    private LinkedList<ViewCard> UNOcards;
    
    public Deck(){
    	
        
        UNOcards = new LinkedList<ViewCard>();
        CardListener CARDLISTENER = new CardListener();
        
        addCards();
        addCardListener(CARDLISTENER);
    }

    private void addCards() {
        for(Color color: UNO_COLORS){
            for(int num: UNO_NUMBERS){
                int reps = (num == 0) ? 1 : 2;
                for(int i = 0; i < reps; i++){
                    UNOcards.add(new NumberCard(color, Integer.toString(num),num));
                }
            }
            for(String actionType : ActionTypes){
                for(int i = 0; i < 2; i++){
                    UNOcards.add(new ActionCard(color, actionType, 20));
                }
            }                    
        }
        
        for (int i = 0; i < 4; i++) {
            UNOcards.add(new WildCard(W_NORMAL, 40));  // Normal Wild card with score of 40
            UNOcards.add(new WildCard(W_DRAW4, 50));   // Draw 4 Wild card with score of 50
        }
        
    }

    public void addCardListener(CardListener listener){
        for(ViewCard card: UNOcards){
            card.addMouseListener(listener);
        }
    }
    
    public LinkedList<ViewCard> getCards(){
        return UNOcards;
    }
}
