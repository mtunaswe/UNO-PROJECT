package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import Controller.GameController;
import Controller.LoadGameController;

import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

import game_model.UserInfo;
import game_model.UserSession;



public class MainMenu {
	static GameController gameController;

	
	JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu window = new MainMenu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 128, 192));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("QUICK PLAY");
		btnNewButton.setFont(new Font("Cabin", Font.PLAIN, 15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameController = new GameController();
				gameController.setupGame();
				
				MainFrame mainframe = new MainFrame(gameController.getGame());
                mainframe.setVisible(true);
                
			}
		});
		btnNewButton.setBounds(130, 199, 143, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnSavedGames = new JButton("SAVED GAMES");
		btnSavedGames.setFont(new Font("Cabin", Font.PLAIN, 15));
		btnSavedGames.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String username = "mert"; 
		        JFileChooser fileChooser = new JFileChooser("saved_games");

		        // Custom file filter
		        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
		            @Override
		            public boolean accept(File file) {
		                if (file.isDirectory()) {
		                    return true;
		                }
		                String filename = file.getName();
		                return filename.startsWith(username) && filename.endsWith(".txt");
		            }

		            @Override
		            public String getDescription() {
		                return "Saved games for " + username;
		            }
		        });

		        int option = fileChooser.showOpenDialog(frame);
		        if (option == JFileChooser.APPROVE_OPTION) {
		            String filePath = fileChooser.getSelectedFile().getPath();
		            File selectedFile = fileChooser.getSelectedFile();

		            // Additional check to ensure the file is allowed
		            if (selectedFile.getName().startsWith(username) && selectedFile.getName().endsWith(".txt")) {
		                LoadGameController loadController = new LoadGameController();
		                loadController.loadGame(filePath);

		                MainFrame mainframe = new MainFrame(loadController.getGame());
		                mainframe.setVisible(true);
		            } else {
		                JOptionPane.showMessageDialog(frame, "You are not allowed to open this file.", "Error", JOptionPane.ERROR_MESSAGE);
		            }
		        }
		    }
		});
		btnSavedGames.setBounds(130, 229, 143, 23);
		frame.getContentPane().add(btnSavedGames);

        

		JButton btnNewButton_2 = new JButton("EXIT ");
		btnNewButton_2.setFont(new Font("Cabin", Font.PLAIN, 15));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
			
		btnNewButton_2.setBounds(337, 229, 89, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(getClass().getResource("/resources/logo.png")));
		lblNewLabel.setBounds(107, 11, 184, 177);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton_3 = new JButton("LEADERBOARD");
		btnNewButton_3.setFont(new Font("Cabin", Font.BOLD, 15));
		btnNewButton_3.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        LeaderBoardWithStats leaderboard = new LeaderBoardWithStats();
		        leaderboard.setVisible(true);
		    }
		});
		btnNewButton_3.setBounds(283, 11, 143, 23);
		frame.getContentPane().add(btnNewButton_3);

		
		JButton btnNewButton_3_1 = new JButton("STATS");
		btnNewButton_3_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserInfo currentUser = UserSession.getCurrentUser();
				if (currentUser != null) {
                    UserStats userStats = new UserStats(currentUser.getNickname());
                    userStats.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(frame, "No user is currently logged in.", "Error", JOptionPane.ERROR_MESSAGE);
                }
			}
		});
		btnNewButton_3_1.setFont(new Font("Cabin", Font.BOLD, 15));
		btnNewButton_3_1.setBounds(337, 199, 89, 23);
		frame.getContentPane().add(btnNewButton_3_1);
	}
	
	public static GameController getGameController() {
		return gameController;
	}

	@SuppressWarnings("static-access")
	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}
		


}



