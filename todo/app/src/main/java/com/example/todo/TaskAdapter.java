package com.example.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.BaseAdapter;
import java.util.ArrayList;

public class TaskAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Task> tasks;
    private boolean isCompletedList;

    public TaskAdapter(Context context, ArrayList<Task> tasks, boolean isCompletedList) {
        this.context = context;
        this.tasks = tasks;
        this.isCompletedList = isCompletedList;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_task, parent, false);
        }

        Task task = tasks.get(position);
        CheckBox checkBox = convertView.findViewById(R.id.taskCheckBox);
        TextView textView = convertView.findViewById(R.id.taskText);

        textView.setText(task.getName());
        checkBox.setChecked(task.isCompleted());

        // Disable checkbox in Completed list (so users can't move it back)
        if (isCompletedList) {
            checkBox.setEnabled(false);
        } else {
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    task.setCompleted(true);
                    ((MainActivity) context).moveTaskToCompleted(task);
                }
            });
        }

        return convertView;
    }
}
