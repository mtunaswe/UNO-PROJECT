package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.util.List;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import game_model.Player;

/**
 * CPUPanel is a custom JPanel that represents a CPU player's hand and status in the game.
 * It displays the CPU player's cards, name, and the number of cards remaining.
 */
public class CPUPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private Player player;
    private Box layout;
    private JLayeredPane cardHolder;
    private JLabel nameLabel;              // Label to display name of CPUs.
    private JLabel cardCountLabel;         // Label to display remaining cards of CPUs.
    private Box controlPanel;

    /**
     * Constructor for CPUPanel.
     * 
     * @param newPlayer the CPU player represented by this panel
     */
    public CPUPanel(Player newPlayer) {
        this.player = newPlayer;
        setBackground(new Color(216, 191, 168)); // Set background color to match the table
        layout = Box.createHorizontalBox();
        cardHolder = new JLayeredPane();
        cardHolder.setPreferredSize(new Dimension(200, 175));
        cardHolder.setBackground(new Color(216, 191, 168)); // Set background color

        setupCards();
        setupControlPanel();

        layout.add(cardHolder);
        layout.add(Box.createHorizontalStrut(5));
        layout.add(controlPanel);
        add(layout);
    }

    /**
     * Sets up the cards in the cardHolder. It creates card components and places them
     * in the cardHolder with a slight offset to show overlapping.
     */
    public void setupCards() {
        cardHolder.removeAll();

        Point origin = new Point(10, 20);         
        int offset = 10; 
        int cardCount = player.getTotalCards();
        
        for (int i = 0; i < player.getTotalCards(); i++) {
                ViewCard card = new ViewCard();
                card.toggleCardFace();             // Set to show the back of the card
                card.setBounds(origin.x, origin.y, card.getPreferredSize().width, card.getPreferredSize().height);
                cardHolder.add(card, i);
                origin.x += offset;             // Offset for overlapping cards
       }
        
        updateCardCountLabel(cardCount);
    }

    /**
     * Sets up the control panel which contains the player's name and the card count label.
     */
    private void setupControlPanel() {
        nameLabel = new JLabel(player.getName());
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setFont(new Font("Cabin", Font.BOLD, 18));
        
        cardCountLabel = new JLabel();
        cardCountLabel.setForeground(Color.GRAY);
        cardCountLabel.setFont(new Font("Cabin", Font.BOLD, 16));
        updateCardCountLabel(player.getTotalCards());
        
        controlPanel = Box.createVerticalBox();
        controlPanel.setBackground(new Color(205, 133, 63)); // Set background color
        controlPanel.add(nameLabel);
        controlPanel.add(Box.createVerticalStrut(10)); // Adds vertical space between name label and card count label
        controlPanel.add(cardCountLabel);
    }

    /**
     * Updates the card count label with the current number of cards.
     * 
     * @param count the current number of cards
     */
    public void updateCardCountLabel(int count) {
         if (cardCountLabel != null) { // Null check to prevent NPE
             cardCountLabel.setText("Cards: " + count);
         }
    }
    
    /**
     * Refreshes the panel by re-setting up the cards and updating the UI.
     */
    public void refreshPanel() {
        setupCards();             // Re-setup cards which also updates card count
        revalidate();
        repaint();
    }

    /**
     * Gets the player associated with this panel.
     * 
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the player associated with this panel.
     * 
     * @param player the player to set
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Removes the played card from the CPU's hand and updates the display.
     * 
     * @param playedCard the card that was played
     */
    public void removePlayedCard(ViewCard playedCard) {
        List<ViewCard> cards = player.getAllCards();
        cards.remove(playedCard);
        refreshPanel();
    }
}
