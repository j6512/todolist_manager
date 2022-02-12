package com.j6512.todolist_manager.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Task extends AbstractEntity {

    @NotBlank
    @Size(min = 1, max = 25, message = "must be between 1 and 25 characters")
    private String name;

    @NotBlank
    @Size(min = 1, max = 100, message = "must be between 1 and 100 characters")
    private String description;

    @ManyToOne
    private TodoList todoList;

    public Task() {

    }

    public Task(String name, String description, TodoList todoList) {
        this.name = name;
        this.description = description;
        this.todoList = todoList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TodoList getTodoList() {
        return todoList;
    }

    public void setTodoList(TodoList todoList) {
        this.todoList = todoList;
    }
}
