package com.tasky.ui.views;

import com.tasky.ui.BaseFrame;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * Created by markus on 2017-02-28.
 */
public class AboutCard extends JPanel {

    final public static String ABOUT_CARD = "About Card";

    private final BaseFrame baseFrame;
    private JLabel headingLabel;
    private JLabel authorLabel;
    private JLabel versionLabel;
    private JLabel copyrightLabel;

    public AboutCard(BaseFrame baseFrame) {
        this.baseFrame = baseFrame;

        this.setLayout(new MigLayout("fillx, insets 60 0 0 0"));

        this.initComponents();
        this.createGUI();
    }

    private void initComponents() {
        this.headingLabel = new JLabel("Tasky Task Manager");
        this.headingLabel.setFont(new Font(this.headingLabel.getFont().getName(), Font.BOLD, 20));
        this.authorLabel = new JLabel("by Markus Hederström");
        this.versionLabel = new JLabel("Version: 0.0.1");
        this.copyrightLabel = new JLabel("Copyright (c) 2017 Blekinge Insitute of Technology");
    }

    private void createGUI() {
        this.add(this.headingLabel, "align center, wrap");
        this.add(this.authorLabel, "align center, wrap");
        this.add(this.versionLabel, "align center, wrap");
        this.add(this.copyrightLabel, "align center, wrap");
    }

}
