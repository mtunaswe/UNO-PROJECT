package gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import Controller.ButtonListener;
import game_model.Player;



public class PlayerPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Player player;
	private String name;

	private Box layout;
	private JLayeredPane cardHolder;
	private Box controlPanel;

	private JButton draw;
	private JButton sayUNO;
	private JLabel nameLbl;
	private ButtonHandler handler;
	
	ButtonListener BUTTONLISTENER = new ButtonListener();

	// Constructor
	public PlayerPanel(Player newPlayer) {
		setPlayer(newPlayer);

		layout = Box.createHorizontalBox();
		cardHolder = new JLayeredPane();
		cardHolder.setPreferredSize(new Dimension(600, 175));

		// Set
		setCards();
		setControlPanel();

		layout.add(cardHolder);
		layout.add(controlPanel);
		add(layout);

		// Register Listeners
		handler = new ButtonHandler();
		draw.addActionListener(BUTTONLISTENER);
		draw.addActionListener(handler);
		
		sayUNO.addActionListener(BUTTONLISTENER);
		sayUNO.addActionListener(handler);
	}

	public void setCards() {
		cardHolder.removeAll();

		// Origin point at the center
		Point origin = getPoint(cardHolder.getWidth(), player.getTotalCards());
		int offset = calculateOffset(cardHolder.getWidth(),
				player.getTotalCards());

		int i = 0;
		for (ViewCard card : player.getAllCards()) {
			card.setBounds(origin.x, origin.y, card.CARDSIZE.width,
					card.CARDSIZE.height);
			cardHolder.add(card, i++);
			cardHolder.moveToFront(card);
			origin.x += offset;
		}
		repaint();
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
		setPlayerName(player.getName());
	}

	public void setPlayerName(String playername) {
		this.name = playername;
	}

	private void setControlPanel() {
		
		draw = new JButton("Draw");
		draw.setBackground(new Color(79, 129, 189));
		draw.setFont(new Font("Cabin", Font.BOLD, 20));
		draw.setFocusable(false);
		
		sayUNO = new JButton("Say UNO");
		sayUNO.setBackground(new Color(149, 55, 53));
		sayUNO.setFont(new Font("Cabin", Font.BOLD, 20));
		sayUNO.setFocusable(false);
		
		nameLbl = new JLabel(name);
		nameLbl.setForeground(Color.BLACK);
		nameLbl.setFont(new Font("Cabin", Font.BOLD, 15));

		controlPanel = Box.createVerticalBox();
		controlPanel.add(nameLbl);
		controlPanel.add(draw);
		controlPanel.add(Box.createVerticalStrut(15));
		controlPanel.add(sayUNO);
	}

	private int calculateOffset(int width, int totalCards) {
		int offset = 71;
		if (totalCards <= 8) {
			return offset;
		} else {
			double o = (width - 100) / (totalCards - 1);
			return (int) o;
		}
	}

	private Point getPoint(int width, int totalCards) {
		Point p = new Point(0, 20);
		if (totalCards >= 8) {
			return p;
		} else {
			p.x = (width - calculateOffset(width, totalCards) * totalCards) / 2;
			return p;
		}
	}
	
	class ButtonHandler implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			if(player.isMyTurn()){
				
				if(e.getSource()==draw)
					BUTTONLISTENER.drawCard();
				else if(e.getSource()==sayUNO)
					BUTTONLISTENER.sayUNO();
			}
		}
	}
}
