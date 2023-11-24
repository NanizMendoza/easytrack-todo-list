package com.easytrack.todolist.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.easytrack.todolist.data.model.TaskModel;
import com.easytrack.todolist.data.repository.TaskRepository;
import com.easytrack.todolist.data.repository.UserRepository;
import com.easytrack.todolist.interfaces.OnTaskResponseCallback;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final LiveData<List<TaskModel>> taskList;
    private final MutableLiveData<String> username = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLogged = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        taskRepository = new TaskRepository();
        taskList = taskRepository.callGetTasksApi();
        userRepository = new UserRepository(application);
        username.postValue(userRepository.getUsername());
        isLogged.postValue(userRepository.getIsLogged());
    }

    public LiveData<List<TaskModel>> getAllTasks() {
        return taskList;
    }

    public void callTaskAction(int action, TaskModel task, OnTaskResponseCallback onTaskResponseCallback) {
        taskRepository.callTaskActionApi(action, task, new OnTaskResponseCallback() {
            @Override
            public void onSuccess(TaskModel task) {
                onTaskResponseCallback.onSuccess(task);
            }

            @Override
            public void onError() {
                onTaskResponseCallback.onError();
            }
        });
    }

    public void onLogout() {
        isLogged.postValue(false);
        userRepository.onLogout();
    }

    public MutableLiveData<Boolean> getIsLogged() {
        return isLogged;
    }

    public MutableLiveData<String> getUsername() {
        return username;
    }
}
