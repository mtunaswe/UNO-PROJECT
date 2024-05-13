package gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import game_model.Player;
import game_model.Game;

public class Session extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<JPanel> playerPanels; // General JPanel to handle both PlayerPanel and CPUPanel
    private TablePanel table;
    private Game game;

    public Session(Game newGame, ViewCard firstCard) {
        setPreferredSize(new Dimension(960, 720));
        setBackground(new Color(30, 36, 40));
        setLayout(new BorderLayout());

        game = newGame;
        
        playerPanels = new ArrayList<>();
        setPlayers();
        table = new TablePanel(firstCard);

        JPanel playerArea = new JPanel();
        playerArea.setLayout(new BoxLayout(playerArea, BoxLayout.Y_AXIS));
        for (JPanel panel : playerPanels) {
            panel.setOpaque(false);
            playerArea.add(panel);
        }

        add(playerArea, BorderLayout.CENTER);
        add(table, BorderLayout.CENTER);
    }

    private void setPlayers() {
        Player[] players = game.getPlayers();
        for (int i = 0; i < players.length; i++) {
            if (i == 0) { // Assuming the first player is always human
                playerPanels.add(new PlayerPanel(players[i]));
            } else {
                playerPanels.add(new CPUPanel(players[i]));
            }
        }
    }
	
	public void refreshPanel(){
		for (JPanel panel : playerPanels) {
			if (panel instanceof PlayerPanel) {
                ((PlayerPanel) panel).setCards();
		}	
	}       
		table.revalidate();		
		revalidate();
	}
	
	public void updatePanel(ViewCard playedCard){
		table.setPlayedCard(playedCard);
		refreshPanel();
	}	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}


