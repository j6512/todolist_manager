package com.j6512.todolist_manager.models;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@Builder
public class TodoList extends AbstractEntity {

    @NotBlank
    @Size(min = 1, max = 20, message = "name for list must be between 1 and 20 characters")
    private String name;

    private Date dateCreated;
    private String formattedDate;

    @ManyToOne
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "todoList")
    private List<Task> tasks = new ArrayList<>();

    public TodoList() {}

    public TodoList(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormattedDate() {
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yy hh:mm a");
        this.formattedDate = dateFormat.format(this.dateCreated);

        return this.formattedDate;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
