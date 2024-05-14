package gui;

import javax.swing.*;
import java.io.*;

public class UserStats extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea textArea;

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
