package com.tasky.app;

import com.tasky.app.models.Task;
import com.tasky.app.models.User;
import com.tasky.db.MySQLAccess;
import com.tasky.db.dao.UserDao;
import com.tasky.ui.UI;
import com.tasky.util.SortableList;

import java.sql.Connection;

/**
 * Created by markus on 2017-03-07.
 */
public class App {
    private Boolean isAuthed;
    private User auth;
    private TaskHandler taskHandler;
    private UserDao userDao;
    private UI ui;
    private Connection conn;

    public App() {
        SortableList fList = new SortableList();
//        fList.add(40);
//        fList.add(2);
//        fList.add(20);
        fList.add(new Task("Task1"));
        fList.add(new Task("Task5"));
        fList.add(new Task("Task3"));

        System.out.println(fList.toString());

        fList.sort();
        System.out.println(fList.toString());
//        fList.filterByName("A");

        try {
            this.conn = new MySQLAccess().getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        this.isAuthed = false;
        this.auth = null;
        this.ui = new UI(this);
        this.taskHandler = new TaskHandler(this);
        this.userDao = new UserDao(this.conn);
    }

    public User getAuth() {
        return this.auth;
    }

    public TaskHandler getTaskHandler() {
        return this.taskHandler;
    }

    public boolean login(String username, String password) {
        User user = new User(username, password);

        if (this.userDao.auth(user)) {
            this.setIsAuthed(true, user);
            return true;
        }
        return false;
    }

    public boolean isAuthed() {
        return this.isAuthed;
    }

    private void setIsAuthed(boolean isAuthed, User user) {
        this.isAuthed = isAuthed;
        this.auth = user;
        this.taskHandler.loadFromFile();
        this.ui.refreshUI();
    }

    public static void main(String[] args) {
        App app = new App();
    }
}
