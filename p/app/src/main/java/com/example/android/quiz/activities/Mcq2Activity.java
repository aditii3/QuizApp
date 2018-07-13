package com.example.android.quiz.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.quiz.R;
import com.example.android.quiz.activities.questions.Mcq2Question;

import java.util.ArrayList;

public class Mcq2Activity extends AppCompatActivity {
    int typeSelected;
    Boolean isActivityRunning;
    final int totalQues = 4;
    ArrayList<Mcq2Question> questions;
    int current = 0;
    TextView question;
    CountDownTimer cdt;
    TextView timerText;
    Intent newIntent;
    int[] ans1 = new int[4];
    int[] ans2 = new int[4];
    CheckBox o1;
    CheckBox o2;
    CheckBox o3;
    CheckBox o4;
    Button submit;
    Boolean runOnce = false;
    final String TIME_KEY = "TIME";
    final String CURRENT_KEY = "CURRENT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcq2);
        isActivityRunning = true;
        ActionBar ab = getActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        newIntent = new Intent(this, ResultActivity.class);

        Intent i = getIntent();
        typeSelected = i.getIntExtra(SettingsActivity.SELECTED_KEY, 0);
        question = findViewById(R.id.textViewQues);
        timerText = findViewById(R.id.textViewTimer);
        submit = findViewById(R.id.btn_submit);

        o1 = findViewById(R.id.cb_option1);
        o2 = findViewById(R.id.cb_option2);
        o3 = findViewById(R.id.cb_option3);
        o4 = findViewById(R.id.cb_option4);

