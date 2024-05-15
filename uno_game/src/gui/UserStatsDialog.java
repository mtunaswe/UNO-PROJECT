package gui;

import game_model.UserInfo;

import javax.swing.*;
import java.awt.*;

/**
 * The UserStatsDialog class displays a dialog box with statistics for a specified user.
 * It extends JDialog to provide a modal dialog window.
 */
public class UserStatsDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a UserStatsDialog with the given parent frame and user information.
     *
     * @param parent The parent JFrame that owns this dialog.
     * @param user   The UserInfo object containing the user's statistics to display.
     */
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
