package com.tasky.app.models;

import java.io.Serializable;

/**
 * Created by markus on 2017-03-20.
 */
public class Category implements Comparable<Category>, Serializable {
    private String title;
    private String identifier;

    public Category() {
        this.title = null;
        this.identifier = null;
    }

    public Category(String title, String identifier) {
        this.title = title;
        this.identifier = identifier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public int compareTo(Category category) {
        return this.getTitle().compareTo(category.getTitle());
    }

    @Override
    public String toString() {
        return this.title;
    }
}
