package com.j6512.todolist_manager.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginFormDTO {

    @NotNull
    @NotBlank
    @Size(min = 3, max = 12, message = "username must be between 3 and 12 characters")
    private String username;

    @NotNull
    @NotBlank
    @Size(min = 6, max = 20, message = "password must be between 6 and 20 characters")
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username.replaceAll("\\s", "");
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
