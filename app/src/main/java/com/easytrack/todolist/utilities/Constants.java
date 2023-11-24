package com.easytrack.todolist.utilities;

public class Constants {

    // Network
    public static final String BASE_URL = "https://655d37679f1e1093c5991e4f.mockapi.io/api/v1/";
    public static final String API_GET_TASKS = "todo_items";
    public static final String API_INSERT_TASK = "todo_items";
    public static final String API_UPDATE_TASK = "todo_items/{id}";
    public static final String API_DELETE_TASK = "todo_items/{id}";

    // SharedPreferences
    public static final String PREFERENCES_FILENAME = "todoListPreferences";
    public static final String PREFERENCES_USERNAME_KEY = "usernameKey";
    public static final String PREFERENCES_PASSWORD_KEY = "passwordKey";
    public static final String PREFERENCES_IS_LOGGED_KEY = "isLoggedKey";

    //Messages
    public static final String MESSAGE_ERROR = "Error: ";

    //API Actions
    public static final int ACTION_INSERT_TASK = 1;
    public static final int ACTION_UPDATE_TASK = 2;
    public static final int ACTION_DELETE_TASK = 3;

    public static final int DELAY_WELCOME_MODAL = 1500;

}
