package com.j6512.todolist_manager.models.data;

import com.j6512.todolist_manager.models.Task;

import java.util.ArrayList;

public class TaskData {
    public static ArrayList<Task> findByColumnAndValue(String column, String value, Iterable<Task> allTasks) {

        ArrayList<Task> results = new ArrayList<>();

        if (value.toLowerCase().equals("all")) {
            results = findByValue(value, allTasks);

            return results;
        }

        for (Task task : allTasks) {

            String aValue = getFieldValue(task, column);

            if (aValue != null && column.equals("list")) {

                if (aValue.contains(value.toLowerCase())) {
                    results.add(task);
                }
            } else if (aValue != null && aValue.toLowerCase().contains(value.toLowerCase())) {
                results.add(task);
            }
        }

        return results;
    }

    public static String getFieldValue(Task task, String fieldName) {

        String theValue = "";

        if (fieldName.equals("task")) {
            theValue = task.getName();
        }

        return theValue;
    }

    public static ArrayList<Task> findByValue(String value, Iterable<Task> allTasks) {
        String lower_val = value.toLowerCase();

        ArrayList<Task> results = new ArrayList<>();

        for (Task task : allTasks) {
            if (task.getName().toLowerCase().contains(lower_val)) {
                results.add(task);
            }
        }

        return results;
    }
}
