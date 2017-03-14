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
        return Encrypt.encrypt(this.app.getAuth().getUsername()) + ".txt";
    }

    /**
     * Add a task, and write to file
     * @param taskName  The name of the task
     */
    public void addTask(String taskName) {
        // Add the task to the file, appending to previous _tasks
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.getFilename(), true))) {

            String content = taskName + "\n";

            bw.write(content);

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.tasks.add(new Task(taskName));
        setChanged();
        notifyObservers(taskName);
    }

    /**
     * Load tasks from file
     */
    public void loadFromFile() {
        // Load the _tasks from file to the list
        try {
            File file = new File(this.getFilename());

            if(file.exists() && !file.isDirectory()) {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.contains(":completed")) {
                        String[] parts = line.split(":");
                        this.tasks.add(new Task(parts[0], true));
                    }
                    else {
                        this.tasks.add(new Task(line));
                    }
                }
                fileReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write the current list to file
     */
    private void writeListToFile() {
        // Write the tasks to the files, overwriting the old file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.getFilename()))) {
            Iterator<Task> listIterator = this.tasks.iterator();
            while (listIterator.hasNext()) {
                Task next = listIterator.next();
                if (next.getCompleted()) {
                    bw.write(next + ":completed\n");
                }
                else {
                    bw.write(next + "\n");
                }
            }

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
