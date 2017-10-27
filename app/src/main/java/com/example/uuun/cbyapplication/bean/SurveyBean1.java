package com.example.uuun.cbyapplication.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by uuun on 2017/8/11.
 */

public class SurveyBean1 implements Serializable{

    /**
     * code : 0
     * message : 成功
     * data : {"content":[{"id":2,"name":"物流调查1","bonus":2.5,"sex":1,"age":"30-50岁","city":"北京","recent":1,"questions":5,"count":3000,"createTime":null,"time":null},{"id":3,"name":"物流调查2","bonus":2,"sex":1,"age":"30-50岁","city":"北京","recent":1,"questions":5,"count":3000,"createTime":null,"time":null},{"id":4,"name":"物流调查3","bonus":1,"sex":1,"age":"30-50岁","city":"北京","recent":1,"questions":5,"count":3000,"createTime":null,"time":null}],"last":false,"totalElements":9,"totalPages":3,"number":0,"size":3,"sort":null,"first":true,"numberOfElements":3}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * content : [{"id":2,"name":"物流调查1","bonus":2.5,"sex":1,"age":"30-50岁","city":"北京","recent":1,"questions":5,"count":3000,"createTime":null,"time":null},{"id":3,"name":"物流调查2","bonus":2,"sex":1,"age":"30-50岁","city":"北京","recent":1,"questions":5,"count":3000,"createTime":null,"time":null},{"id":4,"name":"物流调查3","bonus":1,"sex":1,"age":"30-50岁","city":"北京","recent":1,"questions":5,"count":3000,"createTime":null,"time":null}]
         * last : false
         * totalElements : 9
         * totalPages : 3
         * number : 0
         * size : 3
         * sort : null
         * first : true
         * numberOfElements : 3
         */

        private boolean last;
        private int totalElements;
        private int totalPages;
        private int number;
        private int size;
        private Object sort;
        private boolean first;
        private int numberOfElements;
        private List<ContentBean> content;

        public boolean isLast() {
            return last;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public Object getSort() {
            return sort;
        }

        public void setSort(Object sort) {
            this.sort = sort;
        }

        public boolean isFirst() {
            return first;
        }

        public void setFirst(boolean first) {
            this.first = first;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }
        @Table(name = "survey")
        public static class ContentBean implements Serializable{
            /**
             * id : 2
             * name : 物流调查1
             * bonus : 2.5
             * sex : 1
             * age : 30-50岁
             * city : 北京
             * recent : 1
             * questions : 5
             * count : 3000
             * createTime : null
             * time : null
             */
            @Column(name = "id",isId = true,autoGen = false)
            private int id;
            @Column(name = "name")
            private String name;
            @Column(name = "bonus")
            private double bonus;
            private int sex;
            private String age;
            private String city;
            private int recent;
            @Column(name = "questions")
            private int questions;
            private int count;
            private Long createTime;
            private Long time;

            public int getId() {
                return id;
            }

            public void setId(int id) {
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

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
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

            public int getRecent() {
                return recent;
            }

            public void setRecent(int recent) {
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

            public Object getTime() {
                return time;
            }

            public void setTime(Long time) {
                this.time = time;
            }

            @Override
            public String toString() {
                return "ContentBean{" +
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
    }
}
