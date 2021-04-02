package com.example.todolist.adapter;

import com.example.todolist.model.Task;

public interface OnTodoClickListener {
    void onTodoClick(Task task);
    // methods without body

    void onTodoRadioButtonClick(Task task);
}
