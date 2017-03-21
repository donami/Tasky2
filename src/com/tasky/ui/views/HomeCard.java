package com.tasky.ui.views;

import com.tasky.ui.BaseFrame;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by markus on 2017-02-28.
 */

public class HomeCard extends JPanel {
    final public static String HOME_CARD = "Home Card";

    private final BaseFrame baseFrame;
    private JLabel welcomeLabel;
    private JLabel welcomeText;

    public HomeCard(BaseFrame baseFrame) {
        this.baseFrame = baseFrame;

        this.setLayout(new BorderLayout());

        this.initComponents();
        this.createGUI();
    }

    private void initComponents() {
        if (this.baseFrame.isAuthed()) {
            this.welcomeLabel = new JLabel("Welcome, " + this.baseFrame.getApp().getAuth().getUsername());
            this.welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
            this.welcomeText = new JLabel("With Tasky Task Manager you can easily handle your daily or weekly tasks");
            this.welcomeText.setHorizontalAlignment(JLabel.CENTER);
            this.welcomeLabel.setBorder(new CompoundBorder(this.welcomeLabel.getBorder(), new EmptyBorder(10, 10, 10, 10)));
        }
        else {
            this.welcomeLabel = new JLabel("Welcome, guest");
        }
    }

    private void createGUI() {
        this.add(this.welcomeLabel, BorderLayout.NORTH);
        if (this.baseFrame.isAuthed()) {
            this.add(this.welcomeText, BorderLayout.CENTER);
        }
    }
}
