package com.example.android.quiz.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.android.quiz.activities.questions.question;

import com.example.android.quiz.R;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView result = findViewById(R.id.tv_result);
        result.setText(question.marks+getString(R.string.total));
        if(question.marks>50){
            result.append(getString(R.string.awesome_job));
        }
        else if( question.marks>35&&question.marks<50){
            result.append(getString(R.string.medium_job));
        }else{
            result.append(getString(R.string.bad_job));
        }
    }
}
