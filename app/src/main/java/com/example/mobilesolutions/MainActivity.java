package com.example.mobilesolutions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView registerText, forgetPasswordButton;
    private EditText emailLogin, passwordLogin;
    private Button loginButton;
    private ProgressBar loginProgressBar;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerText = (TextView) findViewById(R.id.registerText);
        forgetPasswordButton = (TextView) findViewById(R.id.forgetPasswordButton);
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

        emailLogin = (EditText) findViewById(R.id.emailLogin);
        passwordLogin = (EditText) findViewById(R.id.passwordLogin);

        loginProgressBar = (ProgressBar) findViewById(R.id.loginProgressBar);
        loginProgressBar.setVisibility(View.GONE);
        auth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registerText:
                startActivity(new Intent(this,RegisterNewUser.class));
                break;
            case R.id.forgetPasswordButton:
                startActivity(new Intent(this,ForgetPassword.class));
                break;
        }
    }
    private void userLogin(){
        String email = emailLogin.getText().toString().trim();
        String password = passwordLogin.getText().toString().trim();
        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailLogin.setError("Enter a valid email!");
            emailLogin.requestFocus();
            return;
        }
        if(password.isEmpty() || password.length()>8){
            passwordLogin.setError("Password is too short!");
            passwordLogin.requestFocus();
            return;
        }
        loginProgressBar.setVisibility(View.VISIBLE);
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

           if(task.isSuccessful()){
               startActivity(new Intent(this,CartView.class));
               Toast.makeText(this,"Hello!", Toast.LENGTH_LONG).show();
           }else {
               Toast.makeText(this,"Error! Please try again!", Toast.LENGTH_LONG).show();
           }
           loginProgressBar.setVisibility(View.GONE);
        });
    }

}