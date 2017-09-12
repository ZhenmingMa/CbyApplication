package com.example.uuun.cbyapplication.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * //未完成的调研内容
 * Created by uuun on 2017/6/21.
 */
@Table(name = "Unfinish")
public class Unfinish {
    @Column(name = "id",isId = true,autoGen = false)
    private int id;
    @Column(name = "content")
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Unfinish() {
    }

    public Unfinish(int id, String content) {
        this.id = id;
        this.content = content;
    }
}
