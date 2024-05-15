package gui;

import javax.swing.*;
import java.io.*;

/**
 * The UserStats class represents a window that displays statistics for a specific user.
 * It extends JFrame to provide a window for the user interface.
 */
public class UserStats extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextArea textArea;

    /**
     * Constructs a UserStats window for a specified user.
     *
     * @param username The username of the user whose statistics are to be displayed.
     */
    public UserStats(String username) {
        setTitle("User Stats");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        textArea = new JTextArea(10, 30);
        textArea.setEditable(false);
        add(new JScrollPane(textArea));
        loadUserStats(username);
    }

    /**
     * Loads the statistics for the specified user from a file and displays them in the text area.
     *
     * @param username The username of the user whose statistics are to be loaded.
     */
    private void loadUserStats(String username) {
        File file = new File("src/user.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(", ");
                if (details[0].equals(username)) {
                    textArea.setText("Username: " + details[0] + "\nWins: " + details[2] +
                            "\nLosses: " + details[3] + "\nTotal Score: " + details[4] +
                            "\nTotal Games: " + details[5]);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
