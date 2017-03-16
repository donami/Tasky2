package com.tasky.ui;

import com.tasky.app.App;
import com.tasky.ui.views.HomeCard;
import com.tasky.ui.views.LoginCard;

import javax.swing.*;

/**
 * Created by markus on 2017-03-07.
 */
public class BaseFrame extends JFrame {
    private CardHandler cardHandler;
    private HomeCard homeCard;
    private final App app;
    private final AppMenu menuBar;

    public BaseFrame(App app) {
        this.app = app;

        this.menuBar = new AppMenu(this);
        this.setJMenuBar(this.menuBar);

        this.initComponents();
        this.createGUI();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents() {
        this.cardHandler = new CardHandler();
        this.homeCard = new HomeCard(this);
    }

    private void createGUI() {
        this.setTitle("Tasky Task Manager");
        this.setSize(800, 600);
        this.add(this.cardHandler);
        if (this.isAuthed()) {
            this.cardHandler.add(this.homeCard);
        }
        else {
            this.cardHandler.add(new LoginCard(this));
        }
    }

    public boolean isAuthed() {
        return this.app.isAuthed();
    }

    public App getApp() {
        return this.app;
    }

    public CardHandler getCardHandler() {
        return this.cardHandler;
    }

    public void refresh() {
        this.menuBar.refresh();
    }
}
