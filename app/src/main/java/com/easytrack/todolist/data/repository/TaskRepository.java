package com.easytrack.todolist.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.easytrack.todolist.data.model.TaskModel;
import com.easytrack.todolist.interfaces.OnTaskResponseCallback;
import com.easytrack.todolist.network.ApiClient;
import com.easytrack.todolist.network.ApiInterface;
import com.easytrack.todolist.utilities.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskRepository {

    private static final String TAG = "TaskRepository";

    public LiveData<List<TaskModel>> callGetTasksApi() {
        final MutableLiveData<List<TaskModel>> data = new MutableLiveData<>();
        Call<List<TaskModel>> call = ApiClient.getClient().create(ApiInterface.class).getAllTasks();
        call.enqueue(new Callback<List<TaskModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<TaskModel>> call, @NonNull Response<List<TaskModel>> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
                else {
                    Log.e(TAG, Constants.MESSAGE_ERROR + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<TaskModel>> call, @NonNull Throwable t) {
                call.cancel();
                Log.e(TAG, Constants.MESSAGE_ERROR + t.getMessage());
            }
        });
        return data;
    }

    public void callTaskActionApi(int action, TaskModel task, OnTaskResponseCallback onTaskResponseCallback) {
        Call<TaskModel> call;
        final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        switch (action) {
            case Constants.ACTION_INSERT_TASK:
                call = apiInterface.insertNewTask(task);
                break;
            case Constants.ACTION_UPDATE_TASK:
                call = apiInterface.updateTask(task.getId(), task);
                break;
            default:
                call = apiInterface.deleteTask(task.getId());
                break;
        }

        call.enqueue(new Callback<TaskModel>() {
            @Override
            public void onResponse(@NonNull Call<TaskModel> call, @NonNull Response<TaskModel> response) {
                if (response.isSuccessful()) {
                    onTaskResponseCallback.onSuccess(response.body());
                } else {
                    onTaskResponseCallback.onError();
                    Log.e(TAG, Constants.MESSAGE_ERROR + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TaskModel> call, @NonNull Throwable t) {
                call.cancel();
                onTaskResponseCallback.onError();
                Log.e(TAG, Constants.MESSAGE_ERROR + t.getMessage());
            }
        });
    }
}
