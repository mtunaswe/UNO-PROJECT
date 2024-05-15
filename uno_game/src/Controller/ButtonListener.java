package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ButtonListener implements ActionListener{
	
	public static Rules rules;
	
	public void setServer(Rules rules){
		ButtonListener.rules = rules;
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
