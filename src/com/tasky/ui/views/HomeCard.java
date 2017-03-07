package com.tasky.ui.views;

import com.tasky.ui.BaseFrame;

import javax.swing.*;

/**
 * Created by markus on 2017-02-28.
 */

public class HomeCard extends JPanel {
    final public static String HOME_CARD = "Home Card";

    private BaseFrame baseFrame;
    private JLabel welcomeLabel;

    public HomeCard(BaseFrame baseFrame) {
        this.baseFrame = baseFrame;

        this.initComponents();
        this.createGUI();
    }

    private void initComponents() {
        if (this.baseFrame.isAuthed()) {
            this.welcomeLabel = new JLabel("Welcome, " + this.baseFrame.getApp().getAuth().getUsername());
        }
        else {
            this.welcomeLabel = new JLabel("Welcome, guest");
        }
    }

    private void createGUI() {
        this.add(this.welcomeLabel);
    }
}
