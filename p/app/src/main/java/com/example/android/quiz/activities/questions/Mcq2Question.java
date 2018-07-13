package com.example.android.quiz.activities.questions;

/**
 * Created by aditi on 7/13/2018.
 */

public class Mcq2Question extends question {
    private String question;
    private String o1;
    private String o2;
    private String o3;
    private String o4;
    private int ans1;
    private int ans2;

    public String getO1() {
        return o1;
    }

    public static void setMarks(int m) {
        com.example.android.quiz.activities.questions.question.marks += m;
    }

    public String getO2() {
        return o2;
    }

    public String getO3() {
        return o3;
    }

    public String getO4() {
        return o4;
    }

    public Mcq2Question(String q, String o1, String o2, String o3, String o4, int a1, int a2) {
        question = q;
        this.o1 = o1;
        this.o2 = o2;
        this.o3 = o3;
        this.o4 = o4;
        ans1 = a1;
        ans2 = a2;

    }

    private Mcq2Question() {
    }

    public int getAns1() {
        return ans1;
    }

    public int getAns2() {
        return ans2;
    }

    public String getQuestion() {
        return question;
    }

}
