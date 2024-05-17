package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import Interfaces.Constants;

/**
 * The ViewCard class represents a visual component for displaying a card in a card game.
 * It extends JPanel and includes functionality to display the front and back of the card,
 * with various card details such as color, type, and value.
 */
public class ViewCard extends JPanel implements Constants{

    private static final long serialVersionUID = 1L;

    public Color cardColor = null;
    protected String value = null;
    private int type = 0;
    private boolean showFront = true;

    private Border defaultBorder = BorderFactory.createEtchedBorder(WHEN_FOCUSED, Color.white, Color.gray);
    private Border focusedBorder = BorderFactory.createEtchedBorder(WHEN_FOCUSED, Color.black, Color.gray);

    private int WIDTH = 50;
    private int HEIGHT = 75;
    Dimension MEDIUM = new Dimension(WIDTH * 2, HEIGHT * 2);

    Dimension CARDSIZE = MEDIUM;

    int cardWidth = CARDSIZE.width;
    int cardHeight = CARDSIZE.height;

    private Image backImage;
    private Image skipImage;
    private Image reverseImage;

    public static int NUMBERS = 1;
    public static int ACTION = 2;
    public static int WILD = 3;
    
    protected static final Map<Color, String> colorMap = new HashMap<>();

    static {
        colorMap.put(RED, "Red");
        colorMap.put(BLUE, "Blue");
        colorMap.put(GREEN, "Green");
        colorMap.put(YELLOW, "Yellow");
    }


    /**
     * Constructor for creating a ViewCard for CPUPanel.
     * This constructor initializes the card with default settings and loads the card images.
     */
    public ViewCard() {
        this.setPreferredSize(CARDSIZE);
        this.setBorder(defaultBorder);
        this.addMouseListener(new MouseHandler());
        loadImages();
    }

    /**
     * Constructor for creating a ViewCard for PlayerPanel with specific color, type, and value.
     * 
     * @param cardColor The color of the card.
     * @param cardType  The type of the card (NUMBERS, ACTION, WILD).
     * @param cardValue The value of the card.
     */
    public ViewCard(Color cardColor, int cardType, String cardValue) {
        this.cardColor = cardColor;
        this.type = cardType;
        this.value = cardValue;

        this.setPreferredSize(CARDSIZE);
        this.setBorder(defaultBorder);

        this.addMouseListener(new MouseHandler());
        loadImages();
    }

    /**
     * Loads the images for the card's back, skip, and reverse icons.
     */
    private void loadImages() {
        try {
            backImage = ImageIO.read(getClass().getResource("/resources/uno_back_card.png"));
            skipImage = ImageIO.read(getClass().getResource("/resources/skipCard.png"));
            reverseImage = ImageIO.read(getClass().getResource("/resources/reverseCard.png"));
        } catch (IOException e) {
            System.err.println("Error loading images: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
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

    /**
     * Displays the front of the card with its details.
     * 
     * @param g The Graphics object for rendering.
     */
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
            Image imageToDraw = null;
            if (value.equals("Skip")) {
                imageToDraw = skipImage;
            } else if (value.equals("Reverse")) {
                imageToDraw = reverseImage;
            }

            if (imageToDraw != null) {
                // Draw the center image smaller
                int centerImgWidth = cardWidth / 2;
                int centerImgHeight = cardHeight / 2;
                g2.drawImage(imageToDraw, (cardWidth - centerImgWidth) / 2, (cardHeight - centerImgHeight) / 2, centerImgWidth, centerImgHeight, this);

                // Draw small icons in the corners
                int smallImgSize = 20;
                g2.drawImage(imageToDraw, margin, margin, smallImgSize, smallImgSize, this); // top-left
                g2.drawImage(imageToDraw, cardWidth - margin - smallImgSize, cardHeight - margin - smallImgSize, smallImgSize, smallImgSize, this); // bottom-right
            } else {
                // Value in the center
                Font defaultFont = new Font("Cabin", Font.BOLD, cardWidth / 2 + 5);
                FontMetrics fm = this.getFontMetrics(defaultFont);
                int stringWidth = fm.stringWidth(value) / 2;
                int fontHeight = defaultFont.getSize() * 1 / 3;

                g2.setColor(cardColor);
                g2.setFont(defaultFont);
                g2.drawString(value, cardWidth / 2 - stringWidth, cardHeight / 2 + fontHeight);

                // Value in the top-left corner
                defaultFont = new Font("Cabin", Font.ITALIC, cardWidth / 5);
                fm = this.getFontMetrics(defaultFont);
                stringWidth = fm.stringWidth(value) / 2;
                fontHeight = defaultFont.getSize() * 1 / 3;

                g2.setColor(Color.white);
                g2.setFont(defaultFont);
                g2.drawString(value, 2 * margin, 5 * margin);

                // Value in the bottom-right corner
                stringWidth = fm.stringWidth(value) / 2;
                fontHeight = defaultFont.getSize() * 1 / 3;

                g2.drawString(value, cardWidth - 2 * margin - fm.stringWidth(value), cardHeight - 2 * margin);
            }
        }
    }

    /**
     * Displays the back of the card.
     * 
     * @param g The Graphics object for rendering.
     */
    void displayBack(Graphics g) {
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

    /**
     * Toggles the visibility of the card's face (front or back).
     */
    public void toggleCardFace() {
        showFront = !showFront;
        repaint();
    }

    /**
     * Mouse listener for handling mouse events on the card.
     */
    class MouseHandler extends MouseAdapter {
        public void mouseEntered(MouseEvent e) {
            setBorder(focusedBorder);
        }

        public void mouseExited(MouseEvent e) {
            setBorder(defaultBorder);
        }
    }

    /**
     * Sets the size of the card.
     * 
     * @param newSize The new size for the card.
     */
    public void setCardSize(Dimension newSize) {
        this.setPreferredSize(newSize);
    }

    /**
     * Sets the color of the card.
     * 
     * @param newColor The new color for the card.
     */
    public void setColor(Color newColor) {
        this.cardColor = newColor;
    }

    /**
     * Gets the color of the card.
     * 
     * @return The color of the card.
     */
    public Color getColor() {
        return cardColor;
    }

    /**
     * Sets the value of the card.
     * 
     * @param newValue The new value for the card.
     */
    public void setCardValue(String newValue) {
        this.value = newValue;
    }

    /**
     * Gets the value of the card.
     * 
     * @return The value of the card.
     */
    public String getCardValue() {
        return value;
    }

    /**
     * Sets the type of the card.
     * 
     * @param newType The new type for the card.
     */
    public void setType(int newType) {
        this.type = newType;
    }

    /**
     * Gets the type of the card.
     * 
     * @return The type of the card.
     */
    public int getType() {
        return type;
    }
    
    /**
     * Gets the name of the card's color.
     * 
     * @return The name of the card's color.
     */
    public String getColorName() {
        return colorMap.getOrDefault(cardColor, "Wild Card");
    }
}
