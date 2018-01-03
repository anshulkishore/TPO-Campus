package com.example.anshul.tpocampus.Student;

/**
 * Created by ANSHUL KISHORE on 15-10-2017.
 */

public class ModelClass {
    String regno, name, branch, course, batch;



    public ModelClass(String regno, String name, String branch, String course, String batch) {
        this.regno = regno;
        this.name = name;
        this.branch = branch;
        this.course = course;
        this.batch = batch;
    }

    public ModelClass() {
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
