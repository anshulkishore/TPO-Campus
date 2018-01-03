package com.example.anshul.tpocampus.InterviewExperience;

/**
 * Created by ANSHUL KISHORE on 19-12-2017.
 */

public class InterviewExpInfo {

    private String name, company_name, year_of_interview, exp;

    public InterviewExpInfo(String name, String company_name, String year_of_interview, String exp) {
        this.name = name;
        this.company_name = company_name;
        this.year_of_interview = year_of_interview;
        this.exp = exp;
    }

    public InterviewExpInfo() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getYear_of_interview() {
        return year_of_interview;
    }

    public void setYear_of_interview(String year_of_interview) {
        this.year_of_interview = year_of_interview;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }
}
