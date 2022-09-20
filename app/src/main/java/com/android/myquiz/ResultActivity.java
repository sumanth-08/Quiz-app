package com.android.myquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.myquiz.databinding.ActivityResultBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class ResultActivity extends AppCompatActivity {

    ActivityResultBinding binding;
    int POINTS = 10;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        int correctAnswer = getIntent().getIntExtra("correct",0);
        int totalQuestion = getIntent().getIntExtra("total",0);

        long points = correctAnswer * POINTS;

        binding.score.setText(String.format("%d/%d", correctAnswer, totalQuestion));
        binding.earnedCoins.setText(String.valueOf(points));
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .update("coins", FieldValue.increment(points));

        TextView textView = binding.congrats;
        ImageView imageView = binding.congratsImage;
        if (points == 0){
            textView.setText("Oops! Try again");
        } if (points == 0){
            imageView.setImageResource(R.drawable.illustrator_image5);
        }

    }

    public void reStart(View view) {
        Intent i = new Intent(ResultActivity.this,HomeActivity.class);
        startActivity(i);
    }


}