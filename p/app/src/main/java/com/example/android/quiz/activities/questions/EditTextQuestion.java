package com.example.android.quiz.activities.questions;


public class EditTextQuestion extends question {
    private String question;
    private String answer;

    public EditTextQuestion(String ques, String ans) {
        question = ques;
        answer = ans;
    }

    private EditTextQuestion() {
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }

    public static void setMarks(int m) {
        com.example.android.quiz.activities.questions.question.marks += m;
    }

}
