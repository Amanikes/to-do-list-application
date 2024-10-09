package com.Aman.todolist.Model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class TaskID {
    @Exclude
    public String taskID;

    public <T extends TaskID> T withID(@NonNull final String id){
        this.taskID = id;
        return (T) this;
    }

}
