package card_model;

import java.awt.Color;
import java.util.LinkedList;

import Controller.CardListener;
import gui.ViewCard;  // Assuming ViewCard extends JComponent and wraps a Card

public class Deck {
    
    private final Color[] UNO_COLORS = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
    private final int[] UNO_NUMBERS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private final ActionCard.Action[] ActionTypes = {ActionCard.Action.Skip, ActionCard.Action.Reverse, ActionCard.Action.DrawTwo}; // Use ActionCard.Action enum directly
    private final WildCard.Type[] WildTypes = {WildCard.Type.NormalWild, WildCard.Type.DrawFour};

    private LinkedList<ViewCard> UNOcards;
    
    public Deck(){
        
        UNOcards = new LinkedList<ViewCard>();
        
        addCards();
    }

    private void addCards() {
        for(Color color: UNO_COLORS){
            for(int num: UNO_NUMBERS){
                int reps = (num == 0) ? 1 : 2;
                for(int i = 0; i < reps; i++){
                    UNOcards.add(new NumberCard(color, num));
                }
            }
            for(ActionCard.Action action : ActionTypes){
                for(int i = 0; i < 2; i++){
                    UNOcards.add(new ActionCard(color, action));
                }
            }                    
        }
        
        for(WildCard.Type wildType : WildTypes){
            for(int i = 0; i < 4; i++){
                UNOcards.add(new WildCard(wildType));
            }
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
