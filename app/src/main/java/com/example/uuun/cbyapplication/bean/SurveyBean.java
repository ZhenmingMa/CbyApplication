package com.example.uuun.cbyapplication.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by uuun on 2017/6/13.
 */
@Table(name = "survey")
public class SurveyBean implements Serializable {
    @Column(name = "id",isId = true,autoGen = false)
    public Integer id;
    @Column(name = "name")
    private String name;    //名字\
    @Column(name = "bonus")
    private double bonus;   //奖金
    private Byte sex;
    private String age;
    private String city;
    private Byte recent;  //最近是否参与过此类调研
    @Column(name = "questions")
    private int questions; //问题数量
    private int count;     //剩余席位
    private Long createTime; //创建时间
    private Long time;     //更新时间

    public SurveyBean() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Byte getRecent() {
        return recent;
    }

    public void setRecent(Byte recent) {
        this.recent = recent;
    }

    public int getQuestions() {
        return questions;
    }

    public void setQuestions(int questions) {
        this.questions = questions;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "SurveyBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bonus=" + bonus +
                ", sex=" + sex +
                ", age='" + age + '\'' +
                ", city='" + city + '\'' +
                ", recent=" + recent +
                ", questions=" + questions +
                ", count=" + count +
                ", createTime=" + createTime +
                ", time=" + time +
                '}';
    }
}
