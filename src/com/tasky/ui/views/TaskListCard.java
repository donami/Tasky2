package com.tasky.ui.views;

import com.tasky.app.TaskHandler;
import com.tasky.app.models.Task;
import com.tasky.ui.BaseFrame;
import com.tasky.ui.ListCellRenderer;
import net.miginfocom.swing.MigLayout;
import org.apache.commons.lang.time.DateUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by markus on 2017-02-28.
 */

public class TaskListCard extends JPanel implements Observer {
    final public static String TASK_LIST_CARD = "Task SLList Card";

    private final BaseFrame baseFrame;
    private JButton deleteTaskButton;
    private JButton addTaskButton;
    private JButton sortButton;
    private JButton toggleCompleteButton;
    private JButton editTaskButton;
    private JButton clearTasksButton;
    private JComboBox<String> sortOrderComboBox;
    private JTextField filterTextField;
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
        this.panelTopButtons.setLayout(new MigLayout("fill"));

        this.addTaskButton = new JButton("Add task");
        this.deleteTaskButton = new JButton("Remove task");
        this.deleteTaskButton.setEnabled(false);
        this.editTaskButton = new JButton("Edit");
        this.editTaskButton.setEnabled(false);
        this.toggleCompleteButton = new JButton("Toggle complete");
        this.toggleCompleteButton.setEnabled(false);
        this.sortButton = new JButton("Sort");
        this.clearTasksButton = new JButton("Clear all");
        this.filterTextField = new JTextField(20);

        String[] availableSortOrders = {
            "Ascending",
            "Descending",
            "Due date Asc",
            "Due date Desc",
            "Completed Asc",
            "Completed Desc"
        };

