package com.Aman.todolist.Model;

public class ToDoModel extends TaskID {

    private String task;
    private String due;  // Renamed for clarity
    private int status;  // Changed Status to lowercase

    // Default constructor required for Firestore
    public ToDoModel() {
    }

    // Constructor to initialize fields
    public ToDoModel(String task, String due, int status) {
        this.task = task;
        this.due = due;
        this.status = status;
    }

    // Getter and Setter for Task
    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    // Getter and Setter for Due Date
    public String getDueDate() {
        return due;
    }

    public void setDueDate(String dueDate) {
        this.due = dueDate;
    }

    // Getter and Setter for Status
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
