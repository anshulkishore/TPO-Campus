package com.example.anshul.tpocampus.InterviewExperience;

/**
 * Created by ANSHUL KISHORE on 19-12-2017.
 */

public class ItemExp {

    private String company_name, student_name, year, experience_details;

    public ItemExp(String company_name, String student_name, String year, String experience_details) {
        this.company_name = company_name;
        this.student_name = student_name;
        this.year = year;
        this.experience_details = experience_details;
    }

    public ItemExp() {}

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getExperience_details() {return experience_details;}

    public void setExperience_details(String experience_details) {this.experience_details = experience_details;}
}
