package com.easytrack.todolist.data.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.easytrack.todolist.utilities.Constants;

public class SharedPreferencesDataSource {

    private static SharedPreferencesDataSource INSTANCE;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;

    public static SharedPreferencesDataSource getInstance() {
        if (INSTANCE == null)
            INSTANCE = new SharedPreferencesDataSource();
        return INSTANCE;
    }

    public void init(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES_FILENAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String getUsername() {
        return sharedPreferences.getString(Constants.PREFERENCES_USERNAME_KEY, null);
    }

    public void setUsername(String username) {
        editor.putString(Constants.PREFERENCES_USERNAME_KEY, username);
        editor.commit();
    }

    public String getPassword() {
        return sharedPreferences.getString(Constants.PREFERENCES_PASSWORD_KEY, null);
    }

    public void setPassword(String password) {
        editor.putString(Constants.PREFERENCES_PASSWORD_KEY, password);
        editor.commit();
    }

    public Boolean getIsLogged() {
        return sharedPreferences.getBoolean(Constants.PREFERENCES_IS_LOGGED_KEY, false);
    }

    public void setIsLogged(Boolean isLogged) {
        editor.putBoolean(Constants.PREFERENCES_IS_LOGGED_KEY, isLogged);
        editor.commit();
    }
}
