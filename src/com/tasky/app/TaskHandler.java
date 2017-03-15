package com.tasky.app;

import com.tasky.app.models.Task;
import com.tasky.util.Encrypt;
import com.tasky.util.SortableList;

import java.io.*;
import java.util.Iterator;
import java.util.Observable;

/**
 * Created by markus on 2017-03-02.
 */
public class TaskHandler extends Observable {

    private App app;
    private SortableList<Task> tasks;
    private SortOrder defaultSortingOrder = SortOrder.ASC;

    public enum SortOrder { ASC, DESC }

    public TaskHandler(App app) {
        this.app = app;
        this.tasks = new SortableList<>();
    }

    /**
     * Get the filename to use based on the users username
     * @return String   Filename
     */
    private String getFilename() {
        return Encrypt.encrypt(this.app.getAuth().getUsername()) + ".ser";
    }

    /**
     * Add a task, and write to file
     * @param taskName  The name of the task
     */
    public void addTask(String taskName) {
        // Add the task to the list
        this.tasks.add(new Task(taskName));

        // Write changes to file
        this.writeListToFile();

        setChanged();
        notifyObservers(taskName);
    }

    /**
     * Load tasks from file
     */
    public void loadFromFile() throws Exception {
        File file = new File(this.getFilename());

        if (file.exists() && !file.isDirectory()) {
            // Load the tasks from file to the list
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(new FileInputStream(this.getFilename()));
                while (true) {
                    Task task = (Task) ois.readObject();
                    this.tasks.add(task);
                }
            } catch (EOFException e) {

            } finally {
                if (ois != null) {
                    ois.close();
                }
            }
        }
    }

    /**
     * Remove all tasks from list
     */
    public void clear() {
        this.tasks.clear();

        this.writeListToFile();

        setChanged();
        notifyObservers();
    }

    /**
     * Write the current list to file
     */
    private void writeListToFile() {
        // Write the tasks to the files
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(this.getFilename()));

            Iterator<Task> listIterator = this.tasks.iterator();
            while (listIterator.hasNext()) {
                os.writeObject(listIterator.next());
            }

            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Change task to completed
     * @param index Index of the task
     */
    public void setComplete(int index, boolean complete) {
        Task task = this.tasks.get(index);
        task.setCompleted(complete);

        this.writeListToFile();

        setChanged();
        notifyObservers(task.getName());
    }

    /**
     * Delete a task
     * @param index Index of the task
     */
    public void deleteTask(int index) {
        // Remove element from list model
        this.tasks.remove(index + 1);

        this.writeListToFile();

        setChanged();
        notifyObservers();
    }

    /**
     * Edit a task
     * @param index Index of the task
     * @param task  New task data
     * @return  True if action was successful
     */
    public boolean editTask(int index, Task task) {

        // Update task
        if (this.tasks.update(index, task)) {
            // Write changes to file
            this.writeListToFile();

            setChanged();
            notifyObservers();

            return true;
        }


        return false;
    }

    /**
     * Sort tasks in default order
     */
    public void sortTasks() {
        this.sortTasks(this.defaultSortingOrder);
    }

    /**
     * Sort tasks in specified order
     * @param sortingOrder  The order to sort by
     */
    public void sortTasks(SortOrder sortingOrder) {
        if (sortingOrder == SortOrder.ASC) {
            this.tasks.sort();
        }
        else {
            this.tasks.sortDesc();
        }

        setChanged();
        notifyObservers();
    }

    /**
     * Getter for the tasks
     * @return  The tasklist
     */
    public SortableList<Task> getTasks() {
        return this.tasks;
    }

}
