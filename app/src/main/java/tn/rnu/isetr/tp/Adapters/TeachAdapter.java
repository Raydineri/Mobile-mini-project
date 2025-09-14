package tn.rnu.isetr.tp.Adapters;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import tn.rnu.isetr.tp.Entity.Teacher;
import tn.rnu.isetr.tp.R;

public class TeachAdapter extends RecyclerView.Adapter<TeachAdapter.TeacherViewHolder> {
    private final List<Teacher> teacherList;
    private int selectedPosition;

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }
    public TeachAdapter(List<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.teacher, parent, false);
        return new TeacherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, int position) {
        Teacher teacher = teacherList.get(position);
        holder.nameTextView.setText(teacher.getName());
        holder.emailTextView.setText(teacher.getEmail());
        holder.itemView.setOnLongClickListener(v -> {
            selectedPosition = holder.getAdapterPosition();
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return teacherList.size();
    }

    public static class TeacherViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, emailTextView;

        public TeacherViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.teacher_name);
            emailTextView = itemView.findViewById(R.id.teacher_email);
        }
    }
    public void sortByName(List<Teacher> teachers) {
        Collections.sort(teachers, new Comparator<Teacher>() {
            @Override
            public int compare(Teacher teacher1, Teacher teacher2) {
                return teacher1.getName().compareTo(teacher2.getName());
            }
        });
        notifyDataSetChanged();
    }

    public void reverseByName(List<Teacher> teachers) {
        Collections.sort(teachers, new Comparator<Teacher>() {
            @Override
            public int compare(Teacher teacher1, Teacher teacher2) {
                return teacher2.getName().compareTo(teacher1.getName());
            }
        });
        notifyDataSetChanged();
    }
    public void removeItem(int position) {
        teacherList.remove(position);
        notifyItemRemoved(position);
    }
    public List<Teacher> getTeacherList() {
        return teacherList;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = new MenuInflater(v.getContext());
        inflater.inflate(R.menu.context, menu);
    }
}
