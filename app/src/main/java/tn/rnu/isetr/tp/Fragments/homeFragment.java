package tn.rnu.isetr.tp.Fragments;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import tn.rnu.isetr.tp.Database.DatabaseManager;
import tn.rnu.isetr.tp.Entity.Teacher;
import tn.rnu.isetr.tp.R;
import tn.rnu.isetr.tp.Adapters.TeachAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class homeFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Teacher> teacherList;
    private TeachAdapter teacherAdapter;
    DatabaseManager databaseManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);



        recyclerView = view.findViewById(R.id.mRecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        if (teacherList == null) {
            teacherList = new ArrayList<>();
        }
        TextView headerList = view.findViewById(R.id.header_list);
        if (headerList != null) {

            headerList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(getActivity(), v);
                    popupMenu.setOnMenuItemClickListener(onPopupMenuClickListener());
                    popupMenu.inflate(R.menu.popup);
                    popupMenu.show();
                }
            });
        }
        teacherAdapter = new TeachAdapter(teacherList);
        recyclerView.setAdapter(teacherAdapter);
        databaseManager = new DatabaseManager(getContext());
        registerForContextMenu(recyclerView);
        loadTeachers();



        return view;
    }

    private void loadTeachers() {
        teacherList.clear();
        Cursor cursor = databaseManager.getAllTeachers();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));

                teacherList.add(new Teacher(name, email));
            } while (cursor.moveToNext());
            cursor.close();
        }
        teacherAdapter.notifyDataSetChanged();
    }

    public TeachAdapter getAdapter() {
        return teacherAdapter;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.mRecyclerview) {
            MenuInflater inflater = requireActivity().getMenuInflater();
            inflater.inflate(R.menu.context, menu);

        }
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = teacherAdapter.getSelectedPosition();

        if (position != RecyclerView.NO_POSITION && item.getItemId() == R.id.delete_item) {
            // Remove the teacher from the database
            Teacher teacher = teacherList.get(position);
            boolean isDeleted = databaseManager.deleteTeacher(teacher.getEmail());

            if (isDeleted) {
                // Remove the teacher from the list and notify the adapter
                teacherList.remove(position);
                teacherAdapter.notifyItemRemoved(position);
            }
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }


    public void setTeacherList(List<Teacher> teacherList) {
        this.teacherList = teacherList;
        if (teacherAdapter != null) {
            teacherAdapter.notifyDataSetChanged();
        }

    }

    private PopupMenu.OnMenuItemClickListener onPopupMenuClickListener() {
        return new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                RecyclerView recyclerView = getView().findViewById(R.id.mRecyclerview);

                if (item.getItemId()==R.id.show_recycler_view) {
                    recyclerView.setVisibility(View.VISIBLE);
                    return true;} else if (item.getItemId()==R.id.hide_recycler_view) {
                    recyclerView.setVisibility(View.GONE);
                    return true;
                }else{
                    return false;
                }
            }
        };
    }


}
