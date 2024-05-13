package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import game_model.Player;

public class CPUPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Player player;
    private Box layout;
    private JLayeredPane cardHolder;
    private JLabel nameLabel;

    public CPUPanel(Player newPlayer) {
        this.player = newPlayer;
        layout = Box.createHorizontalBox();
        cardHolder = new JLayeredPane();
        cardHolder.setPreferredSize(new Dimension(600, 175));

        setupCards();
        setupControlPanel();

        layout.add(cardHolder);
        layout.add(Box.createHorizontalStrut(40));
        layout.add(nameLabel);
        add(layout);
    }

    private void setupCards() {
        cardHolder.removeAll();

        Point origin = new Point(20, 20); // Start position for cards
        for (int i = 0; i < player.getTotalCards(); i++) {
                ViewCard card = new ViewCard();
                card.toggleCardFace(); // Set to show the back
                card.setBounds(origin.x, origin.y, card.getPreferredSize().width, card.getPreferredSize().height);
                cardHolder.add(card, i);
                origin.x += 15; // Offset for overlapping cards
       }
    }

    private void setupControlPanel() {
        nameLabel = new JLabel(player.getName());
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Cabin", Font.BOLD, 18));
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}