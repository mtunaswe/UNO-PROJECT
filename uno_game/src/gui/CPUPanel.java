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
    private JLabel nameLabel;			  //label to display name of CPUs.
    private JLabel cardCountLabel;        //label to display remaining cards of CPUs.
    private Box controlPanel;

    public CPUPanel(Player newPlayer) {
        this.player = newPlayer;
        layout = Box.createHorizontalBox();
        cardHolder = new JLayeredPane();
        cardHolder.setPreferredSize(new Dimension(200, 175));

        setupCards();
        setupControlPanel();

        layout.add(cardHolder);
        layout.add(Box.createHorizontalStrut(5));
        layout.add(controlPanel);
        add(layout);
        

    }

    private void setupCards() {
        cardHolder.removeAll();

        Point origin = new Point(10, 20); 		
        int offset = 10; 
        int cardCount = player.getTotalCards();
        
        for (int i = 0; i < player.getTotalCards(); i++) {
                ViewCard card = new ViewCard();
                card.toggleCardFace(); 			// Set to show the back of the card
                card.setBounds(origin.x, origin.y, card.getPreferredSize().width, card.getPreferredSize().height);
                cardHolder.add(card, i);
                origin.x += offset; 			// Offset for overlapping cards
       }
        
        updateCardCountLabel(cardCount);
    }

    private void setupControlPanel() {
        nameLabel = new JLabel(player.getName());
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setFont(new Font("Cabin", Font.BOLD, 18));
        
        cardCountLabel = new JLabel();
        cardCountLabel.setForeground(Color.GRAY);
        cardCountLabel.setFont(new Font("Cabin", Font.BOLD, 16));
        updateCardCountLabel(player.getTotalCards());
        
        controlPanel = Box.createVerticalBox();
        controlPanel.add(nameLabel);
        controlPanel.add(Box.createVerticalStrut(10)); // Adds vertical space between name label and card count label
        controlPanel.add(cardCountLabel);
        
        
    }

    public void updateCardCountLabel(int count) {
    	 if (cardCountLabel != null) { // Null check to prevent NPE
             cardCountLabel.setText("Cards: " + count);
         }
    }
    
    public void refreshPanel() {
        setupCards(); 			// Re-setup cards which also updates card count
        revalidate();
        repaint();
    }

      

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}