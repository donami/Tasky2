package com.tasky.ui.views;

import com.tasky.ui.BaseFrame;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * Created by markus on 2017-02-28.
 */
public class LoginCard extends JPanel {

    final public static String LOGIN_CARD = "Login Card";

    private final BaseFrame baseFrame;
    private JButton loginButton;
    private TextField usernameTextField;
    private JPasswordField passwordTextField;
    private JLabel welcomeLabel;

    public LoginCard(BaseFrame baseFrame) {
        this.baseFrame = baseFrame;

        this.initComponents();
        this.createGUI();
        this.addEvents();
    }

    private void initComponents() {
        this.welcomeLabel = new JLabel("Welcome! Please enter your credentials");
        this.usernameTextField = new TextField();
        this.passwordTextField = new JPasswordField();
        this.loginButton = new JButton("Login");
    }

    private void createGUI() {
        this.setLayout(new MigLayout("fillx, insets 60 20 20 20"));

        this.usernameTextField.setPreferredSize(new Dimension(500, 30));
        this.usernameTextField.setMaximumSize(this.usernameTextField.getPreferredSize());
        this.usernameTextField.setText("user"); // TODO: 2017-03-08 remove line
        this.passwordTextField.setPreferredSize(new Dimension(500, 30));
        this.passwordTextField.setMaximumSize(this.passwordTextField.getPreferredSize());
        this.passwordTextField.setText("123");  // TODO: 2017-03-08 remove line 

        this.add(this.welcomeLabel, "align center, span");
        this.add(this.usernameTextField, "align center, span");
        this.add(this.passwordTextField, "align center, span");
        this.add(this.loginButton, "align center, wrap");
    }

    private void addEvents() {
        this.loginButton.addActionListener(e -> this.login(usernameTextField.getText(), passwordTextField.getText()));
    }

    private void login(String username, String password) {
        // Validate credentials
        if (this.baseFrame.getApp().login(username, password)) {
            HomeCard homeCard = new HomeCard(baseFrame);
            baseFrame.getCardHandler().add(homeCard, HomeCard.HOME_CARD);
            baseFrame.getCardHandler().getCardLayout().show(baseFrame.getCardHandler(), HomeCard.HOME_CARD);
        }
        else {
            JOptionPane.showMessageDialog(this.baseFrame, "Invalid credentials");
        }
    }
}
