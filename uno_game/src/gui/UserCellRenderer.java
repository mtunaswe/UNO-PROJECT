package gui;

import game_model.UserInfo;

import javax.swing.*;
import java.awt.*;

public class UserCellRenderer extends DefaultListCellRenderer {
    private static final long serialVersionUID = 1L;

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof UserInfo) {
            UserInfo user = (UserInfo) value;
            setText(user.getRank()+". "+  user.toString());
        }
        return c;
    }
}