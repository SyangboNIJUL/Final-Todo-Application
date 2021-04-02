package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.adapter.OnTodoClickListener;
import com.example.todolist.adapter.RecyclerViewAdapter;
import com.example.todolist.model.SharedViewModel;
import com.example.todolist.model.Task;
import com.example.todolist.model.TaskViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements OnTodoClickListener {
    private static final String TAG = "ITEM";
    private TaskViewModel taskViewModel;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    BottomSheetFragment bottomSheetFragment;
    private SharedViewModel sharedViewModel;
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

        //instance of sharedViewModel
        sharedViewModel = new ViewModelProvider(this)
                .get(SharedViewModel.class);

        //retrieve data from task table
        taskViewModel.getAllTasks().observe(this, tasks -> {
            //setting up recyclerView Adapter
            recyclerViewAdapter = new RecyclerViewAdapter(tasks, this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item1:
                Intent intent = new Intent(MainActivity.this,Login.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "You have been logged out", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void showBottomSheetDialogue() {
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());

    }


    @Override
    public void onTodoClick(Task task) {
        sharedViewModel.selectItem(task);
        sharedViewModel.setIsEdit(true);
        showBottomSheetDialogue();
        Log.d("Click", "onTodoClick: " + task.getTask());

    }

    @Override
    public void onTodoRadioButtonClick(Task task) {
        Log.d("Click", "onRadioButton: " + task.getTask());
        //delete task by clicking radiobutton
        TaskViewModel.delete(task);
        recyclerViewAdapter.notifyDataSetChanged();
    }


}