package com.android.myquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.android.myquiz.databinding.ActivityQuizBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    ActivityQuizBinding binding;

    ArrayList<Question> questions;
    int index = 0;
    Question question;
    CountDownTimer timer;
    FirebaseFirestore database;
    int correctAnswer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        questions = new ArrayList<>();
        database = FirebaseFirestore.getInstance();

       final String categoryId = getIntent().getStringExtra("categoryId");

        Random random = new Random();
        final int rand = random.nextInt(10);

        database.collection("categories")
                .document(categoryId)
                .collection("questions")
                .whereGreaterThanOrEqualTo("index",rand)
                .orderBy("index")
                .limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.getDocuments().size() < 5) {
                    database.collection("categories")
                            .document(categoryId)
                            .collection("questions")
                            .whereLessThanOrEqualTo("index",rand)
                            .orderBy("index")
                            .limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (DocumentSnapshot snapshot : queryDocumentSnapshots){
                                Question question = snapshot.toObject(Question.class);
                                questions.add(question);
                            }
                            setNextQuestion();
                        }
                    });

                }else{
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots){
                        Question question = snapshot.toObject(Question.class);
                        questions.add(question);
                    }
                    setNextQuestion();

                }
            }
        });

//        questions.add(new Question("What ia android","operation system","Mobile devise","i don't know","none of the above","operation system"));
//        questions.add(new Question("What ia windows","operation system","laptop","i don't know","none of the above","operation system"));

        resetTimer();

    }

    void resetTimer() {
        timer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long l) {
                binding.timer.setText(String.valueOf(l/1000));
            }

            @Override
            public void onFinish() {

            }

        };
    }


    void setNextQuestion() {
        if (timer != null)
            timer.cancel();
        timer.start();

        if (index < questions.size()) {
            binding.questionCounter.setText(String.format("%d/%d", (index+1), questions.size()));
            question = questions.get(index);
            binding.question.setText(question.getQuestion());
            binding.option1.setText(question.getOption1());
            binding.option2.setText(question.getOption2());
            binding.option3.setText(question.getOption3());
            binding.option4.setText(question.getOption4());
        }
    }

    void checkAnswer(TextView textView) {
        String selectAnswer = textView.getText().toString();
        if (selectAnswer.equals(question.getAnswer())) {
            correctAnswer++;
            textView.setBackground(getResources().getDrawable(R.drawable.option_right));
        } else {
//            showAnswer();
            textView.setBackground(getResources().getDrawable(R.drawable.option_wrong));
        }
    }

    void reset() {
        binding.option1.setBackground(getResources().getDrawable(R.drawable.rounded_button3));
        binding.option2.setBackground(getResources().getDrawable(R.drawable.rounded_button3));
        binding.option3.setBackground(getResources().getDrawable(R.drawable.rounded_button3));
        binding.option4.setBackground(getResources().getDrawable(R.drawable.rounded_button3));
    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.option1:
            case R.id.option2:
            case R.id.option3:
            case R.id.option4:

            if (timer != null)
                timer.cancel();
                TextView selected = (TextView) view;
                checkAnswer(selected);

                break;
            case R.id.next:
                reset();
                  if (index <= questions.size()){
                      index++;
                      setNextQuestion();

                  }else{
                      Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                      intent.putExtra("correct", correctAnswer);
                      intent.putExtra("total", questions.size());
                      startActivity(intent);

                  }
                    break;

                }
        }
    }
