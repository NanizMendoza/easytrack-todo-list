package com.easytrack.todolist.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.easytrack.todolist.data.model.TaskModel;
import com.easytrack.todolist.databinding.ItemTaskBinding;
import com.easytrack.todolist.interfaces.OnTaskListener;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final List<TaskModel> taskList;
    private final OnTaskListener onTaskListener;

    public TaskAdapter(List<TaskModel> taskList, OnTaskListener onTaskListener) {
        this.taskList = taskList;
        this.onTaskListener = onTaskListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final ItemTaskBinding binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.getContext()));

        return new TaskViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        final TaskModel task = taskList.get(position);
        final boolean hasDescription = !task.getDescription().equals("");

        holder.binding.tvTaskName.setText(task.getName());
        holder.binding.cbTaskCompleted.setChecked(task.getCompleted());

        holder.binding.cvTask.setOnClickListener(view -> onTaskListener.onTaskClickListener(holder.getAdapterPosition()));
        holder.binding.cbTaskCompleted.setOnCheckedChangeListener((view, isChecked) -> onTaskListener.onTaskCheckboxClickListener(holder.getAdapterPosition(), isChecked));

        holder.binding.tvTaskDescription.setText(hasDescription ? task.getDescription() : "");
        holder.binding.tvTaskDescription.setVisibility(hasDescription ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        private final ItemTaskBinding binding;

        public TaskViewHolder(@NonNull ItemTaskBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
