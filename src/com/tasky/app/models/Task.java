package com.tasky.app.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by markus on 2017-02-28.
 */
public class Task implements Comparable<Task>, Serializable {
    private String name;
    private Boolean completed;
    private Date dueDate;

    public Task(String name) {
        this.name = name;
        this.completed = false;
        this.dueDate = null;
    }

    public Task(String name, boolean completed) {
        this.name = name;
        this.completed = completed;
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

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDueDate() {
        return this.dueDate;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(Task o) {
        return this.getName().compareTo(o.getName());
    }
}
