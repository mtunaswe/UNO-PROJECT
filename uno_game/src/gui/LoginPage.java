package gui;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

import game_model.UserInfo;
import game_model.UserSession;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 * LoginPage is a JFrame that provides the login interface for users.
 * It validates user credentials, and on successful login, navigates to the main menu.
 */
public class LoginPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField textField;
    private JPasswordField passwordField;
    
    JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginPage frame = new LoginPage();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the login page frame.
     */
    public LoginPage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 661, 431);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        JLabel lblNewLabel_4 = new JLabel("");
        lblNewLabel_4.setIcon(new ImageIcon(getClass().getResource("/resources/logo.png")));
        lblNewLabel_4.setBounds(202, 25, 177, 156);
        getContentPane().add(lblNewLabel_4);

        textField = new JTextField();
        textField.setBounds(162, 183, 238, 41);
        getContentPane().add(textField);
        textField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBounds(162, 235, 238, 39);
        getContentPane().add(passwordField);

        JLabel lblNewLabel = new JLabel("USERNAME");
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("Cabin", Font.BOLD, 16));
        lblNewLabel.setBounds(51, 190, 108, 23);
        getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("PASSWORD");
        lblNewLabel_1.setForeground(new Color(255, 255, 255));
        lblNewLabel_1.setFont(new Font("Cabin", Font.BOLD, 16));
        lblNewLabel_1.setBounds(51, 241, 95, 23);
        getContentPane().add(lblNewLabel_1);

        JButton btnNewButton = new JButton("LOGIN");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                File file = new File("src/user.txt");
                String username = textField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);
                
                if (userValidation(username, password)) {
                    goToMainMenu();
                    
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            String[] details = line.split(", ");
                            if (details[0].equals(username)) {
                                int wins = Integer.parseInt(details[2]);
                                int losses = Integer.parseInt(details[3]);
                                int totalScore = Integer.parseInt(details[4]);
                                int totalGames = Integer.parseInt(details[5]);
                                UserInfo loggedInUser = new UserInfo(username, password, wins, losses, totalScore, totalGames);
                                UserSession.setCurrentUser(loggedInUser);
                            }
                        }
                    } catch (IOException e1) {
                        System.err.println("Error reading the user data file.");
                        e1.printStackTrace();
                    } catch (NumberFormatException e1) {
                        System.err.println("Error parsing integer from user data.");
                        e1.printStackTrace();
                    }
                }
            }
        });    

        btnNewButton.setFont(new Font("Cabin", Font.BOLD, 14));
        btnNewButton.setBounds(216, 289, 141, 32);
        getContentPane().add(btnNewButton);

        JToggleButton tglbtnNewToggleButton = new JToggleButton("Show/ Hide");
        tglbtnNewToggleButton.setFont(new Font("Cabin", Font.BOLD, 12));
        tglbtnNewToggleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tglbtnNewToggleButton.isSelected()) {
                    passwordField.setEchoChar((char) 0);
                } else {
                    passwordField.setEchoChar('*');
                }
            }
        });
        tglbtnNewToggleButton.setBounds(410, 242, 106, 23);
        getContentPane().add(tglbtnNewToggleButton);

        JButton btnRegister = new JButton("REGISTER");
        btnRegister.setFont(new Font("Cabin", Font.BOLD, 14));
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SignUpPage signUpPage = new SignUpPage();
                signUpPage.frame.setVisible(true);
                signUpPage.frame.setResizable(false);
                signUpPage.frame.setLocation(500, 150);
            }
        });

        btnRegister.setBounds(216, 332, 141, 32);
        getContentPane().add(btnRegister);

        JLabel lblNewLabel_3 = new JLabel("");
        lblNewLabel_3.setIcon(new ImageIcon(getClass().getResource("/resources/background.jpg")));
        lblNewLabel_3.setBounds(0, 0, 661, 431);
        getContentPane().add(lblNewLabel_3);
    }

    /**
     * Validates the username and password against the stored user data.
     * @param username The username to validate.
     * @param password The password to validate.
     * @return true if the username and password are valid, false otherwise.
     */
    private boolean userValidation(String username, String password) {
        File file = new File("src/user.txt");

        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "There is no account in the database. Please click sign up!", "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lst = line.split(",");
                if (lst[0].trim().equals(username) && lst[1].trim().equals(password)) {
                    JOptionPane.showMessageDialog(LoginPage.this, "Login is successful!");
                    return true;
                }
            }
            JOptionPane.showMessageDialog(this, "Wrong username or password. Please try again!", "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Navigates to the main menu.
     */
    private void goToMainMenu() {
        MainMenu mainMenu = new MainMenu();
        mainMenu.frame.setVisible(true);
        mainMenu.frame.setResizable(false);
        mainMenu.frame.setLocation(500, 150);
        dispose();
    }
}
