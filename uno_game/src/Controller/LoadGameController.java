package Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import game_model.CPUPlayer;
import game_model.Game;
import game_model.Player;
import gui.ViewCard;

public class LoadGameController {
    private Game game;

    public LoadGameController(Game game) {
        this.game = game;
    }

    public void loadGame(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            Stack<ViewCard> cardStack = new Stack<>();
            Player[] players = new Player[10]; 
            int playerIndex = 0;
            
            List<ViewCard> userHand = new ArrayList<>();
            int[] cpuHandSizes = new int[10];
            int cpuIndex = 0;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("CurrentPlayerIndex:")) {
                    game.setCurrentPlayerIndex(Integer.parseInt(line.split(":")[1]));
                    
                } else if (line.startsWith("Direction:")) {
                    game.setDirection(Game.Direction.valueOf(line.split(":")[1]));
                    
                } else if (line.startsWith("UserHand:")) {
                    String[] cardStrings = line.split(":")[1].split(";");
                    
                    for (String cardString : cardStrings) {
                        userHand.add(new ViewCard(cardString));
                    }
                    
                } else if (line.startsWith("CPUHandSize:")) {
                    cpuHandSizes[cpuIndex++] = Integer.parseInt(line.split(":")[2]);
                    
                } else {

                    String[] parts = line.split(",");
                    if (parts.length == 5) {
                        Player player = parts[0].contains("CPU") ? new CPUPlayer(parts[0]) : new Player(parts[0]);
                        
                        players[playerIndex++] = player;
                    }
                }
            }

            game.setPlayers(players);
            cardStack = game.getDealer().shuffle();
            game.setCardStack(cardStack);

            players[0].setCards();
            if (!userHand.isEmpty()) {
                players[0].setCards(userHand);
            }

            for (int i = 1; i < cpuIndex + 1; i++) {
                for (int j = 0; j < cpuHandSizes[i - 1]; j++) {
                    players[i].obtainCard(game.getCard());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    