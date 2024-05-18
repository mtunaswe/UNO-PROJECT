package Interfaces;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import Controller.ButtonListener;
import Controller.CardListener;
import gui.InfoPanel;

public interface Constants {
	int TOTAL_CARDS = 108;
	int FIRSTHAND = 7;

	
	Color RED = new Color(216, 46, 63);
	Color BLUE = new Color(53, 129, 216);
	Color GREEN = new Color(40, 204, 45);
	Color YELLOW = new Color(255, 225, 53);
	Color BLACK = Color.BLACK;
	
	Color[] UNO_COLORS = {RED, BLUE, GREEN, YELLOW};
	int[] UNO_NUMBERS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
	
	static final Map<Color, String> colorMap = new HashMap<>();

    
	 
	// ActionCard Functions
	String REVERSE = "Reverse";
	String SKIP = "Skip";
	String DRAW2 = "2+";

	// Wild card functions
	String W_NORMAL = "W";
	String W_DRAW4 = "4+";
	String[] ActionTypes = {REVERSE, SKIP, DRAW2};
	
	CardListener CARDLISTENER = new CardListener();
	ButtonListener BUTTONLISTENER = new ButtonListener();
	
	InfoPanel infoPanel = new InfoPanel();
}