        AddAsync async = new AddAsync();
        async.execute(typeSelected);
        if (savedInstanceState != null) {
            timerText.setText(savedInstanceState.getString(TIME_KEY));
            runOnce = true;
            current = savedInstanceState.getInt(CURRENT_KEY);

        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(newIntent);
            }
        });

        o1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && o2.isChecked()) {
                    int[] a = {1, 2};
                    checkAns(a);
                } else if (isChecked && o3.isChecked()) {
                    int[] a = {1, 3};
                    checkAns(a);
                } else if (isChecked && o4.isChecked()) {
                    int[] a = {1, 4};
                    checkAns(a);
                }

            }
        });
        o2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && o1.isChecked()) {
                    int[] a = {2, 1};
                    checkAns(a);
                } else if (isChecked && o3.isChecked()) {
                    int[] a = {2, 3};
                    checkAns(a);
                } else if (isChecked && o4.isChecked()) {
                    int[] a = {2, 4};
                    checkAns(a);
                }
            }
        });
        o3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && o1.isChecked()) {
                    int[] a = {3, 1};
                    checkAns(a);
                } else if (isChecked && o2.isChecked()) {
                    int[] a = {3, 2};
                    checkAns(a);
                } else if (isChecked && o4.isChecked()) {
                    int[] a = {3, 4};
                    checkAns(a);
                }
            }
        });
        o4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && o1.isChecked()) {
                    int[] a = {4, 1};
                    checkAns(a);
                } else if (isChecked && o2.isChecked()) {
                    int[] a = {4, 2};
                    checkAns(a);
                } else if (isChecked && o3.isChecked()) {
                    int[] a = {4, 3};
                    checkAns(a);
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TIME_KEY, timerText.getText().toString());
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
    }

    private void start() {
        question.setText(questions.get(current).getQuestion());
        o1.setText(questions.get(current).getO1());
        o2.setText(questions.get(current).getO2());
        o3.setText(questions.get(current).getO3());
        o4.setText(questions.get(current).getO4());
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
                timerText.setText(millisUntilFinished / 1000 + "");
            }

            @Override
            public void onFinish() {
                o1.setChecked(false);
                o2.setChecked(false);
                o3.setChecked(false);
                o4.setChecked(false);

                updateQues();
            }
        };
        cdt.start();
    }

    private void checkAns(int[] a) {
        cdt.cancel();
        Toast t = new Toast(this);
        if (((a[0] == ans1[current]) && (a[1] == ans2[current])) || ((a[0] == ans2[current]) && ((a[1] == ans1[current])))) {
            t.makeText(this, getString(R.string.correct), Toast.LENGTH_SHORT).show();

            Mcq2Question.setMarks(7);

        } else {
            t.makeText(this, getString(R.string.wrong), Toast.LENGTH_SHORT).show();
        }
        updateQues();
    }

    private void updateQues() {
        if (current < totalQues - 1) {
            current++;
            o1.setChecked(false);
            o2.setChecked(false);
            o3.setChecked(false);
            o4.setChecked(false);
            start();
        } else {
            cdt = null;
            submit.setVisibility(View.VISIBLE);

        }
    }

    private class AddAsync extends AsyncTask<Integer, Void, ArrayList<Mcq2Question>> {
        @Override
        protected void onPreExecute() {
            Toast.makeText(Mcq2Activity.this, "Wait quiz is being set up", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(ArrayList<Mcq2Question> mcq2Questions) {
            questions = mcq2Questions;
            if (isActivityRunning) {
                start();
            }
        }

        @Override
        protected ArrayList<Mcq2Question> doInBackground(Integer... integers) {
            ArrayList<Mcq2Question> q = new ArrayList<>();
            if (integers[0] == 0) {//add java ques
                q.add(new Mcq2Question("Situations where \"this\" keyword can be used ?", "While accessing the member variable having same name as local variable", "constructor chaining", "No idea", "Wrong Question", 1, 2));
                q.add(new Mcq2Question("class Test extends Exception { }\n" +
                        "  \n" +
                        "class Main {\n" +
                        "   public static void main(String args[]) { \n" +
                        "      try {\n" +
                        "         throw new Test();\n" +
                        "      }\n" +
                        "      catch(Test t) {\n" +
                        "         System.out.println(\"Got the Test Exception\");\n" +
                        "      }\n" +
                        "      finally {\n" +
                        "         System.out.println(\"Inside finally block \");\n" +
                        "      }\n" +
                        "  }\n" +
                        "}", "Inside finally block ", "No specific answer", "Got the Test Exception", "Complie Time Error", 1, 3));
                q.add(new Mcq2Question("Which of the following are Java reserved words?", "run", "import", "default", "implement", 2, 3));
                q.add(new Mcq2Question("What is the type of variable ‘b’ and ‘d’ in the below snippet?\n" +
                        "\n" +
                        "int a[], b;\n" +
                        "int []c, d;", "b is int array", "d is int variable", "b is int variable", "d is int array", 3, 4));
                for (int i = 0; i < q.size(); i++) {
                    ans1[i] = q.get(i).getAns1();
                    ans2[i] = q.get(i).getAns2();
                }
            } else if (integers[0] == 1) {//add cpp question
                q.add(new Mcq2Question("Which of these is true?", "C++ code is converted to ByteCode", "C++ supports pointers", "Java supports operator overloading multiple inheritance but C++ does not", "C++ supports operator overloading multiple inheritance but java does not", 2, 4));
                q.add(new Mcq2Question("What is currect syntax of for loop?", "for(initialization;condition; increment)", "for(increment/decrement; initialization; condition)", "All are correct", "for(initialization;condition; decrement)", 1, 4));
                q.add(new Mcq2Question("Find output of below program\n" +
                        "\n" +
                        "int main()\n" +
                        "{\n" +
                        "int c1,c2;\n" +
                        "int a = -8;\n" +
                        "int b = 3;\n" +
                        "c1 = --a + b;\n" +
                        "c2 = a-- + b;\n" +
                        "cout<<\"c1=\"<<c1<<\", \"<<\"c2=\"<<c2<<endl;\n" +
                        "\n" +
                        "return 0;\n" +
                        "}", "c1=-7", "c1=-6", "c2=-6", "c2=-3", 2, 3));
                q.add(new Mcq2Question("What is a Dangling Pointer?", "Dangling pointers arise during object destruction", "Pointer pointing to memory location which has been freed", "Pointer which is pointing to new location", "A pointer pointing to NULL", 1, 2));
                for (int i = 0; i < q.size(); i++) {
                    ans1[i] = q.get(i).getAns1();
                    ans2[i] = q.get(i).getAns2();
                }

            } else if (integers[0] == 2) {//add android ques
                q.add(new Mcq2Question("Which of the following a Notification object must contain?", "A small icon", "A detail text", "A notification sound", "A background music", 1, 2));
                q.add(new Mcq2Question("Which of the following is true about the Dialog class?", "You can add a custom layout to a dialog using setContentView()", "A dialog has a life-cycle independent of the Activity", "A dialog is displayed on the screen using method show()", "It does not have a method to access the activity that owns it", 1, 3));
                q.add(new Mcq2Question("Which of the following applies to the onDraw() method of class View?", "It takes two parameters: a Canvas and a View", "It takes one parameter of type Canvas", "It uses the Canvas parameter to draw the border of the activity that contains it", "It must be overridden if a customize drawing of a view is required", 2, 4));
                q.add(new Mcq2Question("What are the functionalities of Binder services in android?", "Binder is responsible to manage the thread while using aidl in android", "Binder is responsible for marshalling and un-marshalling of the data", "Binder is a kind of interface", "Hard to tell!!", 1, 2));
                for (int i = 0; i < q.size(); i++) {
                    ans1[i] = q.get(i).getAns1();
                    ans2[i] = q.get(i).getAns2();
                }
            }
            return q;
        }
    }
}
