package tn.rnu.isetr.tp.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tn.rnu.isetr.tp.Adapters.UserAdapter;
import tn.rnu.isetr.tp.Database.DatabaseManager;
import tn.rnu.isetr.tp.Entity.User;
import tn.rnu.isetr.tp.R;

public class UsersContactsFragment extends Fragment {
    private RecyclerView recyclerViewUsers;
    private UserAdapter userAdapter;
    private List<User> userList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        recyclerViewUsers = view.findViewById(R.id.recyclerViewUsers);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(getContext()));

        // Load users from database
        loadUsers();

        return view;
    }
    private void loadUsers() {
        userList = new ArrayList<>();

        // Fetch users from the database
        DatabaseManager databaseManager = new DatabaseManager(getContext());
        Cursor cursor = databaseManager.getAllUsers();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
                userList.add(new User(name, email, phone));
            } while (cursor.moveToNext());
            cursor.close();
        }

        userAdapter = new UserAdapter(userList, getContext());
        recyclerViewUsers.setAdapter(userAdapter);
    }
}
