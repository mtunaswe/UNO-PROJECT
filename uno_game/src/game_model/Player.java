package game_model;

import java.util.LinkedList;

import gui.ViewCard;

public class Player {
	
	private String name = null;
	private boolean isMyTurn = false;
	private boolean saidUNO = false;
	private LinkedList<ViewCard> myCards;
	
	private int playedCards = 0;
	
	public Player(){
		myCards = new LinkedList<ViewCard>();
	}
	
	public Player(String player){
		setName(player);
		myCards = new LinkedList<ViewCard>();
	}
	
	public void setName(String newName){
		name = newName;
	}
	
	public String getName(){
		return this.name;
	}
	public void obtainCard(ViewCard card){
		myCards.add(card);
	}
	
	public LinkedList<ViewCard> getAllCards(){
		return myCards;
	}
	
	public int getTotalCards(){
		return myCards.size();
	}
	
	public boolean hasCard(ViewCard thisCard){
		return myCards.contains(thisCard);		
	}
	
	public void removeCard(ViewCard thisCard){
		myCards.remove(thisCard);
		playedCards++;
	}
	
	public int totalPlayedCards(){
		return playedCards;
	}
	
	public void toggleTurn(){
		isMyTurn = (isMyTurn) ? false : true;
	}
	
	public boolean isMyTurn(){
		return isMyTurn;
	}
	
	public boolean hasCards(){
		return (myCards.isEmpty()) ? false : true;
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
		myCards = new LinkedList<ViewCard>();
	}
}