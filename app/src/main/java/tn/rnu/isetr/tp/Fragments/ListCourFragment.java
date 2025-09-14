package tn.rnu.isetr.tp.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tn.rnu.isetr.tp.Adapters.CoursAdapter;
import tn.rnu.isetr.tp.Database.DatabaseManager;
import tn.rnu.isetr.tp.Entity.cour;
import tn.rnu.isetr.tp.R;

public class ListCourFragment extends Fragment {

    private RecyclerView recyclerView;
    private CoursAdapter coursAdapter;
    private List<cour> coursList;
    private DatabaseManager databaseManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_cour, container, false);


        recyclerView = view.findViewById(R.id.recyclerViewCours);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        if (coursList == null) {
            coursList = new ArrayList<>();
        }
        databaseManager = new DatabaseManager(getContext());



        coursAdapter = new CoursAdapter(coursList, getContext());
        recyclerView.setAdapter(coursAdapter);
        registerForContextMenu(recyclerView);
        FillListWithCourses(coursList);

        return view;
    }
    private void FillListWithCourses(List<cour> coursList) {
        Cursor  cursor = databaseManager.getAllCourses();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                cour cours = new cour(cursor.getString(cursor.getColumnIndexOrThrow("type")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("nbheure")),
                        cursor.getString(cursor.getColumnIndexOrThrow("enseign_id")));
                coursList.add(cours);
            } while (cursor.moveToNext());

        }
        coursAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = coursAdapter.getSelectedPosition();
        if (item.getItemId() == R.id.buttonDelete) {
            databaseManager.deleteCourse(coursList.get(position).getNom());
            coursList.remove(position);
            coursAdapter.notifyItemRemoved(position);
        }

        return super.onContextItemSelected(item);
    }
}
