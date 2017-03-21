package com.tasky.ui;
import com.tasky.app.App;

import java.awt.*;

/**
 * Created by markus on 2017-03-07.
 */
public class UI {
    private final BaseFrame frame;

    public UI(App app) {
        this.frame = new BaseFrame(app);
        this.frame.setMinimumSize(new Dimension(800, 600));
        this.frame.setVisible(true);
    }

    /**
     * Refresh the UI to update changes
     */
    public void refreshUI() {
        this.frame.refresh();
    }
}
