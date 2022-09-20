package com.android.myquiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.myquiz.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;


public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    TextView name, username, email, phone, password;
    Button btn;
    private FirebaseAuth mAuth;
    FirebaseFirestore database;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        name = findViewById(R.id.txt1);
        username = findViewById(R.id.txt2);
        email = findViewById(R.id.txt3);
        phone = findViewById(R.id.txt4);
        password = findViewById(R.id.txt5);
        btn = findViewById(R.id.signup);


        mAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait..! creating a account.");

        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
//                createUser();
                String name1, username1, email1, phone1, password1;

                name1 = binding.txt1.getText().toString();
                username1 = binding.txt2.getText().toString();
                email1 = binding.txt3.getText().toString();
                phone1 = binding.txt4.getText().toString();
                password1 = binding.txt5.getText().toString();

                final User user = new User(name1, username1, email1, phone1, password1);

                if (name1.isEmpty()) {
                    name.setError("This field required");
                    name.requestFocus();
                    return;
                }

                if (username1.isEmpty()){
                    username.setError("Please enter the unique username");
                    username.requestFocus();
                    return;
                }
                if (email1.isEmpty()){
                    email.setError("Enter a email address");
                    email.requestFocus();
                return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
                    email.setError("Enter valid email");
                    email.requestFocus();
                    return;

                }
                if (phone1.isEmpty()){
                    phone.setError("Enter a phone number");
                    phone.requestFocus();
                    return;
                }
                if (phone1.length()<10) {
                     phone.setError("Enter a valid phone number");
                    phone.requestFocus();
                return;
                }
                if (password1.isEmpty()){
                password.setError("Enter a password");
                password.requestFocus();
                return;

                }
                if (password1.length()<8) {
                    password.setError("Enter a strong password");
                    password.requestFocus();
                    return;

                }

                    dialog.show();
                    mAuth.createUserWithEmailAndPassword(email1, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                String uid = task.getResult().getUser().getUid();

                                database
                                        .collection("users")
                                        .document(uid)
                                        .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            dialog.dismiss();
                                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                            finish();
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "Failed to register, Try again..!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                dialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "Email id already exist..!", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                }


        });


        }
    }




//    private void createUser() {
//        String name1 = name.getText().toString();
//        String username1 = username.getText().toString();
//        String email1 = email.getText().toString();
//        String phone1 = phone.getText().toString();
//        String password1 = password.getText().toString();
//
//        if (name1.isEmpty()){
//            name.setError("Name is required");
//            name.requestFocus();
//            return;
//        }
//        if (username1.isEmpty()){
//            username.setError("Please enter the unique username");
//            username.requestFocus();
//            return;
//        }
//        if (email1.isEmpty()){
//            email.setError("Enter a email address");
//            email.requestFocus();
//            return;
//        }
//        if (!Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
//            email.setError("Enter valid email");
//            email.requestFocus();
//            return;
//
//        }
//        if (phone1.isEmpty()){
//            phone.setError("Enter a phone number");
//            phone.requestFocus();
//            return;
//        }
//        if (phone1.length()<10) {
//            phone.setError("Enter a valid phone number");
//            phone.requestFocus();
//            return;
//        }
//        if (password1.isEmpty()){
//            password.setError("Enter a password");
//            password.requestFocus();
//            return;
//
//        }
//        if (password1.length()<8){
//            password.setError("Enter a strong password");
//            password.requestFocus();
//            return;
//        }




//        final User user = new User(name1, username1, email1, password1);
//
//        dialog.show();
//
//        mAuth.createUserWithEmailAndPassword(email1, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
//                if (task.isSuccessful()){
//                    User user = new User(name1, username1, email1, phone1, password1);
//
//                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull @NotNull Task<Void> task) {
//                            if (task.isSuccessful()){
//                                Toast.makeText(getApplicationContext(),"Successful", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//
//                            }else{
//                                Toast.makeText(getApplicationContext(),"Register failed", Toast.LENGTH_SHORT).show();
//
//                            }
//
//                        }
//                    });
//                }else{
//                    Toast.makeText(getApplicationContext(),"This email id already exist", Toast.LENGTH_SHORT).show();
//                }


//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
//    }
//
//    public void LoggingIn(View view) {
//        startActivity(new Intent(this,LoginActivity.class));
//    }


