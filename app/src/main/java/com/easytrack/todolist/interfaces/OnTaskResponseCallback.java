package com.easytrack.todolist.interfaces;

import com.easytrack.todolist.data.model.TaskModel;

public interface OnTaskResponseCallback {
    void onSuccess(TaskModel task);
    void onError();
}
