package com.j6512.todolist_manager.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@Builder
public class User extends AbstractEntity {

    @NotNull
    private String username;

    @NotNull
    private String passwordHash;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<TodoList> todoLists = new ArrayList<>();

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.passwordHash = encoder.encode(password);
    }

//    public User(String username, String password, List<TodoList> todoLists) {
//        this.username = username;
//        this.passwordHash = password;
//        this.todoLists = todoLists;
//    }

    public String getUsername() {
        return this.username;
    }



    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, passwordHash);
    }

    public List<TodoList> getTodoLists() {
        return todoLists;
    }

    public void setTodoLists(List<TodoList> todoLists) {
        this.todoLists = todoLists;
    }
}
