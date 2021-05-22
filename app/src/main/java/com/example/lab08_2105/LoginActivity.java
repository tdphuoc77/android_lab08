package com.example.lab08_2105;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity  extends AppCompatActivity implements View.OnClickListener{
    TextView tvRegister;
    Button btnSignIn;
    EditText txtEmail, txtPassword;

    FirebaseAuth firebaseAuth;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        tvRegister = findViewById(R.id.tvReges);
        btnSignIn = findViewById(R.id.btnSignUp);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        tvRegister.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvReges:{
                Intent intent = new Intent(LoginActivity.this,RegisActivity.class);
                LoginActivity.this.startActivity(intent);
                break;
            }
            case R.id.btnSignUp:{
                String email = txtEmail.getText().toString().trim();
                String password= txtPassword.getText().toString().trim();
                if(email.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(!task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this, "Cant't sign in", Toast.LENGTH_LONG).show();
                                }else {
                                    userId = firebaseAuth.getCurrentUser().getUid();
                                    Intent intent = new Intent(LoginActivity.this, MoodActivity.class);
                                    intent.putExtra("USERID", userId);
                                   startActivity(intent);
                                    finish();
                                }
                            }
                        });
                break;
            }
        }
    }
}
