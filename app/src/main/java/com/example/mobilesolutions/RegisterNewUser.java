package com.example.mobilesolutions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterNewUser extends AppCompatActivity implements View.OnClickListener {
    private Button registrationButton;
    private EditText passwordRegistration, emailRegistration, fullNameRegistration, ageRegistration;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_user);
        mAuth = FirebaseAuth.getInstance();

        registrationButton = (Button) findViewById(R.id.registerationButton);
        registrationButton.setOnClickListener(this);
        passwordRegistration = (EditText) findViewById(R.id.passwordRegistration);
        emailRegistration = (EditText) findViewById(R.id.emailRegistration);
        fullNameRegistration = (EditText) findViewById(R.id.fullNameRegister);
        ageRegistration = (EditText) findViewById(R.id.ageRegistration);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerationButton:
                registerUser();
                break;

        }
    }

    private void registerUser() {
        String email = emailRegistration.getText().toString().trim();
        String password = passwordRegistration.getText().toString().trim();
        String age = ageRegistration.getText().toString().trim();
        String fullName = fullNameRegistration.getText().toString().trim();

        if (fullName.isEmpty()) {
            fullNameRegistration.setError("Full Name Is Required!");
            fullNameRegistration.requestFocus();
            return;
        }
        if (age.isEmpty()) {
            ageRegistration.setError("Age Is Required!");
            ageRegistration.requestFocus();
            return;
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailRegistration.setError("Please Enter Valid Email Address!");
            emailRegistration.requestFocus();
            return;
        }
        if (password.isEmpty() || password.length()<8){
            passwordRegistration.setError("Please Enter at least 8 digits password!");
            passwordRegistration.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        User user= new User(fullName,age,email);
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(task1 -> {
                                    if(task1.isSuccessful()){
                                        Toast.makeText(RegisterNewUser.this, "User registered successfully", Toast.LENGTH_LONG).show();
                                        emailRegistration.setText("");
                                        ageRegistration.setText("");
                                        passwordRegistration.setText("");
                                        fullNameRegistration.setText("");
                                        startActivity(new Intent(this,MainActivity.class));
                                    }
                                    else{
                                        Toast.makeText(RegisterNewUser.this,"Failed to register!", Toast.LENGTH_LONG).show();
                                    }
                            progressBar.setVisibility(View.GONE);
                        });
                    }else{
                        Toast.makeText(RegisterNewUser.this,"Failed to register!", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });

    }
}