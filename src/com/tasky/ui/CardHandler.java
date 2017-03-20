package com.tasky.ui;


import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 * Created by markus on 2017-02-28.
 */

public class CardHandler extends JPanel {

    private CardLayout cardLayout;

    CardHandler() {
        this.cardLayout = new CardLayout();
        this.setLayout(cardLayout);
    }

    /**
     * Get the card layout
     * @return  The card layout
     */
    public CardLayout getCardLayout() {
        return this.cardLayout;
    }

    /**
     * Set the card layout
     * @param cardLayout    The card layout
     */
    public void setCardLayout(CardLayout cardLayout) {
        this.cardLayout = cardLayout;
    }
}
