package com.example.todo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Task> todoList;
    private ArrayList<Task> completedList;
    private TaskAdapter todoAdapter;
    private TaskAdapter completedAdapter;
    private EditText taskInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize task lists
        todoList = new ArrayList<>();
        completedList = new ArrayList<>();

        // Find UI elements
        taskInput = findViewById(R.id.taskInput);
        Button addTaskButton = findViewById(R.id.addTaskButton);
        ListView todoListView = findViewById(R.id.todoListView);
        ListView completedListView = findViewById(R.id.completedListView);

        // Set up adapters
        todoAdapter = new TaskAdapter(this, todoList, false);
        completedAdapter = new TaskAdapter(this, completedList, true);

        todoListView.setAdapter(todoAdapter);
        completedListView.setAdapter(completedAdapter);

        // Add task button click event
        addTaskButton.setOnClickListener(v -> {
            String taskName = taskInput.getText().toString().trim();
            if (!taskName.isEmpty()) {
                todoList.add(new Task(taskName));
                taskInput.setText(""); // Clear input field
                todoAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(MainActivity.this, "Enter a task!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Move task to completed list only (not back to To-Do)
    public void moveTaskToCompleted(Task task) {
        todoList.remove(task);
        completedList.add(task);
        todoAdapter.notifyDataSetChanged();
        completedAdapter.notifyDataSetChanged();
    }
}
