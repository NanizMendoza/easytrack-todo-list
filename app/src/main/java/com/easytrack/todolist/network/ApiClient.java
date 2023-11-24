package com.easytrack.todolist.network;

import com.easytrack.todolist.utilities.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit RETROFIT = null;

    public static Retrofit getClient() {
        if (RETROFIT == null) {
            RETROFIT = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return RETROFIT;
    }
}