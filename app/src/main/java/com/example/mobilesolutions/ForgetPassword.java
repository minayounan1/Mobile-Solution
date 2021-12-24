package com.example.mobilesolutions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {
    private EditText emailResetPassword;
    private Button resetButton;
    private ProgressBar resetProgressBar;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        emailResetPassword = (EditText) findViewById(R.id.emailResetPassword);
        resetButton = (Button) findViewById(R.id.resetButton);
        resetProgressBar = (ProgressBar) findViewById(R.id.resetProgressBar);
        resetProgressBar.setVisibility(View.GONE);
        auth = FirebaseAuth.getInstance();

        resetButton.setOnClickListener(v -> resetPassword());
    }
    private void resetPassword(){
        String email = emailResetPassword.getText().toString().trim();
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailResetPassword.setError("Please Enter Valid Email Address!");
            emailResetPassword.requestFocus();
            return;
        }
        resetProgressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()){

                Toast.makeText(ForgetPassword.this, "Check your email box!", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(ForgetPassword.this, "Failed to reset password!", Toast.LENGTH_LONG).show();
            }
            resetProgressBar.setVisibility(View.GONE);
        });
    }
}