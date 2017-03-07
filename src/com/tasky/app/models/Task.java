package com.tasky.app.models;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by markus on 2017-02-28.
 */
public class Task implements Observer{
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
}
