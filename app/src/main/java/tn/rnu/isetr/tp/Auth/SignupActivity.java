package tn.rnu.isetr.tp.Auth;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import tn.rnu.isetr.tp.Database.DatabaseManager;
import tn.rnu.isetr.tp.R;
import tn.rnu.isetr.tp.databinding.ActivitySignupBinding;


public class SignupActivity extends AppCompatActivity {
    private ActivitySignupBinding bind;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        bind = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        databaseManager = new DatabaseManager(this);;



        bind.btnSignup.setOnClickListener(v -> {
            String name = bind.etName.getText().toString();
            String email = bind.etEmail.getText().toString();
            String password = bind.etPassword.getText().toString();
            String confirmPassword = bind.etConfirmPassword.getText().toString();
            String phone = bind.EtPhoneNumber.getText().toString();

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || name.isEmpty() || phone.isEmpty()) {
                bind.etEmail.setError("Email is required");
                bind.etPassword.setError("Password is required");
                bind.etConfirmPassword.setError("Confirm Password is required");
                bind.etName.setError("Name is required");
                bind.EtPhoneNumber.setError("Phone Number is required");
            }
            if (!password.equals(confirmPassword)) {
                bind.etPassword.setError("Password does not match");
                bind.etConfirmPassword.setError("Password does not match");
            }
            else {
                boolean isInserted = databaseManager.insertUser(name, email, password, phone);
                if (isInserted) {
                    Toast.makeText(this, "Signup successful", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(this, "Signup failed(Email May Already Exist)", Toast.LENGTH_SHORT).show();
                }



            }
        });
        bind.tvLoginLink.setOnClickListener(v -> {
            finish();
        });
    }
}
