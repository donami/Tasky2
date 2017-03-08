package com.tasky.app.models;

import java.util.Comparator;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by markus on 2017-02-28.
 */
public class Task implements Observer, Comparable<Task>{
    private String name;
    private Boolean completed;

    public Task(String name) {
        this.name = name;
        this.completed = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println(arg);
    }

    @Override
    public int compareTo(Task o) {
//        return 0;
        return this.getName().compareTo(o.getName());
    }

    public static Comparator<Task> TaskNameComparator = new Comparator<Task>() {

        public int compare(Task task1, Task task2) {
            System.out.println("hej");

            String taskName1 = task1.getName().toUpperCase();
            String taskName2 = task2.getName().toUpperCase();

            //ascending order
            return taskName1.compareTo(taskName2);

            //descending order
            //return taskName2.compareTo(taskName1);
        }

    };
}
