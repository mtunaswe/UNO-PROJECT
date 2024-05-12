package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Controller.Rules;

public class ButtonListener implements ActionListener{
	
	Rules rules;
	
	public void setServer(Rules rules){
		this.rules = rules;
	}
	
	public void drawCard() {
		if(rules.canPlay)
			rules.requestCard();	
	}
	
	public void sayUNO() {
		if(rules.canPlay)
			rules.submitSaidUNO();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
