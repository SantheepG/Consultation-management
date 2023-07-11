package GUI.GUIModels;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class StatusColumnCellRenderer extends DefaultTableCellRenderer {
    public StatusColumnCellRenderer() {
        super();
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value instanceof String) {
            String status = (String) value;
            if (status.equals("Available") || status.equals("Active")) {
                cell.setBackground(new Color(0, 255, 0, 50));
                cell.setForeground(Color.BLACK);
            } else if (status.equals("Full") || status.equals("Unavailable") || status.equals("OnHold")) {
                cell.setBackground(new Color(255, 0, 0, 50));
                cell.setForeground(Color.BLACK);
            } else if (status.equals("OnGoing")) {
                cell.setBackground(new Color(140, 0, 255, 82));
                cell.setForeground(Color.BLACK);
            } else {
                cell.setBackground(new Color(255, 255, 255));
                cell.setForeground(Color.BLACK);
            }
        }
        return cell;
    }
}
