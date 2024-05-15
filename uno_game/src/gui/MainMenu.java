package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import game_model.UserInfo;
import game_model.UserSession;

import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

public class MainMenu {

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
				MainFrame mainframe = new MainFrame();
                mainframe.setVisible(true);
			}
		});
		btnNewButton.setBounds(130, 199, 143, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("SAVED GAMES");
		btnNewButton_1.setFont(new Font("Cabin", Font.PLAIN, 15));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnNewButton_1.setBounds(130, 229, 143, 23);
		frame.getContentPane().add(btnNewButton_1);
		
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
}
