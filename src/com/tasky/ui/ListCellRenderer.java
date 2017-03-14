package com.tasky.ui;

import com.tasky.app.models.Task;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Created by markus on 2017-03-09.
 */
public class ListCellRenderer extends DefaultListCellRenderer {

    private JLabel label;
    private Color textSelectionColor = Color.WHITE;
    private Color backgroundSelectionColor = new Color(95, 186, 125);
    private Color textNonSelectionColor = Color.BLACK;
    private Color backgroundNonSelectionColor = Color.WHITE;

    public ListCellRenderer() {
        this.label = new JLabel();
        this.label.setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean selected,
            boolean expanded) {

        Task task = (Task) value;

        ImageIcon icon;
        // Set icon depending on whether or not the task is completed
        if (task.getCompleted()) {
            icon = this.createImageIcon("/checked.png", "Completed");
        }
        else {
            icon = this.createImageIcon("/unchecked.png", "Not completed");
        }
        this.label.setIcon(icon);

        this.label.setText(task.getName());

        if (selected) {
            label.setBackground(this.backgroundSelectionColor);
            label.setForeground(this.textSelectionColor);
        } else {
            label.setBackground(this.backgroundNonSelectionColor);
            label.setForeground(this.textNonSelectionColor);
        }

        return label;
    }

    private ImageIcon createImageIcon(String path, String description) {
        URL imgURL = getClass().getResource(path);

        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        }
        else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
