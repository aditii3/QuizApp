package com.example.android.quiz.activities.questions;

/**
 * Created by aditi on 7/13/2018.
 */

public class Mcq1Question extends question {
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int ans;

    public Mcq1Question(String q, String o1, String o2, String o3, String o4, int a) {
        question = q;
        option1 = o1;
        option2 = o2;
        option3 = o3;
        option4 = o4;
        ans = a;
    }

    public String getQuestion() {
        return question;
    }

    public int getAns() {
        return ans;
    }

    public String getOption1() {
        return option1;
    }

    public static void setMarks(int m) {
        com.example.android.quiz.activities.questions.question.marks += m;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }
}
