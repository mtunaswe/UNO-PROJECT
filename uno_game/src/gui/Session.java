package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import game_model.Game;
import game_model.Player;

/**
 * The Session class represents the main game session panel for the UNO game.
 * It includes player panels, the game table, and an event log for tracking game events.
 */
public class Session extends JPanel {
    private static final long serialVersionUID = 1L;
    private List<JPanel> playerPanels; // General JPanel to handle both PlayerPanel and CPUPanel
    private TablePanel table;
    private static Game game;
    private JTextArea eventLog;
    private JButton saveLogButton;

    /**
     * Constructs a new Session panel.
     *
     * @param newGame The current game instance.
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

        eventLog = new JTextArea(5, 30);
        eventLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(eventLog);
        scrollPane.setPreferredSize(new Dimension(300, 100));

        // Add the save log button
        saveLogButton = new JButton("Save Log");
        saveLogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveLogToFile();
            }
        });

        // Panel to hold the event log and save button
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.add(scrollPane, BorderLayout.CENTER);
        logPanel.add(saveLogButton, BorderLayout.SOUTH);

        // Panel to hold player panel and log panel
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(playerPanels.get(0), BorderLayout.CENTER);
        southPanel.add(logPanel, BorderLayout.WEST);

        eventLog.append("Welcome to UNO Game!\n");

        setLayout(new BorderLayout());
        add(northCpuArea, BorderLayout.NORTH);
        add(westCpuArea, BorderLayout.WEST);
        add(table, BorderLayout.CENTER);
        add(eastCpuArea, BorderLayout.EAST);
        add(southPanel, BorderLayout.SOUTH);
    }

    /**
     * Distributes the CPU panels in the east, west, and north areas of the game session.
     *
     * @param east The panel to hold CPU panels on the east side.
     * @param west The panel to hold CPU panels on the west side.
     * @param north The panel to hold CPU panels on the north side.
     */
    private void distributeCPUPanels(JPanel east, JPanel west, JPanel north) {
        int totalCPUs = playerPanels.size() - 1; // Excluding the human player
        int groupSize = totalCPUs / 3;
        int extra = totalCPUs % 3;

        int count = 0;
        for (JPanel panel : playerPanels) {
            if (panel instanceof CPUPanel) {
                if (count < groupSize + (extra > 0 ? 1 : 0)) {
                    west.add(panel, 0); // Adding to the bottom of the stack
                    extra--;
                } else if (count < 2 * groupSize + (extra > 1 ? 1 : 0)) {
                    north.add(panel);
                } else {
                    east.add(panel);
                }
                count++;
            }
        }
    }

    /**
     * Sets the players for the session.
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
     * Gets the list of player panels.
     *
     * @return The list of player panels.
     */
    public List<JPanel> getPlayerPanels() {
        return playerPanels;
    }

    /**
     * Refreshes the player panels and table panel.
     */
    public void refreshPanel() {
        for (JPanel panel : playerPanels) {
            if (panel instanceof PlayerPanel) {
                ((PlayerPanel) panel).setCards();
            } else if (panel instanceof CPUPanel) {
                ((CPUPanel) panel).setupCards();
            }
        }
        table.revalidate();
        revalidate();
    }

    /**
     * Updates the table panel with the newly played card and refreshes the player panels.
     *
     * @param playedCard The card that has been played.
     */
    public void updatePanel(ViewCard playedCard) {
        table.setPlayedCard(playedCard);
        refreshPanel();
    }

    /**
     * Logs an event message to the event log.
     *
     * @param message The event message to log.
     */
    public void logEvent(String message) {
        eventLog.append(message + "\n");
    }

    /**
     * Saves the contents of the event log to a file.
     */
    private void saveLogToFile() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getPath();
            if (!filePath.toLowerCase().endsWith(".txt")) {
                filePath += ".txt";
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                eventLog.write(writer);
                JOptionPane.showMessageDialog(this, "Log saved successfully!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving log: " + e.getMessage());
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
