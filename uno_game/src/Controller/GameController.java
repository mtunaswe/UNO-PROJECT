package Controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;
import javax.swing.JOptionPane;

import game_model.CPUPlayer;
import game_model.DealerShuffler;
import game_model.Game;
import game_model.Player;
import gui.ViewCard;

public class GameController {
    private Game game;
    
    /**
     * Constructs a GameController for the specified game.
     *
     * @param game the game instance to control
     */
    public GameController(Game game) {
        this.game = game;
    }
    
    /**
     * Sets up the game by initializing players, shuffling and distributing cards,
     * and setting the initial game state.
     */
    public void setupGame() {
        int playerCount = getPlayerCount();
        Player[] players = new Player[playerCount];
        
        // Create players
        // First player is a human player
        String humanPlayerName = JOptionPane.showInputDialog("Enter your name for display in game:");
        players[0] = new Player(humanPlayerName);

        // The rest are CPU players
        for (int i = 1; i < playerCount; i++) {
            String cpuName = "CPU " + i;
            players[i] = new CPUPlayer(cpuName);
        }

        // Set players in the game
        game.setPlayers(players);
        game.setDealer(new DealerShuffler());
        Stack<ViewCard> cardStack = game.getDealer().shuffle();
        game.setCardStack(cardStack);
        game.getDealer().spreadCards(players);
        players[0].toggleTurn(); // Start game with the human player
        game.setCurrentPlayerIndex(0);
        game.setDirection(Game.Direction.Clockwise);
    }
    
    /**
     * Prompts the user to input the number of players and validates the input.
     *
     * @return the number of players (between 2 and 10)
     */
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
    
    public void saveGame(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Save players
            for (Player player : game.getPlayers()) {
                writer.write(player.getName());
                writer.newLine();
            }

            // Save current player index
            writer.write("CurrentPlayerIndex:" + 0);
            writer.newLine();

            // Save direction
            writer.write("Direction:" + game.getDirection().name());
            writer.newLine();
            
            // Save user hand and CPU hand sizes
            for (Player player : game.getPlayers()) {
                if (player instanceof CPUPlayer) {
                    writer.write("CPUHandSize:" + player.getName() + ":" + player.getTotalCards());
                } else {
                    writer.write("UserHand:");
                    for (ViewCard card : player.getAllCards()) {
                        writer.write(card.toString() + ";");
                    }
                }
                writer.newLine();
            }

         

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    

