package game_model;

import java.util.LinkedList;

import card_model.WildCard;
import gui.ViewCard;

public class Player {
	
	private String name = null;
	private boolean isMyTurn = false;
	private boolean saidUNO = false;
	private LinkedList<ViewCard> cards;
	
	private int playedCards = 0;
	
	public Player(){
		cards = new LinkedList<ViewCard>();
	}
	
	public Player(String player){
		setName(player);
		cards = new LinkedList<ViewCard>();
	}
	
	public void setName(String newName){
		name = newName;
	}
	
	public String getName(){
		return this.name;
	}
	public void obtainCard(ViewCard card){
		cards.add(card);
	}
	
	public LinkedList<ViewCard> getAllCards(){
		return cards;
	}
	
	public int getTotalCards(){
		return cards.size();
	}
	
	public boolean hasCard(ViewCard thisCard){
		return cards.contains(thisCard);		
	}
	
	public void removeCard(ViewCard thisCard){
		cards.remove(thisCard);
		playedCards++;
	}
	
	//Check if this card can be played
	public boolean canPlay(ViewCard topCard, ViewCard newCard) {

		// Color or value matches
		if (topCard.getColor().equals(newCard.getColor())
				|| topCard.getCardValue().equals(newCard.getCardValue()))
			return true;
		// if chosen wild card color matches
		else if (topCard instanceof WildCard)
			return ((WildCard) topCard).getWildColor().equals(newCard.getColor());

		// suppose the new card is a wild card
		else if (newCard instanceof WildCard)
			return true;

		// else
		return false;
		}
	
	
	public int totalPlayedCards(){
		return playedCards;
	}
	
	public void toggleTurn(){
		isMyTurn = !isMyTurn;
	}
	
	public boolean isMyTurn(){
		return isMyTurn;
	}
	
	public boolean hasCards(){
		return !cards.isEmpty();
	}
	
	public boolean getSaidUNO(){
		return saidUNO;
	}
	
	public void saysUNO(){
		saidUNO = true;
	}
	
	public void setSaidUNOFalse(){
		saidUNO = false;
	}
	
	public void setCards(){
		cards = new LinkedList<ViewCard>();
	}
}