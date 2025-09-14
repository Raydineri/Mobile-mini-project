package tn.rnu.isetr.tp.Fragments;

import static java.security.AccessController.getContext;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import tn.rnu.isetr.tp.Adapters.TaskAdapter;
import tn.rnu.isetr.tp.Database.DatabaseManager;
import tn.rnu.isetr.tp.Entity.Tasks;
import tn.rnu.isetr.tp.R;

public class TaskFragment extends Fragment {
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<Tasks> taskList;
    private DatabaseManager databaseManager;

    public TaskFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        recyclerView = view.findViewById(R.id.taskRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize FloatingActionButton
        FloatingActionButton addTaskFab = view.findViewById(R.id.addTaskFab);
        addTaskFab.setOnClickListener(v -> openAddTaskDialog());
        databaseManager = new DatabaseManager(getContext());

        // Initialize task list and adapter
        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(taskAdapter);


        loadTasks();
        taskAdapter = new TaskAdapter(taskList, new TaskAdapter.OnTaskActionListener() {
            @Override
            public void onEditTask(Tasks task) {
                // Open edit task dialog
                openEditTaskDialog(task);
            }

            @Override
            public void onDeleteTask(Tasks task) {
                // Delete task from database
                databaseManager.deleteTask(task.getId());
                taskList.remove(task);
                taskAdapter.notifyDataSetChanged();
                // Refresh the task list
                loadTasks();
            }
            @Override
            public void onCompleteTask(Tasks task) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Complete Task")
                        .setMessage("Are you sure you want to mark this task as completed?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            // Mark the task as completed
                            task.setStatus("Completed");
                            // Update the database or list and notify the adapter
                            databaseManager.updateTask(task.getId(), task.getTitle(), task.getDescription(), task.getDate(), Integer.parseInt(task.getPriority()), task.getStatus());
                            taskAdapter.notifyDataSetChanged();
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
        recyclerView.setAdapter(taskAdapter);


        return view;
    }

    private void loadTasks() {
        taskList.clear();
        Cursor cursor = databaseManager.getAllTasks();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String dueDate = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String priority = String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("priority")));
                String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));

                taskList.add(new Tasks(id, title, description, dueDate, priority, status));
            } while (cursor.moveToNext());
            cursor.close();
        }

        taskAdapter.notifyDataSetChanged();
    }

    private void openAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_task, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // Initialize dialog inputs
        EditText taskTitleInput = dialogView.findViewById(R.id.taskTitleInput);
        EditText taskDescriptionInput = dialogView.findViewById(R.id.taskDescriptionInput);
        EditText taskDueDateInput = dialogView.findViewById(R.id.taskDueDateInput);
        EditText taskPriorityInput = dialogView.findViewById(R.id.taskPriorityInput);
        Button addTaskButton = dialogView.findViewById(R.id.addTaskButton);

        addTaskButton.setOnClickListener(v -> {
            String title = taskTitleInput.getText().toString().trim();
            String description = taskDescriptionInput.getText().toString().trim();
            String dueDate = taskDueDateInput.getText().toString().trim();
            int priority = Integer.parseInt(taskPriorityInput.getText().toString().trim());
            String status = "Pending";

            // Save to database
            databaseManager.addTask(title, description, dueDate, priority, status);

            // Refresh the task list
            loadTasks();
            dialog.dismiss();
        });

        dialog.show();
    }

    private void openEditTaskDialog(Tasks task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_task, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // Initialize dialog inputs with existing task data
        EditText taskTitleInput = dialogView.findViewById(R.id.taskTitleInput);
        EditText taskDescriptionInput = dialogView.findViewById(R.id.taskDescriptionInput);
        EditText taskDueDateInput = dialogView.findViewById(R.id.taskDueDateInput);
        EditText taskPriorityInput = dialogView.findViewById(R.id.taskPriorityInput);
        Button addTaskButton = dialogView.findViewById(R.id.addTaskButton);

        taskTitleInput.setText(task.getTitle());
        taskDescriptionInput.setText(task.getDescription());
        taskDueDateInput.setText(task.getDate());
        taskPriorityInput.setText(String.valueOf(task.getPriority()));
        addTaskButton.setText("Update Task");

        addTaskButton.setOnClickListener(v -> {
            task.setTitle(taskTitleInput.getText().toString().trim());
            task.setDescription(taskDescriptionInput.getText().toString().trim());
            task.setDate(taskDueDateInput.getText().toString().trim());
            task.setPriority(taskPriorityInput.getText().toString().trim());

            databaseManager.updateTask(task.getId(), task.getTitle(), task.getDescription(), task.getDate(), Integer.parseInt(task.getPriority()), task.getStatus());
            loadTasks();
            dialog.dismiss();
        });

        dialog.show();
    }
}

