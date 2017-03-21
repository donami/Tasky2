package com.tasky.app.models;

import java.io.Serializable;

/**
 * Created by markus on 2017-03-20.
 */
public class Category implements Comparable<Category>, Serializable {
    private String title;

    private Category() {
        this.title = null;
    }

    public Category(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int compareTo(Category category) {
        return this.getTitle().compareTo(category.getTitle());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (!Category.class.isAssignableFrom(o.getClass()))
            return false;

        final Category other = (Category) o;

        return this.title.equals(other.getTitle());
    }

    @Override
    public String toString() {
        return this.title;
    }
}
