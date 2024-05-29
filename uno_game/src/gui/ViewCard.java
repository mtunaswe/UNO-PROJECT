package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import Interfaces.Constants;

public class ViewCard extends JPanel implements Constants {

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
        colorMap.put(BLACK, "Wild Card");
    }

    public ViewCard(String cardString) {
        String[] parts = cardString.split(",");
        this.cardColor = getKeyByValue(colorMap, parts[0]);
        this.type = Integer.parseInt(parts[1]);
        this.value = parts[2];
        
        this.setPreferredSize(CARDSIZE);
        this.setBorder(defaultBorder);

        this.addMouseListener(new MouseHandler());
        loadImages();
    }
    
    public static <K, V> K getKeyByValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    public ViewCard() {
        this.setPreferredSize(CARDSIZE);
        this.setBorder(defaultBorder);
        this.addMouseListener(new MouseHandler());
        loadImages();
    }

    public ViewCard(Color cardColor, int cardType, String cardValue) {
        this.cardColor = cardColor;
        this.type = cardType;
        this.value = cardValue;

        this.setPreferredSize(CARDSIZE);
        this.setBorder(defaultBorder);

        this.addMouseListener(new MouseHandler());
        loadImages();
    }

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

    private void displayFront(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color gradientStart = cardColor.brighter();
        Color gradientEnd = cardColor.darker();
        LinearGradientPaint gradient = new LinearGradientPaint(0, 0, 0, cardHeight, new float[]{0f, 1f}, new Color[]{gradientStart, gradientEnd});
        g2.setPaint(gradient);

        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, cardWidth, cardHeight, 20, 20);
        g2.fill(roundedRectangle);

 
        g2.setColor(Color.WHITE);
        AffineTransform org = g2.getTransform();
        g2.rotate(Math.toRadians(45), cardWidth * 3 / 4, cardHeight * 3 / 4);
        g2.fillOval(0, cardHeight * 1 / 4, cardWidth * 3 / 5, cardHeight);
        g2.setTransform(org);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        g2.draw(roundedRectangle);

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
                g2.drawImage(imageToDraw, 5, 5, smallImgSize, smallImgSize, this); // top-left
                g2.drawImage(imageToDraw, cardWidth - 5 - smallImgSize, cardHeight - 5 - smallImgSize, smallImgSize, smallImgSize, this); // bottom-right
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

                g2.setColor(Color.WHITE);
                g2.setFont(defaultFont);
                g2.drawString(value, 2 * 5, 5 * 5);

                // Value in the bottom-right corner
                g2.drawString(value, cardWidth - 2 * 5 - fm.stringWidth(value), cardHeight - 2 * 5);
            }
        }
    }

    void displayBack(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (backImage != null) {
            g2.drawImage(backImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g2.setColor(Color.GRAY);
            RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, cardWidth, cardHeight, 20, 20);
            g2.fill(roundedRectangle);

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

    public String getColorName() {
        return colorMap.getOrDefault(cardColor, "Wild Card");
    }
}
