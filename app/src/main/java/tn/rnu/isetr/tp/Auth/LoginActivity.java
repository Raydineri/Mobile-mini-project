package tn.rnu.isetr.tp.Auth;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import tn.rnu.isetr.tp.Database.DatabaseManager;
import tn.rnu.isetr.tp.MainActivity;
import tn.rnu.isetr.tp.R;
import tn.rnu.isetr.tp.databinding.ActivityLoginBinding;

public class LoginActivity  extends AppCompatActivity {
    ActivityLoginBinding bind;
    DatabaseManager databaseManager;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bind = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        databaseManager = new DatabaseManager(this);


        bind.btnLogin.setOnClickListener(v -> {
            String email = bind.etEmail.getText().toString();
            String password = bind.etPassword.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                bind.etEmail.setError("Email is required");
                bind.ErrorTextView.setText("Email  is required");

                bind.etPassword.setError("Password is required");
                bind.ErrorTextView.setText(bind.ErrorTextView.getText() + " \n Password is required");
            } else {
                Boolean isAuthenticated = databaseManager.authenticateUser(email, password);
                if (isAuthenticated) {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                    sharedPreferences = getSharedPreferences("UserProfile", Context.MODE_PRIVATE);
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    Cursor cursor = databaseManager.getUserByEmail(email);
                    if (cursor.moveToFirst()) {
                        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));

                        saveUserData(name, email);


                    } else {
                        bind.ErrorTextView.setText("Invalid Email or Password");
                        Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    bind.ErrorTextView.setText("Invalid Email or Password");
                    Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                }


            }

        });
        bind.tvSignupLink.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);

        });
    }
    private void saveUserData(String name, String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName", name);
        editor.putString("userEmail", email);

        editor.apply();

        Toast.makeText(this, "User data saved!", Toast.LENGTH_SHORT).show();
    }
}



