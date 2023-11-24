package com.easytrack.todolist.data.repository;

import android.app.Application;
import android.util.Log;

import com.easytrack.todolist.data.sharedPreferences.SharedPreferencesDataSource;
import com.easytrack.todolist.interfaces.OnLoginCallback;
import com.easytrack.todolist.utilities.Constants;

public class UserRepository {

    private static final String TAG = "UserRepository";

    private final String username;
    private final String password;
    private final Boolean isLogged;

    public UserRepository(Application application) {
        SharedPreferencesDataSource.getInstance().init(application);
        username = SharedPreferencesDataSource.getInstance().getUsername();
        password = SharedPreferencesDataSource.getInstance().getPassword();
        isLogged = SharedPreferencesDataSource.getInstance().getIsLogged();
    }

    public void onLogin(String username, String password, OnLoginCallback onLoginCallback) {
        try {
            setIsLogged(true);
            setUsername(username);
            setPassword(password);
            onLoginCallback.onSuccessLogin();
        } catch (Error error) {
            onLoginCallback.onError();
            Log.e(TAG, Constants.MESSAGE_ERROR + error.getMessage());
        }
    }

    public void onLogout() {
        setIsLogged(false);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        SharedPreferencesDataSource.getInstance().setUsername(username);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        SharedPreferencesDataSource.getInstance().setPassword(password);
    }

    public Boolean getIsLogged() {
        return isLogged;
    }

    public void setIsLogged(Boolean isLogged) {
        SharedPreferencesDataSource.getInstance().setIsLogged(isLogged);
    }
}
