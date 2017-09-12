package com.example.uuun.cbyapplication.bean;

/**
 * /**
 * Auto-generated: 2017-06-20 17:15:6
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class QuestionOptions {

    private int id;
    private int questionId;
    private String content;
    private String custom;
    //答案是否被解答
    private int ans_state;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getCustom() {
        return custom;
    }

    public int getAns_state() {
        return ans_state;
    }

    public void setAns_state(int ans_state) {
        this.ans_state = ans_state;
    }
}