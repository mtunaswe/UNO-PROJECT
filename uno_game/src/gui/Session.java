package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import game_model.Game;
import game_model.Player;

/**
 * The Session class represents the main game session panel, 
 * handling the layout of player and table panels.
 */
public class Session extends JPanel {
    private static final long serialVersionUID = 1L;
    private List<JPanel> playerPanels; // General JPanel to handle both PlayerPanel and CPUPanel
    private TablePanel table;
    private static Game game;

    /**
     * Constructs a Session panel for the given game and first card.
     * 
     * @param newGame The game instance.
     * @param firstCard The first card to be displayed on the table.
     */
    public Session(Game newGame, ViewCard firstCard) {
        setPreferredSize(new Dimension(960, 720));
        setBackground(new Color(216, 191, 168)); // Updated to match table's color

        game = newGame;

        playerPanels = new ArrayList<>();
        setPlayers();
        table = new TablePanel(firstCard);

        // Container for CPU panels
        JPanel eastCpuArea = new JPanel();
        JPanel westCpuArea = new JPanel();
        JPanel northCpuArea = new JPanel();

        // Layout settings for each area
        eastCpuArea.setLayout(new BoxLayout(eastCpuArea, BoxLayout.Y_AXIS));
        westCpuArea.setLayout(new BoxLayout(westCpuArea, BoxLayout.Y_AXIS));
        northCpuArea.setLayout(new BoxLayout(northCpuArea, BoxLayout.X_AXIS));

        // Distribute CPU panels
        distributeCPUPanels(eastCpuArea, westCpuArea, northCpuArea);

        // Applying a border to ensure panels do not touch edges
        eastCpuArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        westCpuArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        northCpuArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        setLayout(new BorderLayout());
        add(northCpuArea, BorderLayout.NORTH);
        add(westCpuArea, BorderLayout.WEST);
        add(table, BorderLayout.CENTER);
        add(eastCpuArea, BorderLayout.EAST);
        add(playerPanels.get(0), BorderLayout.SOUTH); // Human player in the south
    }

    /**
     * Distributes CPU panels to the appropriate areas on the game board.
     * 
     * @param east The east area for CPU panels.
     * @param west The west area for CPU panels.
     * @param north The north area for CPU panels.
     */
    private void distributeCPUPanels(JPanel east, JPanel west, JPanel north) {
        int count = 1; // Start from 1 to skip human player
        for (JPanel panel : playerPanels) {
            if (panel instanceof CPUPanel) {
                if (count % 3 == 1) north.add(panel);
                else if (count % 3 == 2) east.add(panel);
                else west.add(panel);
                count++;
            }
        }
    }

    /**
     * Sets the player panels for all players in the game.
     */
    private void setPlayers() {
        Player[] players = game.getPlayers();
        for (int i = 0; players != null && i < players.length; i++) {
            if (i == 0) { // Assuming the first player is always human
                playerPanels.add(new PlayerPanel(players[i]));
            } else {
                playerPanels.add(new CPUPanel(players[i]));
            }
        }
    }

    /**
     * Refreshes the player panels.
     */
    public void refreshPanel() {
        for (JPanel panel : playerPanels) {
            if (panel instanceof PlayerPanel) {
                ((PlayerPanel) panel).setCards();
            }
        }
        table.revalidate();
        revalidate();
    }

    /**
     * Updates the table with the played card and refreshes the player panels.
     * 
     * @param playedCard The card that was played.
     */
    public void updatePanel(ViewCard playedCard) {
        table.setPlayedCard(playedCard);
        refreshPanel();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
