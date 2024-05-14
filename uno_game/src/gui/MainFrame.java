package gui;

import javax.swing.JFrame;
import Controller.Rules;
import Controller.ButtonListener;
import Controller.CardListener;


@SuppressWarnings("serial")
public class MainFrame extends JFrame{
	
	private Session mainPanel;
	private Rules server;
	CardListener CARDLISTENER = new CardListener();
    ButtonListener BUTTONLISTENER = new ButtonListener();
	
	public MainFrame(){	
		server = new Rules();
		CARDLISTENER.setServer(server);
		BUTTONLISTENER.setServer(server);
		
		mainPanel = server.getSession();
		add(mainPanel);
	}
}



