package com.android.myquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {
    TextView email1,password1;
    TextView forgotPassword ;
    Button btn;
    private FirebaseAuth mAuth;
    ProgressDialog dialog;


//   SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email1 = findViewById(R.id.email);
        password1 = findViewById(R.id.password);
        btn = findViewById(R.id.login);

        mAuth = FirebaseAuth.getInstance();

        forgotPassword = findViewById(R.id.forgotPassword);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Logging in..");



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();

            }

        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ForgotActivity.class));
            }
        });

        if (mAuth.getCurrentUser() != null) {

            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }




    }


    private void loginUser() {

        String email = email1.getText().toString();
        String password = password1.getText().toString();

        if (email.isEmpty()){
            email1.setError("Enter a email address");
            email1.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email1.setError("Enter valid email");
            email1.requestFocus();
            return;

        }

        if (password.isEmpty()){
            password1.setError("Enter a password");
            password1.requestFocus();
            return;

        }
        if (password.length()<8){
            password1.setError("Enter a strong password");
            password1.requestFocus();
            return;
        }

        dialog.show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                dialog.dismiss();
                if (task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()){

                        Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(getApplicationContext(),"Check your email to verify", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override 
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }



    public void registerNow(View view) {
        startActivity(new Intent(this,RegisterActivity.class));
    }
}