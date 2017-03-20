package com.tasky.ui.views;

import com.tasky.app.models.Task;
import com.tasky.ui.IView;
import com.tasky.util.SortableList;
import org.apache.commons.lang.time.DateUtils;

import javax.swing.*;
import java.util.Calendar;
import java.util.Iterator;

/**
 * Created by markus on 2017-03-19.
 */
public class StatusPanel extends JPanel implements IView {

    private JLabel dueTodayLabel;
    private int dueTodayNum;

    StatusPanel() {
        this.initComponents();
        this.createGUI();
    }

    public void initComponents() {
        this.dueTodayNum = 0;
        this.dueTodayLabel = new JLabel("Tasks due today: " + this.dueTodayNum);
    }

    public void createGUI() {
        this.add(this.dueTodayLabel);
    }

    /**
     * Update the UI
     * @param tasks List of tasks
     */
    void updateUI(SortableList<Task> tasks) {
        this.dueTodayNum = this.calcDueDateToday(tasks);
        this.dueTodayLabel.setText("Tasks due today: " + this.dueTodayNum);
    }

    /**
     * Calculate the number of tasks that are due today
     * @param tasks List of tasks
     * @return  Number of tasks
     */
    private int calcDueDateToday(SortableList<Task> tasks) {
        int num = 0;
        Calendar calendar = Calendar.getInstance();

        // Iterate through lists to find tasks due today
        Iterator<Task> listIterator = tasks.iterator();
        while (listIterator.hasNext()) {
            Task curr = listIterator.next();

            if (curr.getDueDate() != null) {
                // If the tasks due date is today, increment counter
                if (DateUtils.isSameDay(curr.getDueDate(), calendar.getTime())) {
                    num++;
                }
            }
        }

        return num;
    }

}
