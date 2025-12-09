package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The ButtonListener class handles button click events and performs
 * actions such as drawing a card or saying UNO in the game.
 */
public class ButtonListener implements ActionListener {
	
	public static Rules rules;
	
	/**
	 * Sets the rules server for this listener.
	 *
	 * @param rules the Rules object to be set as the server
	 */
	public void setServer(Rules rules) {
		ButtonListener.rules = rules;
	}
	
	/**
	 * Requests to draw a card if the rules allow it.
	 */
	public void drawCard() {
		if (rules.canPlay) {
			rules.requestCard();
		}
	}
	
	/**
	 * Submits that the player has said UNO if the rules allow it.
	 */
	public void sayUNO() {
		if (rules.canPlay) {
			rules.submitSaidUNO();
		}
	}

	/**
	 * Invoked when an action occurs.
	 * This method is currently not implemented.
	 * It is generated to avoid some errors.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}
}

