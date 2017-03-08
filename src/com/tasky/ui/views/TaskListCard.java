package com.tasky.ui.views;

import com.tasky.app.models.Task;
import com.tasky.ui.BaseFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by markus on 2017-02-28.
 */

public class TaskListCard extends JPanel implements Observer {
    final public static String TASK_LIST_CARD = "Task SLList Card";

    private BaseFrame baseFrame;
    private JLabel titleLabel;
    private JButton deleteTaskButton;
    private JButton addTaskButton;
    private JList taskList;
    private DefaultListModel listModel;

    public TaskListCard(BaseFrame baseFrame) {
        this.baseFrame = baseFrame;

        this.initComponents();
        this.createGUI();
        this.addEvents();
    }

    @Override
    public void update(Observable o, Object arg) {
        this.refreshListModel();
    }

    private void initComponents() {
        this.setLayout(new MigLayout("fill"));

        this.listModel = new DefaultListModel();

        this.refreshListModel();

        this.taskList = new JList(this.listModel);
        this.taskList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        this.taskList.setVisibleRowCount(-1);

        this.titleLabel = new JLabel("Your tasks");
        this.addTaskButton = new JButton("Add task");
        this.deleteTaskButton = new JButton("Remove task");
        this.deleteTaskButton.setEnabled(false);
    }

    private void createGUI() {
        this.add(this.titleLabel, "w 80%");
        this.add(this.addTaskButton, "w 10%");
        this.add(this.deleteTaskButton, "w 10%, wrap");
        this.add(this.taskList, "w 100%, h 100%, span, wrap");
    }

    private void refreshListModel() {
        this.listModel.clear();

        Iterator<Task> listIterator = this.baseFrame.getApp().getTaskHandler().getTasks().iterator();
        while (listIterator.hasNext()) {
            this.listModel.addElement(listIterator.next());
        }
    }

    private void addEvents() {
        this.deleteTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (taskList.getSelectedIndex() > -1) {
                    baseFrame.getApp().getTaskHandler().deleteTask(taskList.getSelectedIndex());

                    // If there are no remaining tasks, or no task is selected,
                    // disable the button
                    if (listModel.getSize() <= 0 ||
                            taskList.getSelectedIndex() == -1)
                    {
                        deleteTaskButton.setEnabled(false);
                    }
                }
            }
        });

        this.addTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskName = (String) JOptionPane.showInputDialog("Task name");

                // If a task name is provided, write to file
                if ((taskName != null) && (taskName.length() > 0)) {
                    baseFrame.getApp().getTaskHandler().addTask(taskName);
                }
            }
        });

        this.taskList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    deleteTaskButton.setEnabled(true);
                }
            }
        });
    }
}
