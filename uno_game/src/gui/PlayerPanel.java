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

/**
 * The PlayerPanel class represents the panel for the human player, 
 * displaying the player's cards and control buttons (Draw and Say UNO).
 */
public class PlayerPanel extends JPanel {
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
    
    private ButtonListener BUTTONLISTENER = new ButtonListener();

    /**
     * Constructs a PlayerPanel for the given player.
     * 
     * @param newPlayer The player to be displayed in this panel.
     */
    public PlayerPanel(Player newPlayer) {
        setPlayer(newPlayer);
        setBackground(new Color(216, 191, 168)); // Set background color to match the table

        layout = Box.createVerticalBox();
        cardHolder = new JLayeredPane();
        cardHolder.setPreferredSize(new Dimension(600, 175));
        cardHolder.setBackground(new Color(216, 191, 168)); // Set background color

        // Set cards and control panel
        setCards();
        setControlPanel();

        layout.add(cardHolder);
        layout.add(Box.createVerticalStrut(10)); // Add some spacing between card holder and control panel
        layout.add(controlPanel);
        add(layout);

        // Register Listeners
        handler = new ButtonHandler();
        draw.addActionListener(BUTTONLISTENER);
        draw.addActionListener(handler);
        
        sayUNO.addActionListener(BUTTONLISTENER);
        sayUNO.addActionListener(handler);
    }

    /**
     * Sets the cards for the player.
     */
    public void setCards() {
        cardHolder.removeAll();

        // Origin point at the center
        Point origin = getPoint(cardHolder.getWidth(), player.getTotalCards());
        int offset = calculateOffset(cardHolder.getWidth(), player.getTotalCards());

        int i = 0;
        for (ViewCard card : player.getAllCards()) {
            card.setBounds(origin.x, origin.y, card.CARDSIZE.width, card.CARDSIZE.height);
            cardHolder.add(card, i++);
            cardHolder.moveToFront(card);
            origin.x += offset;
        }
        repaint();
    }

    /**
     * Gets the player associated with this panel.
     * 
     * @return The player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the player for this panel and updates the player name.
     * 
     * @param player The player to be set.
     */
    public void setPlayer(Player player) {
        this.player = player;
        setPlayerName(player.getName());
    }

    /**
     * Sets the player's name.
     * 
     * @param playername The player's name.
     */
    public void setPlayerName(String playername) {
        this.name = playername;
    }

    /**
     * Sets up the control panel with buttons and labels.
     */
    private void setControlPanel() {
        draw = new JButton("Draw");
        draw.setBackground(new Color(160, 82, 45));
        draw.setFont(new Font("Cabin", Font.BOLD, 20));
        draw.setFocusable(false);
        
        sayUNO = new JButton("Say UNO");
        sayUNO.setBackground(new Color(210, 180, 140));
        sayUNO.setFont(new Font("Cabin", Font.BOLD, 20));
        sayUNO.setFocusable(false);
        
        nameLbl = new JLabel(name);
        nameLbl.setForeground(Color.BLACK);
        nameLbl.setFont(new Font("Cabin", Font.BOLD, 15));

        controlPanel = Box.createHorizontalBox();
        controlPanel.setBackground(new Color(205, 133, 63)); // Set background color
        controlPanel.add(nameLbl);
        controlPanel.add(Box.createHorizontalStrut(10)); // Add some spacing between the name label and buttons
        controlPanel.add(draw);
        controlPanel.add(Box.createHorizontalStrut(15));
        controlPanel.add(sayUNO);
    }

    /**
     * Calculates the offset for the cards based on the panel width and total cards.
     * 
     * @param width The width of the panel.
     * @param totalCards The total number of cards.
     * @return The offset for the cards.
     */
    private int calculateOffset(int width, int totalCards) {
        int offset = 71;
        if (totalCards <= 7) {
            return offset;
        } else {
            double o = (width - 100) / (totalCards - 1);
            return (int) o;
        }
    }

    /**
     * Gets the starting point for the cards based on the panel width and total cards.
     * 
     * @param width The width of the panel.
     * @param totalCards The total number of cards.
     * @return The starting point for the cards.
     */
    private Point getPoint(int width, int totalCards) {
        Point p = new Point(0, 20);
        if (totalCards >= 7) {
            return p;
        } else {
            p.x = (width - calculateOffset(width, totalCards) * totalCards) / 2;
            return p;
        }
    }

    /**
     * Handles button actions for drawing a card and saying UNO.
     */
    class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (player.isMyTurn()) {
                if (e.getSource() == draw) {
                    BUTTONLISTENER.drawCard();
                } else if (e.getSource() == sayUNO) {
                    BUTTONLISTENER.sayUNO();
                }
            }
        }
    }
}
