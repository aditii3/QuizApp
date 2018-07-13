package com.example.android.quiz.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.quiz.R;
import com.example.android.quiz.activities.questions.Mcq1Question;

import java.util.ArrayList;

public class Mcq1Activity extends AppCompatActivity {
    int typeSelected;
    Boolean isActivityRunning;
    final int totalQues = 4;
    ArrayList<Mcq1Question> questions;
    int current = 0;
    TextView question;
    CountDownTimer cdt;
    TextView timerText;
    Intent newIntent;
    RadioGroup radioGroup;
    RadioButton o1;
    RadioButton o2;
    RadioButton o3;
    RadioButton o4;
    Boolean runOnce = false;
    final String TIME_KEY = "TIME";
    final String CURRENT_KEY = "CURRENT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcq1);
        isActivityRunning = true;

        ActionBar ab = getActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        newIntent = new Intent(this, Mcq2Activity.class);

        Intent i = getIntent();
        typeSelected = i.getIntExtra(SettingsActivity.SELECTED_KEY, 0);
        newIntent.putExtra(SettingsActivity.SELECTED_KEY, typeSelected);
        question = findViewById(R.id.textViewQues);
        radioGroup = findViewById(R.id.rg_mcq1);
        o1 = findViewById(R.id.rb_option1);
        o2 = findViewById(R.id.rb_option2);
        o3 = findViewById(R.id.rb_option3);
        o4 = findViewById(R.id.rb_option4);
        timerText = findViewById(R.id.textViewTimer);
        if (savedInstanceState != null) {
            timerText.setText(savedInstanceState.getString(TIME_KEY));
            runOnce = true;
            current = savedInstanceState.getInt(CURRENT_KEY);

        }

        if (isActivityRunning) {
            AddQuesAsync quesAsync = new AddQuesAsync();
            quesAsync.execute(typeSelected);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_option1:
                        cdt.cancel();
                        check(1);
                        break;
                    case R.id.rb_option2:
                        cdt.cancel();
                        check(2);
                        break;
                    case R.id.rb_option3:
                        cdt.cancel();
                        check(3);
                        break;
                    case R.id.rb_option4:
                        cdt.cancel();
                        check(4);
                        break;
                }
            }
        });


    }

    private void start() {
        question.setText(questions.get(current).getQuestion());
        o1.setText(questions.get(current).getOption1());
        o2.setText(questions.get(current).getOption2());
        o3.setText(questions.get(current).getOption3());
        o4.setText(questions.get(current).getOption4());
        int millisInFuture;
        if (runOnce) {
            millisInFuture = Integer.parseInt(timerText.getText().toString()) * 1000;
            runOnce = false;
        } else {
            millisInFuture = 20000;
        }

        cdt = new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerText.setText("" + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                o1.setChecked(false);
                o2.setChecked(false);
                o3.setChecked(false);
                o4.setChecked(false);
                if (current < totalQues) {
                    updateQues();
                }

            }

        }.start();


    }

    private void updateQues() {
        if (cdt != null) {
            cdt.cancel();
        }


        if (current < totalQues - 1) {
            current++;
            o1.setChecked(false);
            o2.setChecked(false);
            o3.setChecked(false);
            o4.setChecked(false);
            start();
        } else if (current == totalQues - 1) {
            cdt.cancel();
            questions.clear();
            startActivity(newIntent);
        }

    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }

    private void check(int i) {
        Toast t = new Toast(this);
        if (i == questions.get(current).getAns()) {
            Mcq1Question.setMarks(5);
            t.makeText(this, getString(R.string.correct), Toast.LENGTH_SHORT).show();

        } else {
            t.makeText(this, getString(R.string.wrong), Toast.LENGTH_SHORT).show();
        }
        if (current < totalQues) {
            updateQues();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TIME_KEY, timerText.getText().toString());
        outState.putInt(CURRENT_KEY, current);
    }

    private class AddQuesAsync extends AsyncTask<Integer, Void, ArrayList<Mcq1Question>> {
        @Override
        protected void onPreExecute() {
            Toast.makeText(Mcq1Activity.this, "Wait quiz is being set up", Toast.LENGTH_SHORT).show();
        }


        @Override
        protected ArrayList<Mcq1Question> doInBackground(Integer... integers) {
            ArrayList<Mcq1Question> q = new ArrayList<>();
            if (integers[0] == 0) {//add java ques
                q.add(new Mcq1Question("What is the stored in the object obj in following lines of code?", "Memory address of allocated memory of object", "NULL", "Any arbitrary pointer", "Garbage", 2));
                q.add(new Mcq1Question("Which of the following is a valid declaration of an object of class Box?", "Box obj = new Box();", "Box obj = new Box;", "obj = new Box();", "new Box obj;", 1));
                q.add(new Mcq1Question("Which of these statement is incorrect?", "Every class must contain a main() method", "Applets do not require a main() method at all", "There can be only one main() method in a program", "main() method must be made public", 1));
                q.add(new Mcq1Question("Which exception is thrown when java is out of memory?", "MemoryFullException", "MemoryOutOfBoundsException", "OutOfMemoryError", "MemoryError", 3));
            } else if (integers[0] == 1) {//add cpp question
                q.add(new Mcq1Question("All members of class have which access to its members", "private", "public", "protected", "depends", 1));
                q.add(new Mcq1Question("Constructor is", "A class automatically called whenever a new object of this class is created", "A class automatically called whenever a new object of this class is destroyed", "A function automatically called whenever a new object of this class is created", "A function automatically called whenever a new object of this class is destroyed", 3));
                q.add(new Mcq1Question("Which operator is used to define a member of a class from outside the class definition", "->", "::", ".", ">>", 2));
                q.add(new Mcq1Question("How to define a destructor", "X~() {}", "X() {}~", "X() ~{}", "~X() {}", 4));

            } else if (integers[0] == 2) {//add android ques
                q.add(new Mcq1Question("What is an activity", "Manage the Application content", "Single focused thing that a user can do", "Represents a screen", "Screen UI", 2));
                q.add(new Mcq1Question("What is an anonymous class in android??", "A class that does not have a name but have functionalities in it", "Interface class", "Java class", "Manifest file", 1));
                q.add(new Mcq1Question("What is Manifest.xml", "It has information about layout in an application", "It has the information about activities in an application", "It has all the information about an application", "None of the above", 3));
                q.add(new Mcq1Question("On which thread services work in android?", "Worker Thread", "Own Thread", "Main Thread", "None of the above", 3));

            }
            return q;
        }

        @Override
        protected void onPostExecute(ArrayList<Mcq1Question> mcq1Questions) {
            questions = mcq1Questions;
            if (isActivityRunning) {
                start();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        isActivityRunning = false;
        if (questions != null) {
            questions.clear();
        }
        if (cdt != null) {
            cdt.cancel();
        }
    }

}
