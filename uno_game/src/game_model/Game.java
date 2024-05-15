package game_model;

import java.util.Stack;

import javax.swing.JOptionPane;

import gui.InfoPanel;
import gui.ViewCard;


public class Game {
	private Player[] players;
	private boolean isOver;
	public static InfoPanel infoPanel;
	private int currentPlayerIndex;

	Direction direction;
	
	private CPUPlayer cpu;
	private DealerShuffler dealer;
	private Stack<ViewCard> cardStack;
	
	
	public enum Direction
	{
		Clockwise, Counter_Clockwise;
	}
	
	public Game(){
		setupGame();
		infoPanel = new InfoPanel();
		isOver = false;
	}
	
	 private void setupGame() {
	        int playerCount = getPlayerCount();
	        players = new Player[playerCount];
	        
	        //Create players
	        
	        // First player is a human player
	        String humanPlayerName = JOptionPane.showInputDialog("Enter your name for display in game:");
	        players[0] = new Player(humanPlayerName);

	        // The rest are CPU players
	        for (int i = 1; i < playerCount; i++) {
	            String cpuName = "CPU " + i;
	            players[i] = new CPUPlayer(cpuName);
	        }

	        // Create Dealer
	        dealer = new DealerShuffler();
	        cardStack = dealer.shuffle();
	        dealer.spreadCards(players);
	        players[0].toggleTurn(); 			// Start game with the human player
	        currentPlayerIndex = 0;
	        direction = Direction.Clockwise;
	    }
	
	 private int getPlayerCount() {
		    int count = 2; // Initialize count

		    while (true) {
		        try {
		            String input = JOptionPane.showInputDialog("How many players? (2-10)");

		            // Check if the input is null (user pressed cancel)
		            if (input == null) {
		                JOptionPane.showMessageDialog(null, "Please enter a number between 2 and 10.");
		                continue;
		            }

		            count = Integer.parseInt(input);

		            // Check if the count is within the valid range
		            if (count >= 2 && count <= 10) {
		                break;
		            } else {
		                JOptionPane.showMessageDialog(null, "Please enter a number between 2 and 10.");
		            }
		        } catch (NumberFormatException e) {
		            // Handle invalid input
		            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number between 2 and 10.");
		        }
		    }

		    return count;
		    
	 }


	public Player[] getPlayers() {
		return players;
	}

	public ViewCard getCard() {
		return dealer.getCard();
	}
	
	public void removePlayedCard(ViewCard playedCard) {

		for (Player p : players) {
			if (p.hasCard(playedCard)){
				p.removeCard(playedCard);
				
				if (p.getTotalCards() == 1 && !p.getSaidUNO()) {
					infoPanel.setError(p.getName() + " Forgot to say UNO");
					p.obtainCard(getCard());
					p.obtainCard(getCard());				//draw two times as a penalty
					
				}else if(p.getTotalCards()>2){
					p.setSaidUNOFalse();
				}
			}			
		}
	}
	

	public void drawCard(ViewCard topCard) {

		boolean canPlay = false;

		for (Player p : players) {
			if (p.isMyTurn()) {
				ViewCard newCard = getCard();
				p.obtainCard(newCard);
				canPlay = p.canPlay(topCard, newCard);
				break;
			}
		}

		if (!canPlay)
			switchTurn();
	}
	
	 public void switchTurn() {
	        players[currentPlayerIndex].toggleTurn();

	        if (direction == Direction.Clockwise) {
	            currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
	        } else {
	            currentPlayerIndex = (currentPlayerIndex - 1 + players.length) % players.length;
	        }

	        players[currentPlayerIndex].toggleTurn();
	        whoseTurn();
	    }
	 
	 
	 public void skipTurn() {
	        switchTurn();
	        switchTurn(); // Skip the next player's turn
	    }
	 
	 
	 public void reverseDirection() {
	        direction = (direction == Direction.Clockwise) ? Direction.Counter_Clockwise : Direction.Clockwise;
	        infoPanel.updateDirection(direction.toString());
	    }

	 public Direction getDirection() {
		 return direction;
	 }

	//Draw cards x times
	 public void drawPlus(int times) {
		 for (Player p : players) {
			 if (!p.isMyTurn()) {
				 for (int i = 1; i <= times; i++)
					 p.obtainCard(getCard());
			 	}
		 	}
	 	}
	
	 //response whose turn it is
	 public void whoseTurn() {

		 for (Player p : players) {
			 if (p.isMyTurn()){
				
				 infoPanel.updateText(p.getName() + "'s Turn");
				 System.out.println(p.getName() + "'s Turn");
				 if(p.getName().contains("CPU")) {
					 cpu = (CPUPlayer) p;
				 }
			 }
		 }
		 infoPanel.setDetail(remainingCards());
		 infoPanel.repaint();
	 }
	
	 //return if the game is over
	 public boolean isOver() {
		 
		 if(cardStack.isEmpty()){
			 isOver= true;
			 return isOver;
		 }
		
		 for (Player p : players) {
			 if (!p.hasCards()) {
				 isOver = true;
				 break;
			 }
		 }
		
		 return isOver;
	 }	

	 public int remainingCards() {
		 return cardStack.size();
	 }

	 public int[] playedCardsSize() {
		 int[] nr = new int[players.length];
		 int i = 0;
		 for (Player p : players) {
			 nr[i] = p.totalPlayedCards();
			 i++;
		 }
		 return nr;
	 }


	 public void setSaidUNO() {
		 for (Player p : players) {
			 if (p.isMyTurn()) {
				 if (p.getTotalCards() == 2) {
					 p.saysUNO();
					 infoPanel.setError(p.getName() + " said UNO");
				 }
			 }
		 }
	 }
	
	 public boolean isPCsTurn(){
		 if(cpu.isMyTurn()){
			 return true;
		 }
		 return false;
	 }

	 //if it's CPU's turn, play it for CPU
	 public void playPC(ViewCard topCard) {		
		
		 if (cpu.isMyTurn()) {
			 boolean playable = cpu.play(topCard);
			 System.out.println(cpu.getName()+"played.");
			 System.out.println(cpu.getTotalCards()+"left");
			 if(!playable)
				 drawCard(topCard);
		 }
	 }
	
	 
}


