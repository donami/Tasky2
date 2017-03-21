package com.tasky.ui.views;

import com.tasky.app.CategoryHandler;
import com.tasky.app.TaskHandler;
import com.tasky.app.models.Category;
import com.tasky.app.models.Task;
import com.tasky.ui.BaseFrame;
import com.tasky.ui.ListCellRenderer;
import net.miginfocom.swing.MigLayout;
import org.apache.commons.lang.time.DateUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private JScrollPane jScrollPane;
    private JScrollPane categoryScrollPane;
    private JList<Task> taskList;
    private JPanel panelBottomButtons;
    private JPanel panelTopButtons;
    private JPanel panelMenu;
    private StatusPanel statusPanel;
    private JList<Category> categoryList;
    private DefaultListModel<Category> categoryListModel;
    private JButton addCategoryButton;
    private Category noCategorySelected;

    public TaskListCard(BaseFrame baseFrame) {
        this.baseFrame = baseFrame;

        this.initComponents();
        this.createGUI();
        this.addEvents();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof TaskHandler) {
            this.refreshListModel();
        }
        else if (o instanceof CategoryHandler) {
            this.refreshCategoryListModel();
        }
    }

    private void initComponents() {
        this.setLayout(new MigLayout());

        this.listModel = new DefaultListModel<>();

        this.taskList = new JList<>();
        this.taskList.setModel(this.listModel);
        ListCellRenderer renderer = new ListCellRenderer();
        this.taskList.setCellRenderer(renderer);
        this.jScrollPane = new JScrollPane(taskList);
        this.statusPanel = new StatusPanel();

        this.refreshListModel();

        this.noCategorySelected = new Category("No category", "no_cat");
        this.categoryListModel = new DefaultListModel<>();
        this.categoryListModel.addElement(this.noCategorySelected);
        this.categoryList = new JList<>();
        this.categoryList.setModel(this.categoryListModel);
        this.categoryScrollPane = new JScrollPane(this.categoryList);

        this.refreshCategoryListModel();

        this.panelMenu = new JPanel();

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

        this.addCategoryButton = new JButton("Add category");

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
        this.panelMenu.add(this.addCategoryButton);

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

        this.add(this.panelMenu, "w 100%, span, wrap");
        this.add(this.panelTopButtons, "w 100%, span, wrap");
        this.add(this.categoryScrollPane, "w 30%, h 100%");
        this.add(this.jScrollPane, "w 70%, h 100%, span, wrap");
        this.add(this.panelBottomButtons, "w 100%, span, wrap");
        this.add(new JSeparator(SwingConstants.HORIZONTAL), "w 100%, span, wrap");
        this.add(this.statusPanel);
    }

    private void refreshListModel() {
        this.listModel.clear();

        Iterator<Task> listIterator = this.baseFrame.getApp().getTaskHandler().getTasks().iterator();
        while (listIterator.hasNext()) {
            this.listModel.addElement(listIterator.next());
        }

        this.statusPanel.updateUI(this.baseFrame.getApp().getTaskHandler().getTasks());
    }

    private void refreshCategoryListModel() {
        this.categoryListModel.clear();
        this.categoryListModel.addElement(this.noCategorySelected);

        Iterator<Category> listIterator = this.baseFrame.getApp().getCategoryHandler().getList().iterator();
        while (listIterator.hasNext()) {
            this.categoryListModel.addElement(listIterator.next());
        }
    }

    /**
     * Event handler for adding category
     */
    private void handleAddCategoryClick() {
        String categoryName = JOptionPane.showInputDialog(this.baseFrame, "Category title");

        if (categoryName != null) {
            Category category = new Category(categoryName, "main");
            this.baseFrame.getApp().getCategoryHandler().add(category);
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

    /**
     * Display task information When user
     * double clicks a task in the list
     */
    private void handleTaskDoubleClick() {
        if (this.taskList.getSelectedIndex() < 0) {
            return;
        }

        Task task = taskList.getSelectedValue();

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new MigLayout());

        infoPanel.add(new JLabel("Task name:"));
        infoPanel.add(new JLabel(task.getName()), "wrap");

        infoPanel.add(new JLabel("Category:"));
        infoPanel.add(new JLabel((task.getCategory() == null) ? "No category" : task.getCategory().getTitle()), "wrap");

        infoPanel.add(new JLabel("Due date:"));
        infoPanel.add(new JLabel((task.getDueDate() == null) ? "Not specified" : task.getDueDate().toString()), "wrap");

        infoPanel.add(new JLabel(task.getCompleted() ? "Completed" : "Not completed"), "wrap");

        JOptionPane.showMessageDialog(baseFrame, infoPanel, "View task", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Event handler for clicking sort button
     */
    private void handleSortClick() {
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
    }

    /**
     * Event handler for clicking add button
     */
    private void handleAddClick() {
        String taskName = JOptionPane.showInputDialog("Task name");

        // If a task name is provided, write to file
        if ((taskName != null) && (taskName.length() > 0)) {
            baseFrame.getApp().getTaskHandler().addTask(taskName);
        }
    }

    /**
     * Event handler for clicking toggle complete button
     */
    private void handleToggleCompleteClick() {
        if (this.taskList.getSelectedIndex() > -1) {
            this.baseFrame.getApp().getTaskHandler().setComplete(this.taskList.getSelectedIndex() + 1,
                    !this.taskList.getSelectedValue().getCompleted());
        }
    }

    /**
     * Event handler for clicking edit button
     */
    private void handleEditClick() {
        if (this.taskList.getSelectedIndex() > -1) {
            Task selectedTask = this.taskList.getSelectedValue();
            JTextField taskNameInput = new JTextField(selectedTask.getName());
            JCheckBox completed = new JCheckBox();
            JComboBox<Category> categoryComboBox = new JComboBox<>();
            JComboBox<Date> dateComboBox = new JComboBox<>();

            if (this.baseFrame.getApp().getCategoryHandler().getList().getNrOfElements() <= 0) {
                categoryComboBox.setEnabled(false);
            }

            GregorianCalendar calendar = new GregorianCalendar();
            dateComboBox.addItem(calendar.getTime());

            Iterator<Category> listIterator = this.baseFrame.getApp().getCategoryHandler().getList().iterator();
            while (listIterator.hasNext()) {
                categoryComboBox.addItem(listIterator.next());
            }

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
            dialogPanel.add(new JLabel("Category"));
            dialogPanel.add(categoryComboBox, "w 100%, wrap");
            dialogPanel.add(new JLabel("Due date"));
            dialogPanel.add(dateComboBox, "wrap");
            dialogPanel.add(new JLabel("Completed"));
            dialogPanel.add(completed, "wrap");

            int result = JOptionPane.showConfirmDialog(this.baseFrame, dialogPanel, "Edit task", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                selectedTask.setName(taskNameInput.getText());
                selectedTask.setDueDate((Date) dateComboBox.getSelectedItem());
                selectedTask.setCompleted(completed.isSelected());
                selectedTask.setCategory((Category) categoryComboBox.getSelectedItem());

                if (selectedTask.getName() != null) {
                    this.baseFrame.getApp().getTaskHandler().editTask(this.taskList.getSelectedIndex() + 1, selectedTask);
                }
            }
        }
    }

    /**
     * Event handler for clicking delete button
     */
    private void handleDeleteClick() {
        // Delete task if it's selected
        if (taskList.getSelectedIndex() > -1) {
            baseFrame.getApp().getTaskHandler().deleteTask(taskList.getSelectedIndex());
        }
    }

    /**
     * Event handler for selecting a task
     * @param e Event
     */
    private void taskListOnChange(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if (this.taskList.getSelectedIndex() > -1) {
                this.deleteTaskButton.setEnabled(true);
                this.editTaskButton.setEnabled(true);
                this.toggleCompleteButton.setEnabled(true);
            } else {
                this.deleteTaskButton.setEnabled(false);
                this.editTaskButton.setEnabled(false);
                this.toggleCompleteButton.setEnabled(false);
            }

            // The clear button should only be enabled if there are any tasks
            this.clearTasksButton.setEnabled(!this.listModel.isEmpty());
        }
    }

    /**
     * Event handler for selecting a task
     * @param e Event
     */
    private void categoryListOnChange(ListSelectionEvent e) {
        if (categoryList.getSelectedValue() == null || e.getValueIsAdjusting()) {
            return;
        }

        if (this.categoryList.getSelectedValue().equals(this.noCategorySelected)) {
            this.listModel.clear();
        }

        Iterator<Task> listIterator = this.baseFrame.getApp().getTaskHandler().getTasks().iterator();
        while (listIterator.hasNext()) {
            Task curr = listIterator.next();

            // If default category is selected, all tasks
            // should be displayed else filter by category
            if (this.categoryList.getSelectedValue().equals(this.noCategorySelected)) {
                this.listModel.addElement(curr);
            }
            else {
                if (!curr.getCategory().getTitle().equals(this.categoryList.getSelectedValue().getTitle())) {
                    if (this.listModel.contains(curr)) {
                        this.listModel.removeElement(curr);
                    }
                }
                else {
                    if (!this.listModel.contains(curr)) {
                        this.listModel.addElement(curr);
                    }
                }
            }
        }
    }

    /**
     * Event handler for clicking clear
     */
    private void handleClearClick() {
        this.baseFrame.getApp().getTaskHandler().clear();
    }

    /**
     * Add events for the card
     */
    private void addEvents() {
        this.addCategoryButton.addActionListener(e -> this.handleAddCategoryClick());

        this.sortButton.addActionListener(e -> this.handleSortClick());

        this.toggleCompleteButton.addActionListener(e -> this.handleToggleCompleteClick());

        this.clearTasksButton.addActionListener(e -> this.handleClearClick());

        this.editTaskButton.addActionListener(e -> this.handleEditClick());

        this.deleteTaskButton.addActionListener(e -> this.handleDeleteClick());

        this.addTaskButton.addActionListener(e -> this.handleAddClick());

        this.taskList.getSelectionModel().addListSelectionListener(this::taskListOnChange);

        this.categoryList.getSelectionModel().addListSelectionListener(this::categoryListOnChange);

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

        this.taskList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                // If it was a double click
                if (e.getClickCount() == 2) {
                    handleTaskDoubleClick();
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
