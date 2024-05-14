package gui;

import game_model.UserInfo;

import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.*;

public class LeaderBoard_stats extends JFrame {
    private static final long serialVersionUID = 1L;
    private JList<UserInfo> list;

    public LeaderBoard_stats() {
        setTitle("Leaderboard");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        list = new JList<>();
        list.setCellRenderer(new UserCellRenderer());
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = list.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        UserInfo selectedUser = list.getModel().getElementAt(index);
                        new UserStatsDialog(LeaderBoard_stats.this, selectedUser).setVisible(true);
                    }
                }
            }
        });
        
        
        add(new JScrollPane(list));
        loadLeaderboard();
    }

    private void loadLeaderboard() {
        File file = new File("src/user.txt");
        ArrayList<UserInfo> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(", ");
                if (details.length == 6) {
                    String nickname = details[0];
                    String password = details[1];
                    int wins = Integer.parseInt(details[2]);
                    int losses = Integer.parseInt(details[3]);
                    int totalScore = Integer.parseInt(details[4]);
                    int totalGames = Integer.parseInt(details[5]);
                    users.add(new UserInfo(nickname, password, wins, losses, totalScore, totalGames));
                }
            }
            users.sort(Comparator.comparingInt(UserInfo::getTotalScore).reversed());
            DefaultListModel<UserInfo> model = new DefaultListModel<>();
            int rank = 1;
            for (UserInfo user : users) {
            	user.setRank(rank);
                model.addElement(user);
                rank++;
            }
            list.setModel(model);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}

