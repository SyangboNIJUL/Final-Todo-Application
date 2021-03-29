package com.example.todolist;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.adapter.RecyclerViewAdapter;
import com.example.todolist.model.TaskViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "ITEM";
    private TaskViewModel taskViewModel;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    BottomSheetFragment bottomSheetFragment;
    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        counter = 0;

        //initializing the bottomSheet Fragment
        bottomSheetFragment = new BottomSheetFragment();
        ConstraintLayout constraintLayout = findViewById(R.id.bottomSheet);
        BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior = BottomSheetBehavior.from(constraintLayout);
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);

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
//           Task task = new Task("Task" + counter++ , Priority.MEDIUM, Calendar.getInstance().getTime(), Calendar.getInstance().getTime(),false);
//           TaskViewModel.insert(task);
            //showing buttonSheet while clicking fab button
            showBottomSheetDialogue();
        });
        
    }

    private void showBottomSheetDialogue() {
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());

    }

}