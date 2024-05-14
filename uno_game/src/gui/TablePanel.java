package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import card_model.WildCard;

public class TablePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private ViewCard topCard;
    private JPanel table;
    private Image backgroundImage;

    InfoPanel infoPanel = new InfoPanel();

    public TablePanel(ViewCard firstCard) {
        try {
            backgroundImage = ImageIO.read(new File("C:\\Users\\merta\\git\\UNO-PROJECT\\uno_game\\src\\resources\\table_background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setOpaque(false);
        setLayout(new GridBagLayout());

        topCard = firstCard;
        table = new JPanel();
        table.setBackground(new Color(64, 64, 64));

        setTable();
        setComponents();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image at full panel size
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void setTable() {
        table.setPreferredSize(new Dimension(500, 200));
        table.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        table.add(topCard, c);
    }

    private void setComponents() {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 130, 0, 45);
        add(table, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_END;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(0, 1, 0, 1);
        add(infoPanel, c);
    }

    public void setPlayedCard(ViewCard playedCard) {
        table.removeAll();
        topCard = playedCard;
        setTable();

        setBackgroundColor(playedCard);
    }

    public void setBackgroundColor(ViewCard playedCard) {
        Color background;
        if (playedCard instanceof WildCard)
            background = ((WildCard) playedCard).getWildColor();
        else
            background = playedCard.getColor();

        table.setBackground(background);
    }
}
