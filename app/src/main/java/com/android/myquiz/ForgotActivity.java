package com.android.myquiz;

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

import org.jetbrains.annotations.NotNull;

public class ForgotActivity extends AppCompatActivity {

    private EditText resetEmail;
    private Button reset;
    private ProgressBar progressBar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        resetEmail = findViewById(R.id.resetEmail);
        reset = findViewById(R.id.forgotBtn);
        progressBar = findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

    }

    private void resetPassword() {
        String email001 = resetEmail.getText().toString().trim();

        if (email001.isEmpty()){
            resetEmail.setError("Please enter your email id");
            resetEmail.requestFocus();
           return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email001).matches()){
            resetEmail.setError("Valid Email id required");
            resetEmail.requestFocus();
            return;

        }
        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email001).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ForgotActivity.this,"Check your email to reset your password", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ForgotActivity.this,"Something went wrong, Try again", Toast.LENGTH_LONG).show();

                }
                progressBar.setVisibility(View.GONE);

            }
        });

    }
}