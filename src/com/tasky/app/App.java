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

    /**
     * Get auth
     * @return Auth object if user is authenticated else null
     */
    public User getAuth() {
        return this.auth;
    }

    /**
     * Getter for taskhandler
     * @return  The taskhandler
     */
    public TaskHandler getTaskHandler() {
        return this.taskHandler;
    }

    /**
     * Authenticate user with credentials
     * @param username  The username
     * @param password  The password
     * @return  True if authentication was successful else false
     */
    public boolean login(String username, String password) {
        User user = new User(username, password);

        // If authentication was successful
        if (this.userDao.auth(user)) {
            this.setIsAuthed(true, user);
            return true;
        }
        return false;
    }

    /**
     * Returns true if user is authenticated else false
     * @return  True if user is authenticated
     */
    public boolean isAuthed() {
        return this.isAuthed;
    }

    /**
     * Set auth and load users task from file, refresh UI
     * @param isAuthed
     * @param user
     */
    private void setIsAuthed(boolean isAuthed, User user) {
        this.isAuthed = isAuthed;
        this.auth = user;

        // Load the tasks from file
        this.taskHandler.loadFromFile();

        // Refresh UI
        this.ui.refreshUI();
    }

    public static void main(String[] args) {
        App app = new App();
    }
}
