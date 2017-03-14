package com.tasky.ui.views;

import com.tasky.app.TaskHandler;
import com.tasky.app.models.Task;
import com.tasky.ui.BaseFrame;
import com.tasky.ui.ListCellRenderer;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
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
    private JButton deleteTaskButton;
    private JButton addTaskButton;
    private JButton sortButton;
    private JButton setCompleteButton;
    private JButton setNotCompleteButton;
    private JButton editTaskButton;
    private JComboBox<String> sortOrderComboBox;
    private DefaultListModel<Task> listModel;
    private JScrollPane jScrollPane1;
    private JList<Task> taskList;
    private JPanel panelBottomButtons;
    private JPanel panelTopButtons;

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
        this.setLayout(new MigLayout());

        this.listModel = new DefaultListModel<>();

        this.taskList = new JList<>();
        this.taskList.setModel(this.listModel);
        ListCellRenderer renderer = new ListCellRenderer();
        this.taskList.setCellRenderer(renderer);
        this.jScrollPane1 = new JScrollPane(taskList);

        this.refreshListModel();

        this.panelBottomButtons = new JPanel();
        this.panelBottomButtons.setLayout(new BoxLayout(this.panelBottomButtons, BoxLayout.LINE_AXIS));
        this.panelBottomButtons.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));


        this.panelTopButtons = new JPanel();
        this.panelTopButtons.setLayout(new BoxLayout(this.panelTopButtons, BoxLayout.LINE_AXIS));
        this.panelTopButtons.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        this.addTaskButton = new JButton("Add task");
        this.deleteTaskButton = new JButton("Remove task");
        this.deleteTaskButton.setEnabled(false);
        this.sortButton = new JButton("Sort");
        this.setCompleteButton = new JButton("Mark as completed");
        this.setNotCompleteButton = new JButton("Mark as not complete");
        this.editTaskButton = new JButton("Edit");

        String[] availableSortOrders = { "Ascending", "Descending" };
        this.sortOrderComboBox = new JComboBox<>(availableSortOrders);
        this.sortOrderComboBox.setMaximumSize(new Dimension(80, 50));
    }

    private void createGUI() {
        this.panelBottomButtons.add(this.editTaskButton);
        this.panelBottomButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        this.panelBottomButtons.add(this.deleteTaskButton);
        this.panelBottomButtons.add(Box.createHorizontalGlue());
        this.panelBottomButtons.add(this.setCompleteButton);
        this.panelBottomButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        this.panelBottomButtons.add(this.setNotCompleteButton);

        this.panelTopButtons.add(this.addTaskButton);
        this.panelTopButtons.add(Box.createHorizontalGlue());
        this.panelTopButtons.add(this.sortOrderComboBox);
        this.panelTopButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        this.panelTopButtons.add(this.sortButton);

        this.add(this.panelTopButtons, "w 100%, span, wrap");
        this.add(this.jScrollPane1, "w 100%, h 100%, span, wrap");
        this.add(this.panelBottomButtons, "w 100%, span, wrap");
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

        this.sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (sortOrderComboBox.getSelectedIndex()) {
                    case 0:
                        baseFrame.getApp().getTaskHandler().sortTasks(TaskHandler.SortOrder.ASC);
                        break;
                    case 1:
                        baseFrame.getApp().getTaskHandler().sortTasks(TaskHandler.SortOrder.DESC);
                        break;
                    default:
                        baseFrame.getApp().getTaskHandler().sortTasks();

                }
            }
        });

        this.setCompleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (taskList.getSelectedIndex() > -1) {
                    baseFrame.getApp().getTaskHandler().setComplete(taskList.getSelectedIndex() + 1, true);
                }
            }
        });

        this.setNotCompleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (taskList.getSelectedIndex() > -1) {
                    baseFrame.getApp().getTaskHandler().setComplete(taskList.getSelectedIndex() + 1, false);
                }
            }
        });

        this.editTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (taskList.getSelectedIndex() > -1) {

                    Task selectedTask = taskList.getSelectedValue();

                    JTextField taskNameInput = new JTextField(selectedTask.getName());
                    JCheckBox completed = new JCheckBox();

                    if (selectedTask.getCompleted()) {
                        completed.setSelected(true);
                    }

                    JPanel dialogPanel = new JPanel();
                    dialogPanel.setLayout(new MigLayout());

                    dialogPanel.add(new JLabel("Task name"));
                    dialogPanel.add(taskNameInput, "w 100%, wrap");
                    dialogPanel.add(new JLabel("Completed"));
                    dialogPanel.add(completed, "wrap");


                    int result = JOptionPane.showConfirmDialog(baseFrame, dialogPanel, "Enter new data", JOptionPane.OK_CANCEL_OPTION);

                    if (result == JOptionPane.OK_OPTION) {
                        selectedTask.setName(taskNameInput.getText());
                        selectedTask.setCompleted(completed.isSelected());

                        if (selectedTask.getName() != null) {
                            baseFrame.getApp().getTaskHandler().editTask(taskList.getSelectedIndex() + 1, selectedTask);
                        }
                    }
                }
            }
        });
    }

}
