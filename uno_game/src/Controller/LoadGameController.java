package Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import Interfaces.Constants;
import game_model.CPUPlayer;
import game_model.DealerShuffler;
import game_model.Game;
import game_model.Player;
import gui.ViewCard;

public class LoadGameController implements Constants{
    private Game game;

    public LoadGameController() {
        game = new Game();
    }

    public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public void loadGame(String filePath) {
		int playerCount = 0;
		
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("CurrentPlayerIndex:") && 
                    !line.startsWith("Direction:") && 
                    !line.startsWith("UserHand:") && 
                    !line.startsWith("CPUHand:")) {
                    playerCount++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
		
		Player[] players = new Player[playerCount];
        List<ViewCard> userHand = new ArrayList<>();
        ArrayList<ArrayList<ViewCard>> cpusHand = new ArrayList<>();
		
		
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int playerIndex = 0;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("CurrentPlayerIndex:")) {
                    game.setCurrentPlayerIndex(Integer.parseInt(line.split(":")[1]));

                } else if (line.startsWith("Direction:")) {
                    game.setDirection(Game.Direction.valueOf(line.split(":")[1]));

                } else if (line.startsWith("UserHand:")) {
                    String[] cardStrings = line.split(":")[1].split(";");
                    for (String cardString : cardStrings) {
                        if (!cardString.isEmpty()) {
                        	ViewCard card = new ViewCard(cardString);
                            card.addMouseListener(CARDLISTENER);
                            userHand.add(card);
                            
                        }
                    }

                } else if (line.startsWith("CPUHand:")) {
                	ArrayList<ViewCard> individualCpuHand = new ArrayList<>();
                    String[] cardStrings = line.split(":")[1].split(";");
                    for (String cardString : cardStrings) {
                        if (!cardString.isEmpty()) {
                        	ViewCard card = new ViewCard(cardString);
                            card.addMouseListener(CARDLISTENER);
                            individualCpuHand.add(card);
  
                        }
                    }
                    
                    cpusHand.add(individualCpuHand);
                   
                    
                    
                } else {
                    Player player = (line.contains("CPU")) ? new CPUPlayer(line) : new Player(line);
                    players[playerIndex++] = player;
                }
            }


            game.setPlayers(players);
            game.setDealer(new DealerShuffler());
            Stack<ViewCard> cardStack = game.getDealer().shuffle();
            game.setCardStack(cardStack);
           
            players[0].setCards(userHand);
            
  
            players[0].toggleTurn();
            
            
            for (int i = 0; i < cpusHand.size(); i++) {
                players[i + 1].setCards(cpusHand.get(i));
                
              
            }
                 

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
    }
}
