package com.easytrack.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;

import com.easytrack.todolist.adapter.TaskAdapter;
import com.easytrack.todolist.data.model.TaskModel;
import com.easytrack.todolist.databinding.ActivityMainBinding;
import com.easytrack.todolist.databinding.AlertDialogWelcomeBinding;
import com.easytrack.todolist.databinding.BottomSheetTaskBinding;
import com.easytrack.todolist.databinding.NavHeaderBinding;
import com.easytrack.todolist.interfaces.OnTaskListener;
import com.easytrack.todolist.interfaces.OnTaskResponseCallback;
import com.easytrack.todolist.ui.LoginActivity;
import com.easytrack.todolist.utilities.Constants;
import com.easytrack.todolist.utilities.Methods;
import com.easytrack.todolist.viewModel.MainViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private int selectedAction;
    private TaskModel selectedTask;
    private TaskAdapter taskAdapter;
    private int selectedTaskPosition;
    private List<TaskModel> taskList;
    private MainViewModel mainViewModel;
    private ActivityMainBinding binding;
    private ActionBarDrawerToggle drawerToggle;
    private BottomSheetDialog bottomSheetDialog;
    private BottomSheetTaskBinding bottomSheetBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeComponents();
        initializeNavigation();
        initializeBottomSheetDialog();

        setObservers();
    }

    private void initializeComponents() {
        //Inicializa lista de tareas
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(AppCompatResources.getDrawable(this, R.drawable.custom_list_divider)));
        binding.appBarMain.listTask.rvListTask.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        binding.appBarMain.listTask.rvListTask.addItemDecoration(itemDecoration);

        //Inicializa botón flotante que despliega diálogo inferior para crear tarea
        binding.appBarMain.fabAddTask.setOnClickListener(v -> {
            selectedTask = null;
            showBottomSheetDialog();
        });
    }

    private void initializeNavigation() {
        //Inicializa actionBar customizada
        setSupportActionBar(binding.appBarMain.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //Inicializa barra de navegación lateral
        drawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        binding.navView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.logout) {
                mainViewModel.onLogout(); //Llamada a función en viewModel para cerrar sesión
            }
            return true;
        });
    }

    private void initializeBottomSheetDialog() {
        //Inicializa diálogo inferior
        bottomSheetBinding = BottomSheetTaskBinding.inflate(getLayoutInflater());
        bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetTheme);
        bottomSheetDialog.setContentView(bottomSheetBinding.getRoot());

        bottomSheetBinding.ibCloseBottomSheet.setOnClickListener(v -> bottomSheetDialog.dismiss());

        bottomSheetBinding.btnAddTask.setOnClickListener(view -> {
            if (validateTaskName()) {
                selectedAction = Constants.ACTION_INSERT_TASK;
                selectedTask = new TaskModel(bottomSheetBinding.etTaskName.getText().toString(), bottomSheetBinding.etTaskDescription.getText().toString());
                callTaskAction();
            }
        });

        bottomSheetBinding.btnUpdateTask.setOnClickListener(view -> {
            if (validateTaskName()) {
                selectedAction = Constants.ACTION_UPDATE_TASK;
                selectedTask.setName(bottomSheetBinding.etTaskName.getText().toString());
                selectedTask.setDescription(bottomSheetBinding.etTaskDescription.getText().toString());
                callTaskAction();
            }
        });

        bottomSheetBinding.btnDeleteTask.setOnClickListener(view -> {
            selectedAction = Constants.ACTION_DELETE_TASK;
            callTaskAction();
        });
    }

    private void setObservers() {
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getAllTasks().observe(this, taskList -> {
            this.taskList = taskList;
            populateTaskList();
            binding.appBarMain.rlProgressBar.setVisibility(View.GONE);
        });
        mainViewModel.getIsLogged().observe(this, isLogged -> {
            if (!isLogged) {
                //Si el usuario no ha iniciado sesión, muestra pantalla de Login
                Intent intentToLogin = new Intent(MainActivity.this, LoginActivity.class);
                intentToLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentToLogin);
            }
            else {
                //Si hay sesión iniciada, muestra mensaje de bienvenida y nombre de usuario en barra lateral
                mainViewModel.getUsername().observe(this, username -> {
                    if(username != null && !username.equals("")) {
                        NavHeaderBinding navViewHeaderBinding = NavHeaderBinding.bind(binding.navView.getHeaderView(0));
                        navViewHeaderBinding.tvNavUsername.setText(username);
                        showWelcomeDialog(username);
                    }
                });
            }
        });
    }

    public void showWelcomeDialog(String username) {
        //Crea diálogo de bienvenida
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialogWelcomeBinding alertBinding = AlertDialogWelcomeBinding.inflate(getLayoutInflater());
        alertBinding.tvAlertUsername.setText(username);
        builder.setView(alertBinding.getRoot());

        //Agrega animación a diálogo
        final AlertDialog dialog = builder.create();
        if (dialog.getWindow() != null)
            dialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
        dialog.show();

        //Cierra diálogo después de (Constants.DELAY_WELCOME_MODAL) segundos
        new Handler().postDelayed(dialog::dismiss, Constants.DELAY_WELCOME_MODAL);
    }

    public void populateTaskList() {
        taskAdapter = new TaskAdapter(taskList, new OnTaskListener() {
            @Override
            public void onTaskClickListener(int position) {
                //Muestra diálogo inferior al seleccionar una tarea de la lista
                selectedTask = taskList.get(position);
                selectedTaskPosition = position;
                showBottomSheetDialog();
            }

            @Override
            public void onTaskCheckboxClickListener(int position, boolean isChecked) {
                //Actualiza el status completado de tarea al marcar/desmarcar checkbox en lista
                selectedTask = taskList.get(position);
                selectedTaskPosition = position;
                selectedTask.setCompleted(isChecked);
                selectedAction = Constants.ACTION_UPDATE_TASK;
                callTaskAction();
            }
        });
        binding.appBarMain.listTask.rvListTask.setAdapter(taskAdapter);
        binding.appBarMain.listTask.rvListTask.setVisibility(View.VISIBLE);
    }

    //Llamada a función en viewModel que agrega/actualiza/elimina tareas
    private void callTaskAction() {
        mainViewModel.callTaskAction(selectedAction, selectedTask, new OnTaskResponseCallback() {
            @Override
            public void onSuccess(TaskModel task) {
                String successMsg = "";
                switch (selectedAction) {
                    case Constants.ACTION_INSERT_TASK:
                        taskList.add(task);
                        taskAdapter.notifyItemInserted(taskList.size()); //Notifica a la lista que se agregó un registro
                        successMsg = getResources().getString(R.string.add_task_success);
                        break;
                    case Constants.ACTION_UPDATE_TASK:
                        taskAdapter.notifyItemChanged(selectedTaskPosition); //Notifica a la lista que se actualizó un registro
                        successMsg = getResources().getString(R.string.update_task_success);
                        break;
                    case Constants.ACTION_DELETE_TASK:
                        taskList.remove(selectedTaskPosition);
                        taskAdapter.notifyItemRemoved(selectedTaskPosition); //Notifica a la lista que se agregó un registro
                        taskAdapter.notifyItemRangeChanged(selectedTaskPosition, taskList.size()); //Actualiza posición de items en lista
                        successMsg = getResources().getString(R.string.delete_task_success);
                        break;
                }
                dismissBottomSheetDialog();
                Methods.createToast(getApplicationContext(), successMsg);
            }

            @Override
            public void onError() {
                String errorMsg = "";
                switch (selectedAction) {
                    case Constants.ACTION_INSERT_TASK:
                        errorMsg = getResources().getString(R.string.add_task_error);
                        break;
                    case Constants.ACTION_UPDATE_TASK:
                        errorMsg = getResources().getString(R.string.update_task_error);
                        break;
                    case Constants.ACTION_DELETE_TASK:
                        errorMsg = getResources().getString(R.string.delete_task_error);
                        break;
                }
                Methods.createToast(getApplicationContext(), errorMsg);
            }
        });
    }

    private void showBottomSheetDialog() {
        boolean isSelectedTask = selectedTask != null;

        bottomSheetBinding.etTaskName.setText(isSelectedTask ? selectedTask.getName() : "");
        bottomSheetBinding.btnAddTask.setVisibility(isSelectedTask ? View.GONE : View.VISIBLE);
        bottomSheetBinding.btnUpdateTask.setVisibility(isSelectedTask ? View.VISIBLE : View.GONE);
        bottomSheetBinding.btnDeleteTask.setVisibility(isSelectedTask ? View.VISIBLE : View.GONE);
        bottomSheetBinding.etTaskDescription.setText(isSelectedTask ? selectedTask.getDescription() : "");
        bottomSheetBinding.tvBottomSheetTitle.setText(isSelectedTask ? R.string.update_task : R.string.add_task);

        bottomSheetDialog.show();
    }

    private void dismissBottomSheetDialog() {
        if(bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        }
    }

    private boolean validateTaskName() {
        //Valida que se haya ingresado un nombre al agregar o editar tarea
        if(bottomSheetBinding.etTaskName.getText().toString().trim().equals("")) {
            Methods.createToast(getApplicationContext(), getResources().getString(R.string.task_name_error));
            return false;
        }
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}