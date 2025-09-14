package tn.rnu.isetr.tp.Adapters;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tn.rnu.isetr.tp.Entity.Tasks;
import tn.rnu.isetr.tp.R;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Tasks> taskList;
    private OnTaskActionListener listener;

    public interface OnTaskActionListener {
        void onEditTask(Tasks task);
        void onDeleteTask(Tasks task);
        void onCompleteTask(Tasks task); // New method

    }

    public TaskAdapter(List<Tasks> taskList, OnTaskActionListener listener) {
        this.taskList = taskList;
        this.listener = listener;
    }
    public TaskAdapter(List<Tasks> taskList) {
        this.taskList = taskList;

    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Tasks task = taskList.get(position);
        if(holder.taskStatus.getText().equals("Pending")){
            holder.markAsComplete.setVisibility(View.GONE);
        }
        holder.taskTitle.setText(task.getTitle());
        holder.taskDueDate.setText(task.getDate());
        holder.taskStatus.setText(task.getStatus());
        holder.itemView.setOnLongClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(holder.itemView.getContext(),v);
            popupMenu.inflate(R.menu.task_item_menu);
            popupMenu.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.edit_task) {
                    listener.onEditTask(task);
                    return true;
                } else if (id == R.id.delete_task) {
                    listener.onDeleteTask(task);
                    return true;
                } else {
                    return false;
                }
            });
            popupMenu.show();
            return true;
        });
        holder.markAsComplete.setOnClickListener(v -> {

            listener.onCompleteTask(task);
            notifyDataSetChanged();

        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskTitle, taskDueDate, taskStatus;
        Button markAsComplete;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.taskTitle);
            taskDueDate = itemView.findViewById(R.id.taskDueDate);
            taskStatus = itemView.findViewById(R.id.taskStatus);
            markAsComplete = itemView.findViewById(R.id.markCompleteButton);
        }
    }
}

