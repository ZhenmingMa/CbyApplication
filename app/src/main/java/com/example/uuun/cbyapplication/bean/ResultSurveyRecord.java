package com.example.uuun.cbyapplication.bean;

import java.util.List;

/**
 * Created by Ma on 2017/6/22.
 */

public class ResultSurveyRecord {
    private List<SurveyRecord> list;

    public List<SurveyRecord> getList() {
        return list;
    }

    public void setList(List<SurveyRecord> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ResultSurveyRecord{" +
                "list=" + list +
                '}';
    }
}
