package com.tasky.ui;
import com.tasky.app.App;
import com.tasky.app.TaskHandler;

import javax.swing.*;

/**
 * Created by markus on 2017-03-07.
 */
public class UI {
    private BaseFrame frame;

    public UI(App app) {
        this.frame = new BaseFrame(app);
        this.frame.setVisible(true);
    }

    /**
     * Refresh the UI to update changes
     */
    public void refreshUI() {
        this.frame.refresh();
    }
}
