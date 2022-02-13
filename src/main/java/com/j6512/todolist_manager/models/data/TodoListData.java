package com.j6512.todolist_manager.models.data;

import com.j6512.todolist_manager.models.TodoList;

import java.util.ArrayList;

public class TodoListData {

    public static ArrayList<TodoList> findByColumnAndValue(String column, String value, Iterable<TodoList> allTodoLists) {

        ArrayList<TodoList> results = new ArrayList<>();

        if (value.toLowerCase().equals("all")) {
            results = findByValue(value, allTodoLists);

            return results;
        }

        for (TodoList todoList : allTodoLists) {
            String aValue = getFieldValue(todoList, column);

            if (aValue != null && column.equals("list")) {
                if (aValue.contains(value.toLowerCase())) {
                    results.add(todoList);
                }
            } else if (aValue != null && aValue.toLowerCase().contains(value.toLowerCase())) {
                results.add(todoList);
            }
        }

        return results;
    }

    public static String getFieldValue(TodoList todoList, String fieldName) {

        String theValue = "";

        if (fieldName.equals("list")) {
            theValue = todoList.getName();
        }

        return theValue;
    }

    public static ArrayList<TodoList> findByValue(String value, Iterable<TodoList> allTodoLists) {

        String lower_val = value.toLowerCase();

        ArrayList<TodoList> results = new ArrayList<>();

        for (TodoList todoList : allTodoLists) {
            if (todoList.getName().toLowerCase().contains(lower_val)) {
                results.add(todoList);
            }
        }

        return results;
    }
}
