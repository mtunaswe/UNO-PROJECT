package Controller;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import Interfaces.Constants;
import game_model.CPUPlayer;
import game_model.Game;
import game_model.Player;
import gui.ViewCard;
import card_model.NumberCard;
import card_model.ActionCard;
import card_model.WildCard;

/**
 * The LoadGameController class is responsible for loading a saved game from a file and setting up the game state accordingly.
 */
public class LoadGameController implements Constants {
    private Game game;

    /**
     * Constructs a LoadGameController and initializes a new Game object.
     * Sets the game state to indicate that it is a loaded game.
     */
    public LoadGameController() {
        game = new Game();
        game.setLoadedGame(true);
    }

    /**
     * Gets the current Game object.
     * 
     * @return the current Game object
     */
    public Game getGame() {
        return game;
    }

    /**
     * Sets the Game object.
     * 
     * @param game the Game object to set
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Loads the game state from a file specified by the file path.
     * 
     * @param filePath the path of the file to load the game state from
     */
    public void loadGame(String filePath) {
        int playerCount = 0;
        String sessionName = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("SessionName:")) {
                    sessionName = line.split(":")[1];
                } else if (!line.startsWith("CurrentPlayerIndex:") &&
                           !line.startsWith("Direction:") &&
                           !line.startsWith("UserHand:") &&
                           !line.startsWith("CPUHand:") &&
                           !line.startsWith("Deck:") &&
                           !line.startsWith("LastCardPlayed:")) {
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
        Stack<ViewCard> cardStack = new Stack<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int playerIndex = 0;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("SessionName:")) {
                    sessionName = line.split(":")[1];
                    game.setSessionName(sessionName);

                } else if (line.startsWith("CurrentPlayerIndex:")) {
                    game.setCurrentPlayerIndex(Integer.parseInt(line.split(":")[1]));

                } else if (line.startsWith("Direction:")) {
                    game.setDirection(Game.Direction.valueOf(line.split(":")[1]));

                } else if (line.startsWith("UserHand:")) {
                    String[] cardStrings = line.split(":")[1].split(";");
                    for (String cardString : cardStrings) {
                        if (!cardString.isEmpty()) {
                            ViewCard card = createCardFromString(cardString);
                            card.addMouseListener(CARDLISTENER);
                            userHand.add(card);
                        }
                    }

                } else if (line.startsWith("CPUHand:")) {
                    ArrayList<ViewCard> individualCpuHand = new ArrayList<>();
                    String[] cardStrings = line.split(":")[1].split(";");
                    for (String cardString : cardStrings) {
                        if (!cardString.isEmpty()) {
                            ViewCard card = createCardFromString(cardString);
                            card.addMouseListener(CARDLISTENER);
                            individualCpuHand.add(card);
                        }
                    }
                    cpusHand.add(individualCpuHand);

                } else if (line.startsWith("Deck:")) {
                    String[] cardStrings = line.split(":")[1].split(";");
                    for (String cardString : cardStrings) {
                        if (!cardString.isEmpty()) {
                            ViewCard card = createCardFromString(cardString);
                            cardStack.add(card);
                        }
                    }

                } else if (line.startsWith("LastCardPlayed:")) {
                    String cardString = line.split(":")[1];
                    ViewCard lastCardPlayed = createCardFromString(cardString);
                    game.setLastCardPlayed(lastCardPlayed);

                } else {
                    Player player = (line.contains("CPU")) ? new CPUPlayer(line) : new Player(line);
                    players[playerIndex++] = player;
                }
            }

            game.setPlayers(players);

            for (ViewCard card : cardStack) {
                card.addMouseListener(CARDLISTENER);
            }

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

    /**
     * Creates a ViewCard object from a string representation of the card.
     * 
     * @param cardString the string representation of the card
     * @return the ViewCard object created from the string
     */
    private ViewCard createCardFromString(String cardString) {
        String[] parts = cardString.split(",");
        String colorName = parts[0];
        int cardType = Integer.parseInt(parts[1]);
        String value = parts[2];

        Color color = getColorByName(colorName);

        switch (cardType) {
            case 1: 
                return new NumberCard(color, value, Integer.parseInt(value));
            case 2: 
                return new ActionCard(color, value, 20);
            case 3: 
                return new WildCard(value.equals("+4") ? W_DRAW4 : W_NORMAL, value.equals("+4") ? 50 : 40);
            default:
                throw new IllegalArgumentException("Unknown card type: " + cardType);
        }
    }

    /**
     * Gets the Color object corresponding to the color name.
     * 
     * @param colorName the name of the color
     * @return the Color object corresponding to the color name
     */
    private Color getColorByName(String colorName) {
        switch (colorName) {
            case "Red":
                return RED;
            case "Blue":
                return BLUE;
            case "Green":
                return GREEN;
            case "Yellow":
                return YELLOW;
            default:
                return null;
        }
    }
}
