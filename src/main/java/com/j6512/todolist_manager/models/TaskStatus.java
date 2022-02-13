package com.j6512.todolist_manager.models;

public enum TaskStatus {

    INPROGRESS("In Progress"),
    COMPLETED("Completed"),
    ONHOLD("On Hold");

    private final String status;

    TaskStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
