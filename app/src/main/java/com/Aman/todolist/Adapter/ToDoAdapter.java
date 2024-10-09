package com.Aman.todolist.Adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Aman.todolist.MainActivity;
import com.Aman.todolist.Model.ToDoModel;
import com.Aman.todolist.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.myViewHolder> {
    private List<ToDoModel> toDoList;
    private MainActivity activity;
    private FirebaseFirestore firestore;

    public ToDoAdapter(MainActivity mainActivity , List<ToDoModel> toDoList){
        this.toDoList = toDoList;
        activity = mainActivity;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.each_task , parent, false);
        firestore = FirebaseFirestore.getInstance();
        return new myViewHolder(view);
    }

    public void deleteTask(int position){
        ToDoModel toDoModel = toDoList.get(position);
        firestore.collection("task").document(toDoModel.taskID).delete();
        toDoList.remove(position);
        notifyItemRemoved(position);
    }

    public void editTask(int position){
        ToDoModel toDoModel = toDoList.get(position);

        Bundle bundle = new Bundle();
        bundle.putString("task" , toDoModel.getTask());
        bundle.putString("due" ,  toDoModel.getDueDate());
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
       ToDoModel toDoModel = toDoList.get(position);
       holder.checkBox.setText(toDoModel.getTask());
       holder.dueDate.setText("Due on "+ toDoModel.getDueDate());

       holder.checkBox.setChecked(toBoolean(toDoModel.getStatus()));

       holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
               if (isChecked){
                  firestore.collection("tasks").document(toDoModel.taskID).update("status" , 1);
               }
           }
       });
    }

    private boolean toBoolean(int status){
        return status != 0;
    }

    @Override
    public int getItemCount() {
        return toDoList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView dueDate;
        CheckBox checkBox;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            dueDate = itemView.findViewById(R.id.task_due_date);
            checkBox = itemView.findViewById(R.id.taskCheckBox);
        }
    }
}
