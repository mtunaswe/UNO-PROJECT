package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import game_model.Game;
import game_model.Player;

public class Session extends JPanel {
    private static final long serialVersionUID = 1L;
    private List<JPanel> playerPanels; // General JPanel to handle both PlayerPanel and CPUPanel
    private TablePanel table;
    private static Game game;
    private JTextArea eventLog;
    

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
        // Distribute CPU panels
        distributeCPUPanels(eastCpuArea, westCpuArea, northCpuArea);
        
        eventLog = new JTextArea(5, 30);
        eventLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(eventLog);
        scrollPane.setPreferredSize(new Dimension(200, 100));

        // Panel to hold player panel and event log
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(playerPanels.get(0), BorderLayout.CENTER);
        southPanel.add(scrollPane, BorderLayout.WEST);
        
        eventLog.append("Welcome to UNO Game\n");
        

        setLayout(new BorderLayout());
        add(northCpuArea, BorderLayout.NORTH);
        add(westCpuArea, BorderLayout.WEST);
        add(table, BorderLayout.CENTER);
        add(eastCpuArea, BorderLayout.EAST); 
        add(southPanel, BorderLayout.SOUTH);
       
    }

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
    

    public List<JPanel> getPlayerPanels() {
        return playerPanels;
    }

    public void refreshPanel() {
        for (JPanel panel : playerPanels) {
            if (panel instanceof PlayerPanel) {
                ((PlayerPanel) panel).setCards();
            }
            else if (panel instanceof CPUPanel) {
                ((CPUPanel) panel).setupCards();
            }
        }
        table.revalidate();
        revalidate();
    }

    public void updatePanel(ViewCard playedCard) {
        table.setPlayedCard(playedCard);
        refreshPanel();
        
    }
    
    /**
     * Logs an event message to the event log.
     * 
     * @param message the event message to log
     */
    public void logEvent(String message) {
        eventLog.append(message + "\n");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}


