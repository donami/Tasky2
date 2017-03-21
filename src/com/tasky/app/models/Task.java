package com.tasky.app.models;

import com.tasky.util.TaskComparator;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by markus on 2017-02-28.
 */
public class Task extends TaskComparator implements Comparable<Task>, Serializable {
    private String name;
    private Boolean completed;
    private Date dueDate;
    private Category category;

    public Task(String name) {
        this.name = name;
        this.completed = false;
        this.dueDate = null;
        this.category = null;
    }

    public Task(String name, boolean completed) {
        this.name = name;
        this.completed = completed;
        this.category = null;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
