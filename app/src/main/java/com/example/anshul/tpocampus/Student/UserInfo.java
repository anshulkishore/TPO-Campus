package com.example.anshul.tpocampus.Student;

/**
 * Created by ANSHUL KISHORE on 10-10-2017.
 */

public class UserInfo {

    public UserInfo(){}

    String name;
    String regno;
    String course;
    String branch;
    String verified;
    String tpoCredits;
    String intern_placement;
    String _Email;

    public String getIntern_placement() {return intern_placement;}

    public void setIntern_placement(String intern_placement) {this.intern_placement = intern_placement;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getTpocredits() {
        return tpoCredits;
    }

    public void setTpocredits(String tpocredits) {
        this.tpoCredits = tpocredits;
    }

    public String get_Email() {
        return _Email;
    }

    public void set_Email(String _Email) {
        this._Email = _Email;
    }
}

