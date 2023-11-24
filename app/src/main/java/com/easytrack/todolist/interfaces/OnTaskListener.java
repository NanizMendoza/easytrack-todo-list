package com.easytrack.todolist.interfaces;

public interface OnTaskListener {
    void onTaskClickListener(int position);
    void onTaskCheckboxClickListener(int position, boolean checked);
}
