package gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;


public class InfoPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String error;
	private String text;
	private int panelCenter;
	private String sessionName;
	
	private int rest = 0;
	
	public InfoPanel() {
        setPreferredSize(new Dimension(275, 200));
        setOpaque(false);
        error = "";
        text = "Game Started";
        sessionName = "Session 1";
        updateText(text);
    }
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		panelCenter = getWidth()/2;
		
		printMessage(g);
		printError(g);
		printDetail(g);
		printSessionName(g);
	}

	private void printError(Graphics g) {
		if(!error.isEmpty()){
			Font adjustedFont = new Font("Cabin", Font.PLAIN,	25);
			
			//Determine the width of the word to position
			FontMetrics fm = this.getFontMetrics(adjustedFont);
			int xPos = panelCenter - fm.stringWidth(error) / 2;
			
			g.setFont(adjustedFont);
			g.setColor(Color.red);
			g.drawString(error, xPos, 35);
			
			error = "";
		}
	}

	private void printMessage(Graphics g) {
		Font adjustedFont = new Font("Cabin", Font.BOLD,	25);	
		
		//Determine the width of the word to position
		FontMetrics fm = this.getFontMetrics(adjustedFont);
		int xPos = panelCenter - fm.stringWidth(text) / 2;
		
		g.setFont(adjustedFont);
		g.setColor(new Color(0,0,0));
		g.drawString(text, xPos, 75);		
	}
	
	private void printDetail(Graphics g){
		Font adjustedFont = new Font("Cabin", Font.BOLD,	25);	
		FontMetrics fm = this.getFontMetrics(adjustedFont);
		g.setColor(new Color(0,0,0));
		
		//Determine the width of the word to position
		
		text = "Remaining Cards: " + rest;
		int xPos = panelCenter - fm.stringWidth(text) / 2;
		g.drawString(text, xPos, 180);
	
		
		//Details
		adjustedFont = new Font("Cabin", Font.PLAIN,	20);
		g.setFont(adjustedFont);
		fm = this.getFontMetrics(adjustedFont);
		
		text = String.valueOf(rest);
		xPos = panelCenter - fm.stringWidth(text) / 2;
		g.drawString(text, xPos, 190);
	}
	
	private void printSessionName(Graphics g) {
        Font adjustedFont = new Font("Cabin", Font.BOLD, 25);
        FontMetrics fm = this.getFontMetrics(adjustedFont);
        g.setColor(new Color(0, 0, 0));
        int xPos = panelCenter - fm.stringWidth(sessionName) / 2;
        g.drawString(sessionName, xPos, 140);
    }

	public void updateText(String newText) {
		text = newText;
	}
	
	public void setError(String errorMgs){
		error = errorMgs;
	}
	
	public void setDetail(int[] playedCards, int remaining){
		rest = remaining;
	}
}
