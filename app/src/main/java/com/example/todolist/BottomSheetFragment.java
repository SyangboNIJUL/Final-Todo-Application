package com.example.todolist;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;

import com.example.todolist.model.Priority;
import com.example.todolist.model.Task;
import com.example.todolist.model.TaskViewModel;
import com.example.todolist.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;

import java.util.Calendar;
import java.util.Date;

public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener{
    private EditText enterTodo;
    private ImageButton calendarButton;
    private ImageButton priorityButton;
    private RadioButton selectedRadioButton;
    private int selectedButtonId;
    private ImageButton saveButton;
    private CalendarView calendarView;
    private Group calendarGroup;
    private Date dueDate;
    Calendar calendar = Calendar.getInstance();
//    private SharedViewModel sharedViewModel;
    private boolean isEdit;

    //empty constructor
    public BottomSheetFragment() {

    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ){
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        calendarGroup = view.findViewById(R.id.calendar_group);
        calendarView = view.findViewById(R.id.calendar_view);
        calendarButton = view.findViewById(R.id.today_calendar_button);
        enterTodo = view.findViewById(R.id.enter_todo_et);
        saveButton = view.findViewById(R.id.save_todo_button);

        Chip todayChip = view.findViewById(R.id.today_chip);
        todayChip.setOnClickListener(this);
        Chip tomorrowChip = view.findViewById(R.id.tomorrow_chip);
        tomorrowChip.setOnClickListener(this);
        Chip nextWeekChip = view.findViewById(R.id.next_week_chip);
        nextWeekChip.setOnClickListener(this);


        return view;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //fetching information for fragments View

        //checking the visibility of calender
        calendarButton.setOnClickListener(view12 -> {
            calendarGroup.setVisibility(
                    calendarGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE
            );
            Utils.hideSoftKeyboard(view12);
        });


        calendarView.setOnDateChangeListener((calendarView, year, month, dayOfMonth) -> {
            calendar.clear();
            calendar.set(year, month, dayOfMonth);
            dueDate = calendar.getTime();
            // Log.d("cal", "onViewCreated: ====>month" + (month+1) +",dayOfMonth "+dayOfMonth);

        });



        //fetching information from view
        saveButton.setOnClickListener(v -> {
            String task = enterTodo.getText().toString().trim();
            if (!TextUtils.isEmpty(task) && dueDate != null) {
                Task myTask = new Task(task, Priority.HIGH,
                        dueDate, Calendar.getInstance().getTime(), false);
                TaskViewModel.insert(myTask);
            }
        });


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.today_chip) {
            //set date for today
            calendar.add(Calendar.DAY_OF_YEAR, 0);
            dueDate = calendar.getTime();
            Log.d("Time", "onClick: " + dueDate.toString());
        } else if (id == R.id.tomorrow_chip) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            dueDate = calendar.getTime();
            Log.d("Time", "onClick: " + dueDate.toString());
        } else if (id == R.id.next_week_chip) {
            calendar.add(Calendar.DAY_OF_YEAR, 7);
            dueDate = calendar.getTime();
            Log.d("Time", "onClick: " + dueDate.toString());
        }

    }
}