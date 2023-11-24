package com.easytrack.todolist.utilities;

import android.content.Context;
import android.widget.Toast;

public class Methods {

    public static void createToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
