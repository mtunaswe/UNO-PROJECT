package Controller;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import javax.swing.JOptionPane;

import card_model.WildCard;
import game_model.Game;
import game_model.Player;
import gui.InfoPanel;
import gui.Session;
import gui.ViewCard;

public class Rules {
	private Game game;
	private Session session;
	private Stack<ViewCard> discardCards;
	
	public static Color[] getUNO_COLORS() {
		return UNO_COLORS;
	}

	public boolean canPlay;

	InfoPanel infoPanel = new InfoPanel();
	CardListener CARDLISTENER = new CardListener();
	
	public static Color RED = new Color(216, 46, 63);
	public static Color BLUE = new Color(53, 129, 216);
	public static Color GREEN = new Color(40, 204, 45);
	public static Color YELLOW = new Color(255, 225, 53);
	public static Color[] UNO_COLORS = {RED, BLUE, GREEN, YELLOW};

	public Rules() {
		//mode = requestMode();
		
		game = new Game();
		discardCards = new Stack<ViewCard>();

		// First Card
		ViewCard firstCard = game.getCard();
		modifyFirstCard(firstCard);

		discardCards.add(firstCard);
		session = new Session(game, firstCard);

		game.whoseTurn();
		canPlay = true;
	}

	
	//custom settings for the first card
	private void modifyFirstCard(ViewCard firstCard) {
		firstCard.removeMouseListener(CARDLISTENER);
		if (firstCard instanceof WildCard) {
			int random = new Random().nextInt() % 4;
			try {
				((WildCard) firstCard).useWildColor(UNO_COLORS[Math.abs(random)]);
			} catch (Exception ex) {
				System.out.println("something wrong with modifyFirstcard");
			}
		}
	}
	
	//return Main Panel
	public Session getSession() {
		return this.session;
	}
	
	
	//request to play a card
	public void playThisCard(ViewCard clickedCard) {

		// Check player's turn
		if (!isHisTurn(clickedCard)) {
			infoPanel.setError("It's not your turn");
			infoPanel.repaint();
		} else {

			// Card validation
			if (isValidMove(clickedCard)) {

				clickedCard.removeMouseListener(CARDLISTENER);
				discardCards.add(clickedCard);
				game.removePlayedCard(clickedCard);

				// function cards ??
				switch (clickedCard.getClass().getSimpleName()) {
				case "ActionCard":
					performAction(clickedCard);
					break;
				case "WildCard":
					performWild((WildCard) clickedCard);
					break;
				default:
					break;
				}

				game.switchTurn();
				session.updatePanel(clickedCard);
				checkResults();
			} else {
				infoPanel.setError("invalid move");
				infoPanel.repaint();
			}
			
		}
		
		
		
		if(canPlay){
			if(game.isPCsTurn()){
				game.playPC(peekTopCard());
			}
		}
	}

	//Check if the game is over
	private void checkResults() {

		if (game.isOver()) {
			canPlay = false;
			infoPanel.updateText("GAME OVER");
		}
	}
	
	//check player's turn
	public boolean isHisTurn(ViewCard clickedCard) {

		for (Player p : game.getPlayers()) {
			if (p.hasCard(clickedCard) && p.isMyTurn())
				return true;
		}
		return false;
	}
	

	//check if it is a valid card
	  private boolean isValidMove(ViewCard playedCard) {
	        ViewCard topCard = peekTopCard();
	        
	        return playedCard.getColor().equals(topCard.getColor()) ||
	               playedCard.getCardValue().equals(topCard.getCardValue()) ||
	               playedCard instanceof WildCard ||
	               (topCard instanceof WildCard && ((WildCard) topCard).getWildColor().equals(playedCard.getColor()));
	   
	  }	
	

		

	// ActionCards
	private void performAction(ViewCard actionCard) {
        switch (actionCard.getCardValue()) {
            case "DrawTwo":
                game.drawPlus(2);
                break;
            case "Skip":
                game.switchTurn();
                break;
            case "Reverse":
                game.switchTurn();
                
                game.reverseDirection();
                
                break;
        }
    }

	private void performWild(WildCard functionCard) {		

		//System.out.println(game.whoseTurn());
		if(game.isPCsTurn()){			
			int random = new Random().nextInt(UNO_COLORS.length);
			functionCard.useWildColor(UNO_COLORS[random]);
		}
		else{
			
			ArrayList<String> colors = new ArrayList<String>();
			colors.add("RED");
			colors.add("BLUE");
			colors.add("GREEN");
			colors.add("YELLOW");
			
			String chosenColor = (String) JOptionPane.showInputDialog(null,
					"Choose a color", "Wild Card Color",
					JOptionPane.DEFAULT_OPTION, null, colors.toArray(), null);
	
			functionCard.useWildColor(UNO_COLORS[colors.indexOf(chosenColor)]);
		}
		
		if (functionCard.getCardValue().equals("DrawFour"))
			game.drawPlus(4);
	}
	
	public void requestCard() {
		game.drawCard(peekTopCard());
		
		if(canPlay){
			if(game.isPCsTurn())
				game.playPC(peekTopCard());
		}
		
		session.refreshPanel();
	}

	public ViewCard peekTopCard() {
		return discardCards.peek();
	}

	public void submitSaidUNO() {
		game.setSaidUNO();
	}

}

