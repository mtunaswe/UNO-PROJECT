package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * InfoPanel is a custom JPanel that displays game information such as error messages,
 * status messages, the number of remaining cards, and the current direction of play.
 */
public class InfoPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private String error;
    private String text;
    private String directionText;
    private int panelCenter;
    private int rest = 0;
	private int score;


    /**
     * Constructor for InfoPanel. Initializes the panel with default settings.
     */
    public InfoPanel() {
        setPreferredSize(new Dimension(275, 200));
        setOpaque(false);
        error = "";
        text = "Game Started";
        directionText = "Clockwise";
        
        updateText(text);
        updateDirection(directionText);

    }

    /**
     * Overrides the paintComponent method to custom paint the panel.
     * 
     * @param g the Graphics object used for painting
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        panelCenter = getWidth() / 2;

        printMessage(g);
        printCPUScore(g);
        printError(g);
        printRemaining(g);
        printDirection(g);
        
    }

    

	/**
     * Prints an error message in red at the top of the panel.
     * 
     * @param g the Graphics object used for painting
     */
    private void printError(Graphics g) {
        if (!error.isEmpty()) {
            Font adjustedFont = new Font("Cabin", Font.PLAIN, 25);
            FontMetrics fm = this.getFontMetrics(adjustedFont);
            int xPos = panelCenter - fm.stringWidth(error) / 2;
            g.setFont(adjustedFont);
            g.setColor(Color.red);
            g.drawString(error, xPos, 35);
            
            error = ""; 
        }
    }

    /**
     * Prints the main status message at the center of the panel.
     * 
     * @param g the Graphics object used for painting
     */
    private void printMessage(Graphics g) {
        Font adjustedFont = new Font("Cabin", Font.BOLD, 25);
        FontMetrics fm = this.getFontMetrics(adjustedFont);
        int xPos = panelCenter - fm.stringWidth(text) / 2;
        g.setFont(adjustedFont);
        g.setColor(new Color(0, 0, 0));
        g.drawString(text, xPos, 75);
    }
    
    /**
     * Prints the CPU score below the main message text.
     * 
     * @param g the Graphics object used for painting
     */
    private void printCPUScore(Graphics g) {
        Font adjustedFont = new Font("Cabin", Font.BOLD, 25);
        FontMetrics fm = this.getFontMetrics(adjustedFont);
        g.setColor(new Color(127, 127, 127));

        String cpuScoreText = "CPU Total Score: " + score;
        int xPos = panelCenter - fm.stringWidth(cpuScoreText) / 2;

        g.setFont(adjustedFont);
        g.drawString(cpuScoreText, xPos, 105); 
    }

    /**
     * Prints the detail about the remaining cards at the bottom of the panel.
     * 
     * @param g the Graphics object used for painting
     */
    private void printRemaining(Graphics g) {
    	Font adjustedFont = new Font("Calibri", Font.BOLD,	25);	
		FontMetrics fm = this.getFontMetrics(adjustedFont);
		g.setColor(new Color(127,127,127));
		
		//Determine the width of the word to position
	
		int xPos = panelCenter - fm.stringWidth(text) / 2;
		
		g.setFont(adjustedFont);
		
		text = "Remaining: " + rest;
		xPos = panelCenter - fm.stringWidth(text) / 2;
		g.drawString(text, xPos, 180);
		
		
	}

    /**
     * Prints the current direction of play in the middle of the panel.
     * 
     * @param g the Graphics object used for painting
     */
    private void printDirection(Graphics g) {
        Font adjustedFont = new Font("Cabin", Font.BOLD, 20);
        FontMetrics fm = this.getFontMetrics(adjustedFont);
        g.setColor(new Color(0, 0, 0));
        int xPos = panelCenter - fm.stringWidth(directionText) / 2;
        g.drawString(directionText, xPos, 140);
    }

    /**
     * Updates the main status message and repaints the panel.
     * 
     * @param newText the new status message
     */
    public void updateText(String newText) {
        text = newText;
      
    }

    /**
     * Updates the direction of play message and repaints the panel.
     * 
     * @param newDirection the new direction of play
     */
    public void updateDirection(String newDirection) {
        directionText = "Direction: " + newDirection;
       
    }

    /**
     * Sets an error message to be displayed and repaints the panel.
     * 
     * @param errorMgs the error message
     */
    public void setError(String errorMgs) {
        error = errorMgs;
        
    }

    /**
     * Sets the number of remaining cards to be displayed and repaints the panel.
     * 
     * @param remaining the number of remaining cards
     */
    public void setRemaining(int remaining) {
    	
        rest = remaining;
        
    }

	public void updateScore(int CPUscore) {
		
		score = CPUscore;
		
		
	}
}
