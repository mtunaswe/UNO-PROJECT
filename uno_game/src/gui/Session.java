package gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import game_model.Game;

public class Session extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PlayerPanel player1;
	private PlayerPanel player2;
	private TablePanel table;	
	
	private Game game;
	
	public Session(Game newGame, ViewCard firstCard){
		setPreferredSize(new Dimension(960,720));
		setBackground(new Color(30,36,40));
		setLayout(new BorderLayout());
		
		game = newGame;
		
		setPlayers();
		table = new TablePanel(firstCard);
		player1.setOpaque(false);
		player2.setOpaque(false);
		
		add(player1,BorderLayout.SOUTH);
		add(table, BorderLayout.CENTER);
		add(player2, BorderLayout.NORTH);		
	}
	
	private void setPlayers() {
		player1 = new PlayerPanel(game.getPlayers()[0]);
		player2 = new PlayerPanel(game.getPlayers()[1]);		
	}
	
	public void refreshPanel(){
		player1.setCards();
		player2.setCards();
		
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


