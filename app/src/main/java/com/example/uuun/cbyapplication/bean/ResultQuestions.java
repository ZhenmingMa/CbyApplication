package com.example.uuun.cbyapplication.bean;
/**
 * Copyright 2017 bejson.com
 */

import java.util.List;


public class ResultQuestions {
    private int tag,tag1;
    private Question question;
    private List<QuestionOptions> questionOptions;

    public int getTag1() {
        return tag1;
    }

    public void setTag1(int tag1) {
        this.tag1 = tag1;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestionOptions(List<QuestionOptions> questionOptions) {
        this.questionOptions = questionOptions;
    }

    public List<QuestionOptions> getQuestionOptions() {
        return questionOptions;
    }

}