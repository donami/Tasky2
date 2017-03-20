package com.tasky.util;

import com.tasky.app.models.Task;

import java.util.Comparator;

/**
 * Created by markus on 2017-03-17.
 */
public class TaskComparator {

    public static Comparator<Task> byName(boolean ascending) {
        return (o1, o2) -> {
            if (ascending) {
                return o1.getName().compareTo(o2.getName());
            }
            return o2.getName().compareTo(o1.getName());
        };
    }

    public static Comparator<Task> byDueDate(boolean ascending) {
        return (o1, o2) -> {

            if (o1.getDueDate() == null || o2.getDueDate() == null) {
                return -1;
            }

            if (ascending) {
                return o2.getDueDate().compareTo(o1.getDueDate());
            }
            return o1.getDueDate().compareTo(o2.getDueDate());
        };
    }

    public static Comparator<Task> byCompleted(boolean ascending) {
        return (o1, o2) -> {
            if (ascending) {
                return o1.getCompleted().compareTo(o2.getCompleted());
            }
            return o2.getCompleted().compareTo(o1.getCompleted());
        };
    }

}
