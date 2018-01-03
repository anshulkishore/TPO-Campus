package com.example.anshul.tpocampus.Admin;

/**
 * Created by ANSHUL KISHORE on 22-10-2017.
 */

public class AddFulltimeCompanyInfo {

    private String name, profile, cutoff_cpi, salary, location, reg_deadline, reg_deadline_time, test_date,
            cse, it, ece, ee, mech, pie, civil, chem, bio, mca, is_completed, additional_info;

    public AddFulltimeCompanyInfo(String name, String profile, String cutoff_cpi, String salary, String location, String reg_deadline,
                                  String reg_deadline_time, String test_date, String cse, String it, String ece, String ee, String mech,
                                  String pie, String civil, String chem, String bio, String mca, String is_completed,
                                  String additional_info) {
        this.name = name;
        this.profile = profile;
        this.cutoff_cpi = cutoff_cpi;
        this.salary = salary;
        this.location = location;
        this.reg_deadline = reg_deadline;
        this.reg_deadline_time = reg_deadline_time;
        this.test_date = test_date;
        this.cse = cse;
        this.it = it;
        this.ece = ece;
        this.ee = ee;
        this.mech = mech;
        this.pie = pie;
        this.civil = civil;
        this.chem = chem;
        this.bio = bio;
        this.mca = mca;
        this.is_completed = is_completed;
        this.additional_info = additional_info;
    }

    public AddFulltimeCompanyInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getCutoff_cpi() {
        return cutoff_cpi;
    }

    public void setCutoff_cpi(String cutoff_cpi) {
        this.cutoff_cpi = cutoff_cpi;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReg_deadline() {
        return reg_deadline;
    }

    public void setReg_deadline(String reg_deadline) {
        this.reg_deadline = reg_deadline;
    }

    public String getReg_deadline_time() {return reg_deadline_time;}

    public void setReg_deadline_time(String reg_deadline_time) {this.reg_deadline_time = reg_deadline_time;}

    public String getTest_date() {
        return test_date;
    }

    public void setTest_date(String test_date) {
        this.test_date = test_date;
    }

    public String getCse() {
        return cse;
    }

    public void setCse(String cse) {
        this.cse = cse;
    }

    public String getIt() {
        return it;
    }

    public void setIt(String it) {
        this.it = it;
    }

    public String getEce() {
        return ece;
    }

    public void setEce(String ece) {
        this.ece = ece;
    }

    public String getEe() {
        return ee;
    }

    public void setEe(String ee) {
        this.ee = ee;
    }

    public String getMech() {
        return mech;
    }

    public void setMech(String mech) {
        this.mech = mech;
    }

    public String getPie() {
        return pie;
    }

    public void setPie(String pie) {
        this.pie = pie;
    }

    public String getCivil() {
        return civil;
    }

    public void setCivil(String civil) {
        this.civil = civil;
    }

    public String getChem() {
        return chem;
    }

    public void setChem(String chem) {
        this.chem = chem;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getMca() {
        return mca;
    }

    public void setMca(String mca) {
        this.mca = mca;
    }

    public String getIs_completed() {
        return is_completed;
    }

    public void setIs_completed(String is_completed) {
        this.is_completed = is_completed;
    }

    public String getAdditional_info() {return additional_info;}

    public void setAdditional_info(String additional_info) {this.additional_info = additional_info;}
}
