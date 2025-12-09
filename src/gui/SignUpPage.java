package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import game_model.UserInfo;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

/**
 * The SignUpPage class provides the GUI for the user registration.
 */
public class SignUpPage {

    JFrame frame;
    private JTextField textField;
    private JPasswordField passwordField;
    private JPasswordField passwordField_1;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SignUpPage window = new SignUpPage();
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
    public SignUpPage() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 661, 431);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton btnGoBack = new JButton("GO BACK\r\n\r\n");
        btnGoBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        JLabel lblNewLabel_1_2_1 = new JLabel("PASSWORD");
        lblNewLabel_1_2_1.setToolTipText("");
        lblNewLabel_1_2_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_1_2_1.setForeground(new Color(255, 255, 255));
        lblNewLabel_1_2_1.setFont(new Font("Cabin", Font.BOLD, 16));
        lblNewLabel_1_2_1.setBounds(209, 269, 183, 23);
        frame.getContentPane().add(lblNewLabel_1_2_1);

        passwordField = new JPasswordField();
        passwordField.setBounds(304, 254, 238, 32);
        frame.getContentPane().add(passwordField);

        passwordField_1 = new JPasswordField();
        passwordField_1.setBounds(304, 207, 238, 32);
        frame.getContentPane().add(passwordField_1);

        btnGoBack.setFont(new Font("Cabin", Font.BOLD, 14));
        btnGoBack.setBounds(496, 351, 141, 32);
        frame.getContentPane().add(btnGoBack);

        JLabel lblNewLabel_2 = new JLabel("WELCOME TO UNO GAME!");
        lblNewLabel_2.setForeground(new Color(255, 255, 255));
        lblNewLabel_2.setFont(new Font("Cabin", Font.PLAIN, 30));
        lblNewLabel_2.setBounds(10, 28, 455, 72);
        frame.getContentPane().add(lblNewLabel_2);

        JLabel lblNewLabel_1 = new JLabel("USERNAME");
        lblNewLabel_1.setForeground(new Color(255, 255, 255));
        lblNewLabel_1.setFont(new Font("Cabin", Font.BOLD, 16));
        lblNewLabel_1.setBounds(209, 173, 108, 23);
        frame.getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("PASSWORD");
        lblNewLabel_1_1.setForeground(new Color(255, 255, 255));
        lblNewLabel_1_1.setFont(new Font("Cabin", Font.BOLD, 16));
        lblNewLabel_1_1.setBounds(209, 214, 95, 23);
        frame.getContentPane().add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_2 = new JLabel("CONFIRM");
        lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_1_2.setForeground(new Color(255, 255, 255));
        lblNewLabel_1_2.setFont(new Font("Cabin", Font.BOLD, 16));
        lblNewLabel_1_2.setBounds(209, 254, 183, 23);
        frame.getContentPane().add(lblNewLabel_1_2);

        textField = new JTextField();
        textField.setColumns(10);
        textField.setBounds(304, 164, 238, 32);
        frame.getContentPane().add(textField);

        JButton btnNewButton = new JButton("SIGN-UP\r\n");
        btnNewButton.setFont(new Font("Cabin", Font.BOLD, 14));
        btnNewButton.setBounds(337, 351, 141, 32);

        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = textField.getText();
                if (!isValidUsername(username)) {
                    JOptionPane.showMessageDialog(null, "Your username must contain only letters and numbers.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String password = new String(passwordField.getPassword());
                if (!isValidPassword(password)) {
                    JOptionPane.showMessageDialog(null, "Your password must be at least 8 characters and contain at least one letter, one number, and one special character.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String cPassword = new String(passwordField_1.getPassword());
                if (!isValidConfirmation(cPassword, password)) {
                    JOptionPane.showMessageDialog(null, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int wins = 0;
                int losses = 0;
                int totalScore = 0;
                int totalGames = 0;

                if (isUniqueUser(username)) {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/user.txt", true))) {
                        writer.write(username + ", " + password + ", " + wins + ", " + losses + ", " + totalScore + ", " + totalGames + "\n");
                        JOptionPane.showMessageDialog(frame, "Registration successful! User added.", "Information", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException a) {
                        a.printStackTrace();
                    }
                }
            }
        });
        frame.getContentPane().add(btnNewButton);

        JToggleButton tglbtnNewToggleButton = new JToggleButton("Show/ Hide");
        tglbtnNewToggleButton.setHorizontalAlignment(SwingConstants.LEADING);
        tglbtnNewToggleButton.setFont(new Font("Cabin", Font.BOLD, 12));
        tglbtnNewToggleButton.setBounds(552, 211, 107, 23);

        tglbtnNewToggleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tglbtnNewToggleButton.isSelected()) {
                    passwordField_1.setEchoChar((char) 0);
                } else {
                    passwordField_1.setEchoChar('*');
                }
            }
        });
        frame.getContentPane().add(tglbtnNewToggleButton);

        JToggleButton tglbtnNewToggleButton_1 = new JToggleButton("Show/ Hide");
        tglbtnNewToggleButton_1.setHorizontalAlignment(SwingConstants.LEADING);
        tglbtnNewToggleButton_1.setFont(new Font("Cabin", Font.BOLD, 12));
        tglbtnNewToggleButton_1.setBounds(551, 258, 108, 23);

        tglbtnNewToggleButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tglbtnNewToggleButton_1.isSelected()) {
                    passwordField.setEchoChar((char) 0);
                } else {
                    passwordField.setEchoChar('*');
                }
            }
        });
        frame.getContentPane().add(tglbtnNewToggleButton_1);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("resources/background2.jpg"));
        lblNewLabel.setBounds(0, 0, 647, 394);
        frame.getContentPane().add(lblNewLabel);
    }

    /**
     * Validates the username to ensure it only contains letters and numbers.
     *
     * @param username The username to validate.
     * @return true if the username is valid, false otherwise.
     */
    private boolean isValidUsername(String username) {
        String usernameRegex = "^[a-zA-Z0-9]+$";
        Pattern pattern = Pattern.compile(usernameRegex);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    /**
     * Validates the password to ensure it meets complexity requirements.
     *
     * @param password The password to validate.
     * @return true if the password is valid, false otherwise.
     */
    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    /**
     * Validates if the confirmation password matches the original password.
     *
     * @param cpassword The confirmation password.
     * @param password  The original password.
     * @return true if the passwords match, false otherwise.
     */
    private boolean isValidConfirmation(String cpassword, String password) {
        return cpassword.equals(password);
    }

    /**
     * Checks if the username is unique by reading existing usernames from the user.txt file.
     *
     * @param username The username to check.
     * @return true if the username is unique, false otherwise.
     */
    private boolean isUniqueUser(String username) {
        File file = new File("src/user.txt");

        if (!file.exists()) {
            return true;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userExist = line.split(",");
                if (userExist[0].trim().equals(username)) {
                    JOptionPane.showMessageDialog(null, "This user already exists. Please change your username", "Warning", JOptionPane.WARNING_MESSAGE);
                    return false;
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all users from the user.txt file.
     *
     * @return A list of UserInfo objects representing all users.
     */
    public ArrayList<UserInfo> getAllUsers() {
        ArrayList<UserInfo> userList = new ArrayList<>();

        File file = new File("src/user.txt");

        if (!file.exists()) {
            return userList;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lst = line.split(",");
                if (lst.length == 6) {
                    String username = lst[0].trim();
                    String password = lst[1].trim();
                    int wins = Integer.parseInt(lst[2].trim());
                    int losses = Integer.parseInt(lst[3].trim());
                    int totalScore = Integer.parseInt(lst[4].trim());
                    int totalGames = Integer.parseInt(lst[5].trim());

                    UserInfo user = new UserInfo(username, password, wins, losses, totalScore, totalGames);
                    userList.add(user);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return userList;
    }
}
