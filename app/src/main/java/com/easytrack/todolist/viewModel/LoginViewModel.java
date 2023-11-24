package com.easytrack.todolist.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.easytrack.todolist.data.repository.UserRepository;
import com.easytrack.todolist.interfaces.OnLoginCallback;

public class LoginViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    private final MutableLiveData<String> username = new MutableLiveData<>();
    private final MutableLiveData<String> password = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLogged = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        isLogged.postValue(userRepository.getIsLogged());
        username.postValue(userRepository.getUsername());
        password.postValue(userRepository.getPassword());
    }

    public void onLogin(String newUsername, String newPassword, OnLoginCallback onLoginCallback) {
        userRepository.onLogin(newUsername, newPassword, new OnLoginCallback() {
            @Override
            public void onSuccessLogin() {
                isLogged.postValue(true);
                username.postValue(newUsername);
                password.postValue(newPassword);
                onLoginCallback.onSuccessLogin();
            }
            @Override
            public void onError() {
                onLoginCallback.onError();
            }
        });
    }

    public MutableLiveData<String> getUsername() {
        return username;
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }

}
