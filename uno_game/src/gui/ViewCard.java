package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import java.awt.Image;

public class ViewCard extends JPanel {

    private static final long serialVersionUID = 1L;

    public Color cardColor = null;
    protected String value = null;
    private int type = 0;
    private boolean showFront = true;

    private Border defaultBorder = BorderFactory.createEtchedBorder(WHEN_FOCUSED, Color.white, Color.gray);
    private Border focusedBorder = BorderFactory.createEtchedBorder(WHEN_FOCUSED, Color.black, Color.gray);

    private int WIDTH = 50;
    private int HEIGHT = 75;
    Dimension SMALL = new Dimension(WIDTH, HEIGHT);
    Dimension MEDIUM = new Dimension(WIDTH * 2, HEIGHT * 2);
    Dimension BIG = new Dimension(WIDTH * 3, HEIGHT * 3);

    Dimension CARDSIZE = MEDIUM;

    int cardWidth = CARDSIZE.width;
    int cardHeight = CARDSIZE.height;

    private Image backImage;

    public static int NUMBERS = 1;
    public static int ACTION = 2;
    public static int WILD = 3;

    // Constructor for CPUPanel
    public ViewCard() {
        this.setPreferredSize(CARDSIZE);
        this.setBorder(defaultBorder);
        this.addMouseListener(new MouseHandler());
        try {
            backImage = ImageIO.read(new File("C:\\Users\\merta\\git\\UNO-PROJECT\\uno_game\\src\\resources\\uno_back_card.png")); // Update with actual image path
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Constructor for PlayerPanel
    public ViewCard(Color cardColor, int cardType, String cardValue) {
        this.cardColor = cardColor;
        this.type = cardType;
        this.value = cardValue;

        this.setPreferredSize(CARDSIZE);
        this.setBorder(defaultBorder);

        this.addMouseListener(new MouseHandler());
        try {
            backImage = ImageIO.read(new File("C:\\Users\\merta\\git\\UNO-PROJECT\\uno_game\\src\\resources\\uno_back_card.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (showFront) {
            displayFront(g);
        } else {
            displayBack(g);
        }
    }

    private void displayFront(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, cardWidth, cardHeight);

        int margin = 5;
        g2.setColor(cardColor);
        g2.fillRect(margin, margin, cardWidth - 2 * margin, cardHeight - 2 * margin);

        g2.setColor(Color.WHITE);
        AffineTransform org = g2.getTransform();
        g2.rotate(45, cardWidth * 3 / 4, cardHeight * 3 / 4);

        g2.fillOval(0, cardHeight * 1 / 4, cardWidth * 3 / 5, cardHeight);
        g2.setTransform(org);

        if (value != null) {
            // Value in the center
            Font defaultFont = new Font("Cabin", Font.BOLD, cardWidth / 2 + 5);
            FontMetrics fm = this.getFontMetrics(defaultFont);
            int StringWidth = fm.stringWidth(value) / 2;
            int FontHeight = defaultFont.getSize() * 1 / 3;

            g2.setColor(cardColor);
            g2.setFont(defaultFont);
            g2.drawString(value, cardWidth / 2 - StringWidth, cardHeight / 2 + FontHeight);

            // Value in the top-left corner
            defaultFont = new Font("Cabin", Font.ITALIC, cardWidth / 5);
            fm = this.getFontMetrics(defaultFont);
            StringWidth = fm.stringWidth(value) / 2;
            FontHeight = defaultFont.getSize() * 1 / 3;

            g2.setColor(Color.white);
            g2.setFont(defaultFont);
            g2.drawString(value, 2 * margin, 5 * margin);

            // Value in the bottom-right corner
            StringWidth = fm.stringWidth(value) / 2;
            FontHeight = defaultFont.getSize() * 1 / 3;

            g2.drawString(value, cardWidth - 2 * margin - fm.stringWidth(value), cardHeight - 2 * margin);
        }

    }

    private void displayBack(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        if (backImage != null) {
            g2.drawImage(backImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g2.setColor(Color.GRAY);
            g2.fillRect(0, 0, cardWidth, cardHeight);

            g2.setColor(Color.BLACK);
            Font largeFont = new Font("Cabin", Font.BOLD, 36);
            g2.setFont(largeFont);

            String unoText = "UNO";
            FontMetrics fm = g2.getFontMetrics(largeFont);
            int textWidth = fm.stringWidth(unoText);
            int textHeight = fm.getAscent();

            AffineTransform originalTransform = g2.getTransform();
            g2.rotate(Math.toRadians(-45), cardWidth / 2, cardHeight / 2);
            g2.drawString(unoText, (cardWidth - textWidth) / 2, (cardHeight + textHeight) / 2);
            g2.setTransform(originalTransform);
        }
    }

    public void toggleCardFace() {
        showFront = !showFront;
        repaint();
    }

    /**
     * Mouse Listener
     */
    class MouseHandler extends MouseAdapter {

        public void mouseEntered(MouseEvent e) {
            setBorder(focusedBorder);
        }

        public void mouseExited(MouseEvent e) {
            setBorder(defaultBorder);
        }
    }

    public void setCardSize(Dimension newSize) {
        this.setPreferredSize(newSize);
    }

    public void setColor(Color newColor) {
        this.cardColor = newColor;
    }

    public Color getColor() {
        return cardColor;
    }

    public void setCardValue(String newValue) {
        this.value = newValue;
    }

    public String getCardValue() {
        return value;
    }

    public void setType(int newType) {
        this.type = newType;
    }

    public int getType() {
        return type;
    }
}
