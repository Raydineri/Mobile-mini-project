package tn.rnu.isetr.tp.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import tn.rnu.isetr.tp.Database.DatabaseManager;
import tn.rnu.isetr.tp.R;


public class ProfileFragment extends Fragment {

    private static final int IMAGE_PICK_REQUEST_CODE = 1;
    private ImageView profileImage;
    private EditText profileName;
    private SharedPreferences sharedPreferences;
    private DatabaseManager databaseManager;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize UI components
        profileImage = view.findViewById(R.id.profile_picture);
        profileName = view.findViewById(R.id.et_name);
        EditText editEmail = view.findViewById(R.id.et_email);
        EditText editName = view.findViewById(R.id.et_name);
        Button editProfileImageButton = view.findViewById(R.id.editProfileImageButton);
        Button saveProfileButton = view.findViewById(R.id.btn_update_profile);
        saveProfileButton.setOnClickListener(v -> saveUserData(editEmail, editName));
        Button contactUsers = view.findViewById(R.id.ContactUserr);
        contactUsers.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new UsersContactsFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // Initialize SharedPreferences and database
        sharedPreferences = requireActivity().getSharedPreferences("UserProfile", Context.MODE_PRIVATE);

        databaseManager = new DatabaseManager(getContext());



        // Handle profile image change
        editProfileImageButton.setOnClickListener(v -> openImagePicker());




        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadProfileData();
    }

    // Open image picker to select a new profile picture
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_PICK_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                profileImage.setImageURI(selectedImageUri);
                // Save image URI to SharedPreferences (optional)

                saveProfileImageUri(selectedImageUri.toString());
            }
        }
    }

    // Save profile name and image URI to SharedPreferences
    private void saveProfileData(String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.apply();
        Toast.makeText(getContext(), "Profile updated!", Toast.LENGTH_SHORT).show();
    }

    private void saveProfileImageUri(String imageUri) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("imageUri", imageUri);
        editor.apply();
        Toast.makeText(getContext(), "Profile picture updated!", Toast.LENGTH_SHORT).show();
    }

    // Load profile data from SharedPreferences
    private void loadProfileData() {
        String savedName = sharedPreferences.getString("userName", "");
        String savedEmail = sharedPreferences.getString("userEmail", "");

        TextView email = requireView().findViewById(R.id.Emailtext);
        email.setText(savedEmail);
        TextView profileName = requireView().findViewById(R.id.NameText);
        profileName.setText(savedName);






    }
    private void saveUserData(EditText email, EditText name) {
        // Get updated values
        String updatedName = name.getText().toString().trim();
        String updatedEmail = email.getText().toString().trim();

        // Validate inputs
        if (updatedName.isEmpty() || updatedEmail.isEmpty()) {
            Toast.makeText(getContext(), "Name and Email cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save to database (Example: SQLite)
        DatabaseManager databaseManager = new DatabaseManager(getContext());
        boolean isUpdated = databaseManager.updateUser(updatedName, updatedEmail);
        if (isUpdated) {
            Toast.makeText(getContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Failed to update profile", Toast.LENGTH_SHORT).show();
        }
    }
}