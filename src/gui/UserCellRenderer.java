package gui;

import game_model.UserInfo;

import javax.swing.*;
import java.awt.*;

/**
 * The UserCellRenderer class customizes the rendering of UserInfo objects in a JList.
 * It extends DefaultListCellRenderer to display user information with their rank.
 */
public class UserCellRenderer extends DefaultListCellRenderer {
    private static final long serialVersionUID = 1L;

    /**
     * Returns a component that has been configured to display the specified value.
     *
     * @param list         The JList we're painting.
     * @param value        The value returned by list.getModel().getElementAt(index).
     * @param index        The cell's index.
     * @param isSelected   True if the specified cell was selected.
     * @param cellHasFocus True if the specified cell has the focus.
     * @return A component whose paint() method will render the specified value.
     */
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof UserInfo) {
            UserInfo user = (UserInfo) value;
            setText(user.getRank() + ". " + user.toString());
        }
        return c;
    }
}
