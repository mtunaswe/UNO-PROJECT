package gui;

import game_model.UserInfo;

import javax.swing.*;
import java.awt.*;

public class UserStatsDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    public UserStatsDialog(JFrame parent, UserInfo user) {
        super(parent, "User Stats", true);
        setSize(300, 200);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(6, 1));

        add(new JLabel("Nickname: " + user.getNickname()));
        add(new JLabel("Total Games: " + user.getTotalGames()));
        add(new JLabel("Total Wins: " + user.getWins()));
        add(new JLabel("Total Losses: " + user.getLosses()));
        add(new JLabel("Win/Loss Ratio: " + String.format("%.2f", user.getWinLossRatio())));
        add(new JLabel("Average Score/Game: " + String.format("%.2f", user.getAverageScorePerGame())));
    }
}
