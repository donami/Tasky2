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
    private Color textSelectionColor = Color.BLACK;
    private Color backgroundSelectionColor = Color.CYAN;
    private Color textNonSelectionColor = Color.BLACK;
    private Color backgroundNonSelectionColor = Color.WHITE;

    public ListCellRenderer() {
        label = new JLabel();
        label.setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean selected,
            boolean expanded) {


        Task task = (Task) value;

        // Set icon depending on whether or not the task is completed
        if (task.getCompleted()) {
            ImageIcon icon = createImageIcon("/checked.png", "Completed");
            label.setIcon(icon);
        }
        else {
            ImageIcon icon = createImageIcon("/unchecked.png", "Not completed");
            label.setIcon(icon);
        }

        label.setText(task.getName());

        if (selected) {
            label.setBackground(backgroundSelectionColor);
            label.setForeground(textSelectionColor);
        } else {
            label.setBackground(backgroundNonSelectionColor);
            label.setForeground(textNonSelectionColor);
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
