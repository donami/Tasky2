package com.tasky.ui;


import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 * Created by markus on 2017-02-28.
 */

public class CardHandler extends JPanel {

    private CardLayout cardLayout;

    public CardHandler() {
        this.cardLayout = new CardLayout();
        this.setLayout(cardLayout);
    }

    public CardLayout getCardLayout() {
        return this.cardLayout;
    }

    public void setCardLayout(CardLayout cardLayout) {
        this.cardLayout = cardLayout;
    }
}