        this.sortOrderComboBox = new JComboBox<>(availableSortOrders);
        this.sortOrderComboBox.setMaximumSize(new Dimension(120, 50));
    }

    private void createGUI() {
        this.panelBottomButtons.add(this.editTaskButton);
        this.panelBottomButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        this.panelBottomButtons.add(this.deleteTaskButton);
        this.panelBottomButtons.add(Box.createHorizontalGlue());
        this.panelBottomButtons.add(this.clearTasksButton);
        this.panelBottomButtons.add(Box.createHorizontalGlue());
        this.panelBottomButtons.add(this.toggleCompleteButton);

        this.panelTopButtons.add(this.addTaskButton, "align left");
        this.panelTopButtons.add(new JLabel("Filter by name"), "align center, split 2");
        this.panelTopButtons.add(this.filterTextField);
        this.panelTopButtons.add(this.sortOrderComboBox, "align right, split 2");
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

    /**
     * Filter the list model by string
     * @param filter    The string to filter by
     */
    private void filterModel(String filter) {
        Iterator<Task> listIterator = this.baseFrame.getApp().getTaskHandler().getTasks().iterator();
        while (listIterator.hasNext()) {
            Task curr = listIterator.next();

            if (!curr.getName().startsWith(filter)) {
                if (listModel.contains(curr)) {
                    listModel.removeElement(curr);
                }
            }
            else {
                if (!listModel.contains(curr)) {
                    listModel.addElement(curr);
                }
            }
        }
    }

    private void addEvents() {
        this.filterTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterModel(filterTextField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterModel(filterTextField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterModel(filterTextField.getText());
            }
        });

        this.deleteTaskButton.addActionListener(e -> {
            if (taskList.getSelectedIndex() > -1) {
                // Delete task
                baseFrame.getApp().getTaskHandler().deleteTask(taskList.getSelectedIndex());
            }
        });

        this.addTaskButton.addActionListener(e -> {
            String taskName = JOptionPane.showInputDialog("Task name");

            // If a task name is provided, write to file
            if ((taskName != null) && (taskName.length() > 0)) {
                baseFrame.getApp().getTaskHandler().addTask(taskName);
            }
        });

        this.taskList.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (taskList.getSelectedIndex() > -1) {
                    this.deleteTaskButton.setEnabled(true);
                    this.editTaskButton.setEnabled(true);
                    this.toggleCompleteButton.setEnabled(true);
                }
                else {
                    this.deleteTaskButton.setEnabled(false);
                    this.editTaskButton.setEnabled(false);
                    this.toggleCompleteButton.setEnabled(false);
                }

                // The clear button should only be enabled if there are any tasks
                this.clearTasksButton.setEnabled(!this.listModel.isEmpty());
            }
        });

        this.sortButton.addActionListener(e -> {
            TaskHandler.SortOrder selectedSortOrder;

            switch (sortOrderComboBox.getSelectedIndex()) {
                case 0: selectedSortOrder = TaskHandler.SortOrder.NAME_ASC; break;
                case 1: selectedSortOrder = TaskHandler.SortOrder.NAME_DESC; break;
                case 2: selectedSortOrder = TaskHandler.SortOrder.DUE_DATE_ASC; break;
                case 3: selectedSortOrder = TaskHandler.SortOrder.DUE_DATE_DESC; break;
                case 4: selectedSortOrder = TaskHandler.SortOrder.COMPLETED_ASC; break;
                case 5: selectedSortOrder = TaskHandler.SortOrder.COMPLETED_DESC; break;
                default: selectedSortOrder = baseFrame.getApp().getTaskHandler().getDefaultSortingOrder();
            }

            baseFrame.getApp().getTaskHandler().sortTasks(selectedSortOrder);
        });

        this.toggleCompleteButton.addActionListener(e -> {
            if (taskList.getSelectedIndex() > -1) {
                baseFrame.getApp().getTaskHandler().setComplete(taskList.getSelectedIndex() + 1,
                        !this.taskList.getSelectedValue().getCompleted());
            }
        });

        this.clearTasksButton.addActionListener(e -> baseFrame.getApp().getTaskHandler().clear());

        this.editTaskButton.addActionListener(e -> {
            if (taskList.getSelectedIndex() > -1) {
                Task selectedTask = taskList.getSelectedValue();

                JTextField taskNameInput = new JTextField(selectedTask.getName());
                JCheckBox completed = new JCheckBox();
                JComboBox<Date> dateComboBox = new JComboBox<>();

                GregorianCalendar calendar = new GregorianCalendar();
                dateComboBox.addItem(calendar.getTime());

                // Add dates to the combo box
                for (int i = 0; i < 30; i++) {
                    calendar.roll(GregorianCalendar.DAY_OF_YEAR, 1);
                    dateComboBox.addItem(calendar.getTime());

                    // If there already is a due date, select it
                    if (selectedTask.getDueDate() != null &&
                        DateUtils.isSameDay(selectedTask.getDueDate(), calendar.getTime()))
                    {
                        dateComboBox.setSelectedItem(calendar.getTime());
                    }
                }
                dateComboBox.setRenderer(new DateComboBoxRenderer());

                if (selectedTask.getCompleted()) {
                    completed.setSelected(true);
                }

                JPanel dialogPanel = new JPanel();
                dialogPanel.setLayout(new MigLayout());

                dialogPanel.add(new JLabel("Task name"));
                dialogPanel.add(taskNameInput, "w 100%, wrap");
                dialogPanel.add(new JLabel("Due date"));
                dialogPanel.add(dateComboBox, "wrap");
                dialogPanel.add(new JLabel("Completed"));
                dialogPanel.add(completed, "wrap");

                int result = JOptionPane.showConfirmDialog(baseFrame, dialogPanel, "Edit task", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    selectedTask.setName(taskNameInput.getText());
                    selectedTask.setDueDate((Date) dateComboBox.getSelectedItem());
                    selectedTask.setCompleted(completed.isSelected());

                    if (selectedTask.getName() != null) {
                        baseFrame.getApp().getTaskHandler().editTask(taskList.getSelectedIndex() + 1, selectedTask);
                    }
                }
            }
        });
    }

    // Create Date Renderer for formatting Date
    public static class DateComboBoxRenderer extends DefaultListCellRenderer {

        // desired format for the date
        private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Object item = value;

            // if the item to be rendered is date then format it
            if (item instanceof Date) {
                item = dateFormat.format((Date) item);
            }

            return super.getListCellRendererComponent(list, item, index, isSelected, cellHasFocus);
        }
    }

}
