package com.easytrack.todolist.network;

import com.easytrack.todolist.data.model.TaskModel;
import com.easytrack.todolist.utilities.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET(Constants.API_GET_TASKS)
    Call<List<TaskModel>> getAllTasks();

    @POST(Constants.API_INSERT_TASK)
    Call<TaskModel> insertNewTask(@Body TaskModel task);

    @PUT(Constants.API_UPDATE_TASK)
    Call<TaskModel> updateTask(@Path("id") int taskId, @Body TaskModel task);

    @DELETE(Constants.API_DELETE_TASK)
    Call<TaskModel> deleteTask(@Path("id") int taskId);
}
