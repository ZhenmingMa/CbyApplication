package com.example.uuun.cbyapplication.bean;

/**
 * Auto-generated: 2017-06-20 17:15:6
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Question {

    private int id;
    private int surveyId;
    private int type;
    private String content;
    private int que_state;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public int getSurveyId() {
        return surveyId;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public int getQue_state() {
        return que_state;
    }

    public void setQue_state(int que_state) {
        this.que_state = que_state;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}