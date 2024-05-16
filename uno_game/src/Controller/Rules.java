package Controller;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import javax.swing.JOptionPane;

import Interfaces.Constants;

import card_model.WildCard;

import game_model.Game;
import game_model.Player;

import gui.Session;
import gui.ViewCard;

public class Rules implements Constants {
    private Game game;
    private Session session;
    private Stack<ViewCard> discardCards;
    
    public boolean canPlay;

    /**
     * Constructs the Rules controller for the game, initializing the game state,
     * setting up the first card, and preparing the game session.
     */
    public Rules() {
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

    /**
     * Custom settings for the first card, if it 
     * is a WildCard.
     *
     * @param firstCard the first card to be modified
     */
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
    
    /**
     * Returns the main game session.
     *
     * @return the current session
     */
    public Session getSession() {
        return this.session;
    }
    
    /**
     * Requests to play a card and performs necessary checks and actions.
     *
     * @param clickedCard the card to be played
     */
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

                // function cards
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
                infoPanel.setError("Invalid move");
                infoPanel.repaint();
            }
        }

        if (canPlay) {
            if (game.isPCsTurn()) {
                ViewCard playedCard = game.playPC(peekTopCard());
                if (playedCard != null) {
                    game.removePlayedCard(playedCard);
                    session.updatePanel(clickedCard);
                }
            }
        }
    }


    /**
     * Checks if the game is over and updates the game state accordingly.
     */
    private void checkResults() {
        if (game.isOver()) {
            canPlay = false;
            infoPanel.updateText("GAME OVER");
        }
    }
    
    /**
     * Checks if it's the current player's turn to play the clicked card.
     *
     * @param clickedCard the card clicked by the player
     * @return true if it is the player's turn, false otherwise
     */
    public boolean isHisTurn(ViewCard clickedCard) {
        for (Player p : game.getPlayers()) {
            if (p.hasCard(clickedCard) && p.isMyTurn()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks if the move is valid according to the game rules.
     *
     * @param playedCard the card played by the player
     * @return true if the move is valid, false otherwise
     */
    private boolean isValidMove(ViewCard playedCard) {
        ViewCard topCard = peekTopCard();
        return playedCard.getColor().equals(topCard.getColor()) ||
               playedCard.getCardValue().equals(topCard.getCardValue()) ||
               playedCard instanceof WildCard ||
               (topCard instanceof WildCard && ((WildCard) topCard).getWildColor().equals(playedCard.getColor()));
    }

    /**
     * Performs the action of the action card played.
     *
     * @param actionCard the action card to be played
     */
    private void performAction(ViewCard actionCard) {
        switch (actionCard.getCardValue()) {
            case "+2":
                game.drawPlus(2);
                break;
            case "Skip":
                game.skipTurn();
                break;
            case "Reverse":
                game.reverseDirection();
                break;
        }
    }

    /**
     * Performs the action of the wild card played.
     *
     * @param functionCard the wild card to be played
     */
    private void performWild(WildCard functionCard) {
        if (game.isPCsTurn()) {
            int random = new Random().nextInt(UNO_COLORS.length);
            functionCard.useWildColor(UNO_COLORS[random]);
        } else {
            ArrayList<String> colors = new ArrayList<>();
            colors.add("RED");
            colors.add("BLUE");
            colors.add("GREEN");
            colors.add("YELLOW");

            String chosenColor = (String) JOptionPane.showInputDialog(null,
                    "Choose a color", "Wild Card Color",
                    JOptionPane.DEFAULT_OPTION, null, colors.toArray(), null);

            functionCard.useWildColor(UNO_COLORS[colors.indexOf(chosenColor)]);
        }

        if (functionCard.getCardValue().equals("DrawFour")) {
            game.drawPlus(4);
        }
    }

    /**
     * Requests a card to be drawn from the deck.
     */
    public void requestCard() {
        game.drawCard(peekTopCard());

        if (canPlay) {
            if (game.isPCsTurn()) {
                ViewCard playedCard = game.playPC(peekTopCard());
                if (playedCard != null) {
                	game.removePlayedCard(playedCard);
                }
            }
        }
        session.refreshPanel();
    }

    /**
     * Peeks at the top card on the discard stack.
     *
     * @return the top card on the discard stack
     */
    public ViewCard peekTopCard() {
        return discardCards.peek();
    }

    /**
     * Submits the action of saying "UNO" by the player.
     */
    public void submitSaidUNO() {
        game.setSaidUNO();
    }
}
