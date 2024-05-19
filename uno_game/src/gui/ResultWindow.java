package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ResultWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextArea logTextArea;
    private JButton saveLogButton;

    /**
     * Constructs a ResultWindow to display the game results.
     *
     * @param log The event log of the game session.
     * @param sessionName The name of the game session.
     * @param winnerName The name of the winner.
     * @param totalScore The total score of the winner.
     */
    public ResultWindow(String log, String sessionName, String winnerName, int totalScore) {
        setTitle("Game Results");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        logTextArea = new JTextArea(log);
        logTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logTextArea);

        saveLogButton = new JButton("Save Log");
        saveLogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveLogToFile(sessionName);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveLogButton);

        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.add(scrollPane, BorderLayout.CENTER);
        resultPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add game result information with condition
        JTextArea resultTextArea;
        if (totalScore == 0 && winnerName.contains("CPU")) {
            resultTextArea = new JTextArea("Winner: " + winnerName + "\nWinner is the CPU");
        } else {
            resultTextArea = new JTextArea("Winner: " + winnerName + "\nScore: " + totalScore);
        }
        resultTextArea.setEditable(false);
        resultPanel.add(resultTextArea, BorderLayout.NORTH);

        add(resultPanel);
        setVisible(true);
    }

    /**
     * Saves the log to a file in the "logs" directory under the project.
     * The file is automatically named using the session name and the current timestamp.
     */
    private void saveLogToFile(String sessionName) {
        // Ensure the "logs" directory exists
        File logDirectory = new File("logs");
        if (!logDirectory.exists()) {
            logDirectory.mkdir();
        }

        // Create the file name using the session name and current timestamp
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = sessionName + "_" + timestamp + ".txt";
        String filePath = new File(logDirectory, fileName).getPath();

        // Save the log to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            logTextArea.write(writer);
            JOptionPane.showMessageDialog(this, "Log saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving log: " + e.getMessage());
        }
    }
}
