package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private String error;
    private String text;
    private String directionText;
    private int panelCenter;
    private int rest = 0;

    public InfoPanel() {
        setPreferredSize(new Dimension(275, 200));
        setOpaque(false);
        error = "";
        text = "Game Started";
        directionText = "Direction: Clockwise";

        updateText(text);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        panelCenter = getWidth() / 2;

        printMessage(g);
        printError(g);
        printDetail(g);
        printDirection(g);
    }

    private void printError(Graphics g) {
        if (!error.isEmpty()) {
            Font adjustedFont = new Font("Cabin", Font.PLAIN, 25);
            FontMetrics fm = this.getFontMetrics(adjustedFont);
            int xPos = panelCenter - fm.stringWidth(error) / 2;
            g.setFont(adjustedFont);
            g.setColor(Color.red);
            g.drawString(error, xPos, 35);
            error = ""; // Clear the error after displaying
        }
    }

    private void printMessage(Graphics g) {
        Font adjustedFont = new Font("Cabin", Font.BOLD, 25);
        FontMetrics fm = this.getFontMetrics(adjustedFont);
        int xPos = panelCenter - fm.stringWidth(text) / 2;
        g.setFont(adjustedFont);
        g.setColor(new Color(0, 0, 0));
        g.drawString(text, xPos, 75);
    }

    private void printDetail(Graphics g) {
        Font adjustedFont = new Font("Cabin", Font.BOLD, 25);
        FontMetrics fm = this.getFontMetrics(adjustedFont);
        g.setColor(new Color(0, 0, 0));
        String detailText = "Remaining Cards: " + rest;
        int xPos = panelCenter - fm.stringWidth(detailText) / 2;
        g.drawString(detailText, xPos, 180);
    }

    private void printDirection(Graphics g) {
        Font adjustedFont = new Font("Cabin", Font.BOLD, 20);
        FontMetrics fm = this.getFontMetrics(adjustedFont);
        g.setColor(new Color(0, 0, 0));
        int xPos = panelCenter - fm.stringWidth(directionText) / 2;
        g.drawString(directionText, xPos, 140);
    }

    public void updateText(String newText) {
        text = newText;
        repaint();
    }

    public void updateDirection(String newDirection) {
        directionText = "Direction: " + newDirection;
        repaint();
    }

    public void setError(String errorMgs) {
        error = errorMgs;
        repaint();
    }

    public void setDetail(int remaining) {
        rest = remaining;
        repaint();
    }
}
