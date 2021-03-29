package com.example.todolist;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.adapter.RecyclerViewAdapter;
import com.example.todolist.model.Priority;
import com.example.todolist.model.Task;
import com.example.todolist.model.TaskViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "ITEM";
    private TaskViewModel taskViewModel;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //setting up recyclerView to display data
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //object of taskViewModel
        taskViewModel = new ViewModelProvider.AndroidViewModelFactory(
                MainActivity.this.getApplication())
                .create(TaskViewModel.class);

        //retrieve data from task table
        taskViewModel.getAllTasks().observe(this, tasks -> {
            //setting up recyclerView Adapter
            recyclerViewAdapter = new RecyclerViewAdapter(tasks);
          recyclerView.setAdapter(recyclerViewAdapter);
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            //object of task class
           Task task = new Task("Task", Priority.MEDIUM, Calendar.getInstance().getTime(), Calendar.getInstance().getTime(),false);
           TaskViewModel.insert(task);
            //showBottomSheetDialogue();
        });
        
    }

}