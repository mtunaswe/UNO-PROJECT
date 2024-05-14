package gui;

import javax.swing.*;

import java.io.*;
import java.util.*;

public class LeaderBoard extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JList<String> list;

    public LeaderBoard() {
        setTitle("Leaderboard");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        list = new JList<>();
        add(new JScrollPane(list));
        loadLeaderboard();
    }

    private void loadLeaderboard() {
        File file = new File("src/user.txt");
        ArrayList<String> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                users.add(line);
            }
            users.sort((o1, o2) -> {
                String[] split1 = o1.split(", ");
                String[] split2 = o2.split(", ");
                return Integer.compare(Integer.parseInt(split2[4]), Integer.parseInt(split1[4])); // Sorting by totalScore
            });
            DefaultListModel<String> model = new DefaultListModel<>();
            
            int rank = 1;
            for (String user : users) {
                String[] details = user.split(", ");
                model.addElement(rank + ". " +details[0] + " - Score: " + details[4]);
                rank += 1;
            }
            list.setModel(model);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
