package com.easytrack.todolist.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.easytrack.todolist.MainActivity;
import com.easytrack.todolist.R;
import com.easytrack.todolist.databinding.ActivityLoginBinding;
import com.easytrack.todolist.interfaces.OnLoginCallback;
import com.easytrack.todolist.utilities.Methods;
import com.easytrack.todolist.viewModel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(view -> callLogin());

        setObservers();
    }

    private void setObservers() {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getUsername().observe(this, username -> binding.etLoginUsername.setText(username));
        loginViewModel.getPassword().observe(this, password -> binding.etLoginPassword.setText(password));
    }

    private void callLogin() {
        String username = binding.etLoginUsername.getText().toString();
        String password = binding.etLoginPassword.getText().toString();

        if (username.trim().equals("")) {
            Methods.createToast(getApplicationContext(), getResources().getString(R.string.login_validate_username));
        }
        else if(password.trim().equals("")) {
            Methods.createToast(getApplicationContext(), getResources().getString(R.string.login_validate_password));
        } else {
            loginViewModel.onLogin(username, password, new OnLoginCallback() {
                @Override
                public void onSuccessLogin() {
                    //Si el login fue exitoso, muestra la pantalla principal
                    Intent intentToMain = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intentToMain);
                    finish();
                }

                @Override
                public void onError() {
                    Methods.createToast(getApplicationContext(), getResources().getString(R.string.login_error));
                }
            });
        }
    }
}