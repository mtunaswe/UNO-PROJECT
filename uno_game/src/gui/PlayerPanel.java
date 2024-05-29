package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicButtonUI;
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
        setBackground(new Color(216, 191, 168)); 

        layout = Box.createVerticalBox();
        cardHolder = new JLayeredPane();
        cardHolder.setPreferredSize(new Dimension(600, 175));
        cardHolder.setBackground(new Color(216, 191, 168)); 

        // Set cards and control panel
        setCards();
        setControlPanel();

        layout.add(cardHolder);
        layout.add(Box.createVerticalStrut(10)); 
        layout.add(controlPanel);
        add(layout);

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
        
        Dimension size = cardHolder.getPreferredSize();
        cardHolder.setSize(size);
        cardHolder.setPreferredSize(size);

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
        draw.setUI(new ModernButtonUI());
        draw.setBackground(new Color(255, 87, 34));
        draw.setFont(new Font("Cabin", Font.BOLD, 20));
        draw.setForeground(Color.WHITE);
        draw.setFocusable(false);
        draw.setPreferredSize(new Dimension(120, 50));
        
        sayUNO = new JButton("Say UNO");
        sayUNO.setUI(new ModernButtonUI());
        sayUNO.setBackground(new Color(76, 175, 80));
        sayUNO.setFont(new Font("Cabin", Font.BOLD, 20));
        sayUNO.setForeground(Color.WHITE);
        sayUNO.setFocusable(false);
        sayUNO.setPreferredSize(new Dimension(120, 50));
        
        nameLbl = new JLabel(name);
        nameLbl.setForeground(Color.BLACK);
        nameLbl.setFont(new Font("Cabin", Font.BOLD, 15));

        controlPanel = Box.createHorizontalBox();
        controlPanel.add(nameLbl);
        controlPanel.add(Box.createHorizontalStrut(10));
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

    /**
     * Custom button UI for modern styling.
     */
    private static class ModernButtonUI extends BasicButtonUI {
        @Override
        public void paint(Graphics g, JComponent c) {
            AbstractButton b = (AbstractButton) c;
            paintBackground(g, b, b.getModel().isPressed() ? 2 : 0);
            super.paint(g, c);
        }

        private void paintBackground(Graphics g, JComponent c, int yOffset) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = c.getWidth();
            int height = c.getHeight();
            Color color = c.getBackground();

            g2.setColor(color);
            g2.fillRoundRect(0, yOffset, width, height - yOffset, 20, 20);

            g2.setColor(color.darker());
            g2.drawRoundRect(0, yOffset, width, height - yOffset, 20, 20);

            g2.dispose();
        }
    }
}
