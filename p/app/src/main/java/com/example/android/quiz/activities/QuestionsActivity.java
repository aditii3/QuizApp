package com.example.android.quiz.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.android.quiz.R;
import com.example.android.quiz.activities.questions.EditTextQuestion;

import java.util.ArrayList;

public class QuestionsActivity extends AppCompatActivity {
    Boolean isActivityRunning;
    Integer typeSelected;
    ArrayList<EditTextQuestion> questions;
    final int totalQues = 4;
    int current = 0;
    TextView question;
    EditText answer;
    CountDownTimer cdt;
    TextView timer;
    Button submit;
    Intent newIntent;
    Boolean runOnce = false;
    final String TIME_KEY = "TIME";
    final String CURRENT_KEY = "CURRENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isActivityRunning = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        newIntent = new Intent(this, Mcq1Activity.class);

        Intent i = getIntent();
        typeSelected = i.getIntExtra(SettingsActivity.SELECTED_KEY, 0);
        newIntent.putExtra(SettingsActivity.SELECTED_KEY, typeSelected);

        if (isActivityRunning) {
            AddQuesAsync quesAsync = new AddQuesAsync();
            quesAsync.execute(typeSelected);
        }
        ActionBar ab = getActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        question = findViewById(R.id.textViewQues);
        answer = findViewById(R.id.et_ans);
        timer = findViewById(R.id.textViewTimer);
        submit = findViewById(R.id.submit);

        if (savedInstanceState != null) {
            timer.setText(savedInstanceState.getString(TIME_KEY));
            runOnce = true;
            current = savedInstanceState.getInt(CURRENT_KEY);

        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current < totalQues) {
                    checkAnswer();
                } else {
                    cdt.cancel();
                    startActivity(newIntent);
                }
            }
        });


    }

    private void start() {
        if (current < totalQues) {
            question.setText(questions.get(current).getQuestion());
            answer.setText("");
            if (cdt != null) {
                cdt.cancel();
            }
            int millisInFuture;
            if (runOnce) {
                millisInFuture = Integer.parseInt(timer.getText().toString()) * 1000;
                runOnce = false;
            } else {
                millisInFuture = 20000;
            }
            cdt = new CountDownTimer(millisInFuture, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    timer.setText("" + millisUntilFinished / 1000);

                }

                @Override
                public void onFinish() {
                    checkAnswer();
                }
            };
            cdt.start();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NavUtils.navigateUpFromSameTask(this);
    }

    private void checkAnswer() {
        if (TextUtils.equals("", answer.getText())) {
            Toast.makeText(this, getString(R.string.answer_the_question), Toast.LENGTH_SHORT).show();
            return;
        }
        Toast t = new Toast(this);
        if (questions.get(current).getAnswer().toUpperCase().equals(answer.getText().toString().toUpperCase())) {
            EditTextQuestion.setMarks(3);
            t.makeText(this, getString(R.string.correct), Toast.LENGTH_SHORT).show();
        } else {
            t.makeText(this, getString(R.string.wrong), Toast.LENGTH_SHORT).show();
        }
        current++;
        if (current < totalQues) {
            start();
        } else {
            cdt.cancel();
            questions.clear();
            startActivity(newIntent);
        }
    }


    private class AddQuesAsync extends AsyncTask<Integer, Void, ArrayList<EditTextQuestion>> {
        @Override
        protected void onPreExecute() {
            Toast.makeText(QuestionsActivity.this, "Wait quiz is being set up", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(ArrayList<EditTextQuestion> editTextQuestions) {
            questions = editTextQuestions;
            start();
        }


        @Override
        protected ArrayList<EditTextQuestion> doInBackground(Integer... integers) {
            ArrayList<EditTextQuestion> q = new ArrayList<>();
            if (integers[0] == 0) {//add java ques
                q.add(new EditTextQuestion("What is the full form of JVM", "Java Virtual Machine"));
                q.add(new EditTextQuestion("Strings are _____", "Immutable"));
                q.add(new EditTextQuestion("We can restrict inheritence by using _____ keyword", "final"));
                q.add(new EditTextQuestion("Define JSON", "JavaScript Object Notation"));
            } else if (integers[0] == 1) {//add cpp question
                q.add(new EditTextQuestion("A function automatically called whenever a new object of this class is created", "Constructor"));
                q.add(new EditTextQuestion("What is the index number of the last element of an array with 9 elements?", "8"));
                q.add(new EditTextQuestion("What is output\nint array[] = {10, 20, 30};\ncout << -2[array];", "-20"));
                q.add(new EditTextQuestion("Full form of OOPS", "Object Oriented Programming System"));

            } else if (integers[0] == 2) {//add android ques
                q.add(new EditTextQuestion("____ is like  frame or window in java that represents GUI. It represents one screen of android.", "Activity"));
                q.add(new EditTextQuestion("____ is used to share information between android applications.", "Content provider"));
                q.add(new EditTextQuestion("A component that runs in the background. It is used to play music, handle network transaction etc.", "Service"));
                q.add(new EditTextQuestion("What is ADB?", "Android Debug Bridge"));

            }
            return q;
        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TIME_KEY, timer.getText().toString());
        outState.putInt(CURRENT_KEY, current);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (questions != null) {
            questions.clear();
        }
        if (cdt != null) {
            cdt.cancel();
        }
        isActivityRunning = false;

    }
}
