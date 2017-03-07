package com.tasky.app;

import com.tasky.app.models.Task;
import com.tasky.util.Encrypt;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by markus on 2017-03-02.
 */
public class TaskHandler extends Observable {

    private App app;
    private List<String> tasks;

    public TaskHandler(App app) {
        this.app = app;
        this.tasks = new ArrayList<>();
    }

    /**
     * Get the filename to use based on the users username
     * @return String   Filename
     */
    private String getFilename() {
        return Encrypt.encrypt(this.app.getAuth().getUsername()) + ".txt";
    }

    public void addTask(String taskName) {
        // Add the task to the file, appending to previous tasks
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.getFilename(), true))) {

            String content = taskName + "\n";

            bw.write(content);

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.tasks.add(taskName);
        setChanged();
        notifyObservers(taskName);
    }

    public void loadFromFile() {
        // Load the tasks from file to the list
        try {
            File file = new File(this.getFilename());

            if(file.exists() && !file.isDirectory()) {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    this.tasks.add(line);
                }
                fileReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(int index) {
        // Remove element from list model
        this.tasks.remove(index);

        // Write the tasks to the files, overwriting the old file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.getFilename()))) {
            for (Object taskName : this.tasks) {
                bw.write((String)taskName + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        setChanged();
        notifyObservers();
    }

    public List getTasks() {
        return this.tasks;
    }

}
