package com.tasky.ui;

import com.tasky.app.models.Category;
import com.tasky.ui.views.*;

import javax.swing.*;


/**
 * Created by markus on 2017-03-07.
 */

class AppMenu extends JMenuBar {
    private final BaseFrame baseFrame;
    private JMenu fileMenu;
    private JMenu taskMenu;
    private JMenu categoryMenu;
    private JMenuItem aboutItem;
    private JMenuItem loginItem;
    private JMenuItem homeItem;
    private JMenuItem addTaskItem;
    private JMenuItem taskListItem;
    private JMenuItem addCategoryItem;

    AppMenu(BaseFrame baseFrame) {
        this.baseFrame = baseFrame;

        this.initComponents();
        this.createGUI();
        this.addEvents();
    }

    private void initComponents() {
        this.fileMenu = new JMenu("File");

        this.homeItem = new JMenuItem("Start");
        this.loginItem = new JMenuItem("Login");
        this.aboutItem = new JMenuItem("About");

        this.taskMenu = new JMenu("Tasks");

        this.addTaskItem = new JMenuItem("Add task");
        this.taskListItem = new JMenuItem("Tasks");

        this.categoryMenu = new JMenu("Categories");
        this.addCategoryItem = new JMenuItem("Add category");
    }

    private void createGUI() {
        this.add(this.fileMenu);

        if (this.baseFrame.getApp().isAuthed()) {
            this.fileMenu.add(this.homeItem);
            this.add(this.taskMenu);

            this.taskMenu.add(this.addTaskItem);
            this.taskMenu.add(this.taskListItem);

            this.categoryMenu.add(this.addCategoryItem);
            this.add(this.categoryMenu);
        }
        else {
            this.fileMenu.add(this.loginItem);
        }
        this.fileMenu.add(this.aboutItem);
    }

    private void addEvents() {
        this.homeItem.addActionListener(e -> {
            HomeCard homeCard = new HomeCard(baseFrame);
            baseFrame.getCardHandler().add(homeCard, HomeCard.HOME_CARD);
            baseFrame.getCardHandler().getCardLayout().show(baseFrame.getCardHandler(), HomeCard.HOME_CARD);
        });

        this.loginItem.addActionListener(e -> {
            LoginCard loginCard = new LoginCard(baseFrame);
            baseFrame.getCardHandler().add(loginCard, LoginCard.LOGIN_CARD);
            baseFrame.getCardHandler().getCardLayout().show(baseFrame.getCardHandler(), LoginCard.LOGIN_CARD);
        });

        this.aboutItem.addActionListener(e -> {
            AboutCard aboutCard = new AboutCard(baseFrame);
            baseFrame.getCardHandler().add(aboutCard, AboutCard.ABOUT_CARD);
            baseFrame.getCardHandler().getCardLayout().show(baseFrame.getCardHandler(), AboutCard.ABOUT_CARD);
        });

        this.taskListItem.addActionListener(e -> {
            TaskListCard taskListCard = new TaskListCard(baseFrame);
            baseFrame.getApp().getTaskHandler().addObserver(taskListCard);
            baseFrame.getApp().getCategoryHandler().addObserver(taskListCard);
            baseFrame.getCardHandler().add(taskListCard, TaskListCard.TASK_LIST_CARD);
            baseFrame.getCardHandler().getCardLayout().show(baseFrame.getCardHandler(), TaskListCard.TASK_LIST_CARD);
        });

        this.addTaskItem.addActionListener(e -> {
            String taskName = JOptionPane.showInputDialog(baseFrame,"Task name", "Add task", JOptionPane.PLAIN_MESSAGE);

            // If a task name is provided, add to list
            if ((taskName != null) && (taskName.length() > 0)) {
                baseFrame.getApp().getTaskHandler().addTask(taskName);
            }
        });

        this.addCategoryItem.addActionListener(e -> {
            String categoryName = JOptionPane.showInputDialog(baseFrame,"Category name", "Add category", JOptionPane.PLAIN_MESSAGE);

            // If a category name is entered, add it to the list
            if ((categoryName != null) && (categoryName.length() > 0)) {
                baseFrame.getApp().getCategoryHandler().add(new Category(categoryName));
            }
        });
    }

    void refresh() {
        this.fileMenu.removeAll();
        this.createGUI();
    }

}
