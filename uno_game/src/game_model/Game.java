package game_model;

import java.util.Stack;
import Controller.GameController;
import Interfaces.Constants;
import gui.InfoPanel;
import gui.ViewCard;
import gui.CPUPanel;

public class Game implements Constants{
    private Player[] players;
    private boolean isOver;
    private int currentPlayerIndex;

    Direction direction;
    
    private CPUPlayer cpu;
    private DealerShuffler dealer;
    private Stack<ViewCard> cardStack;

    public enum Direction {
        Clockwise, Counter_Clockwise;
    }

    public Game() {
        isOver = false;
        new GameController(this).setupGame();
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
                    p.obtainCard(getCard()); //draw two times as a penalty
                    
                } else if (p.getTotalCards() > 2) {
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
        switchTurn();
    }
    
    public Direction getDirection() {
        return direction;
    }

    public void drawPlus(int times) {
        Player nextPlayer = getNextPlayer();
        
        for (int i = 0; i < times; i++) {
            nextPlayer.obtainCard(getCard());
        }
    }
    
    private Player getNextPlayer() {
        int nextPlayerIndex;
        if (direction == Direction.Clockwise) {
            nextPlayerIndex = (currentPlayerIndex + 1) % players.length;
        } else {
            nextPlayerIndex = (currentPlayerIndex - 1 + players.length) % players.length;
        }
        return players[nextPlayerIndex];
    }

    public void whoseTurn() {
        for (Player p : players) {
            if (p.isMyTurn()) {
                infoPanel.updateText(p.getName() + "'s Turn");
                System.out.println(p.getName() + "'s Turn");
                if (p.getName().contains("CPU")) {
                    cpu = (CPUPlayer) p;
                }
            }
        }
        infoPanel.setDetail(remainingCards());
        infoPanel.repaint();
    }

    public boolean isOver() {
        if (cardStack.isEmpty()) {
            isOver = true;
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
    
    public boolean isPCsTurn() {
        return cpu.isMyTurn();
    }

    public ViewCard playPC(ViewCard topCard) {
        if (cpu.isMyTurn()) {
            boolean playable = cpu.play(topCard);
            System.out.println(cpu.getName() + " played.");
            System.out.println(cpu.getTotalCards() + " left");
            if (!playable) {
                drawCard(topCard);
                return null;
            }
            return topCard;
        }
        return null;
    }

    // Getters and Setters for private fields
    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public void setDealer(DealerShuffler dealer) {
        this.dealer = dealer;
    }

    public DealerShuffler getDealer() {
        return dealer;
    }

    public void setCardStack(Stack<ViewCard> cardStack) {
        this.cardStack = cardStack;
    }

    public Stack<ViewCard> getCardStack() {
        return cardStack;
    }

    public void setCurrentPlayerIndex(int index) {
        currentPlayerIndex = index;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public InfoPanel getInfoPanel() {
        return infoPanel;
    }
}
