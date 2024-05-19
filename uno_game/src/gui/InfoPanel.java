package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import game_model.Game.Direction;

/**
 * InfoPanel is a custom JPanel that displays game information such as error messages,
 * status messages, the number of remaining cards, and the current direction of play.
 */
public class InfoPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private String error;
    private String text;
    private String sessionName;
    private int panelCenter;
    private int rest = 0;
	private int score;
	private Direction direction;
	
	private BufferedImage clockwiseArrow;
    private Image counterClockwiseArrow;


    /**
     * Constructor for InfoPanel. Initializes the panel with default settings.
     */
    public InfoPanel() {
        setPreferredSize(new Dimension(275, 200));
        setOpaque(false);
        
        error = "";
        text = "Game Started";
      
        sessionName = "";
        
        direction = Direction.Clockwise;
        
        
        loadImages();
        updateText(text);
        updateDirection(direction);

    }
    
    /**
     * Loads the images for the arrows indicating direction.
     */
    
   
    private void loadImages() {
        try {
            clockwiseArrow = ImageIO.read(getClass().getResource("/resources/arrow.png"));    
            counterClockwiseArrow = getFlippedImage(clockwiseArrow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Flips an image horizontally.
     * @param image The image to be flipped.
     * @return The flipped image.
     */
    

    private BufferedImage getFlippedImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage flippedImage = new BufferedImage(width, height, image.getType());
        Graphics2D g2d = flippedImage.createGraphics();
        g2d.drawImage(image, 0, 0, width, height, width, 0, 0, height, null);
        g2d.dispose();
        return flippedImage;
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
        printSessionName(g);
        paintDirection(g);
        
    }
    
    private void printSessionName(Graphics g) {
        if (!sessionName.isEmpty()) {
            Font adjustedFont = new Font("Cabin", Font.BOLD, 20);
            FontMetrics fm = this.getFontMetrics(adjustedFont);
            g.setColor(Color.BLUE);
            String sessionNameText = "Session: " + sessionName;
            int xPos = panelCenter - fm.stringWidth(sessionNameText) / 2;
            g.drawString(sessionNameText, xPos, 15);
        }
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
            g.setColor(Color.RED);
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
        g.setColor(Color.MAGENTA);
        
        String cpuScoreText;
        
        if(score != 0) {
        	cpuScoreText = "CPU Total Score: " + score;
        	
        }
        
        else {
        	cpuScoreText = "";
        }

        
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
		g.setColor(Color.ORANGE);
		
		//Determine the width of the word to position
	
		int xPos = panelCenter - fm.stringWidth(text) / 2;
		
		g.setFont(adjustedFont);
		
		text = "Remaining: " + rest;
		xPos = panelCenter - fm.stringWidth(text) / 2;
		g.drawString(text, xPos, 180);
		
		
	}
    
    /**
     * Prints the current direction of play in the middle of the panel.
     * @param g The Graphics object used for painting.
     */
    private void paintDirection(Graphics g) {
        Image arrow = (direction == Direction.Clockwise) ? clockwiseArrow : counterClockwiseArrow;
        if (arrow != null) {
            int arrowWidth = 50; // adjust as needed
            int arrowHeight = 50; // adjust as needed
            int xPos = panelCenter - arrowWidth / 2;
            int yPos = 110;
            g.drawImage(arrow, xPos, yPos, arrowWidth, arrowHeight, null);
        }
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
    public void updateDirection(Direction newDirection) {
    	direction = newDirection; 
        repaint();
       
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
	
	/**
     * Sets the session name to be displayed and repaints the panel.
     * 
     * @param sessionName the session name
     */
    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }
}
