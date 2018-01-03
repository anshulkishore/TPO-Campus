package com.example.anshul.tpocampus.Student;

/**
 * Created by ANSHUL KISHORE on 11-12-2017.
 */

public class StudentCompanyClassFinalYear {

    String comp_name, comp_profile, comp_package, comp_location;

    public StudentCompanyClassFinalYear(String comp_name, String comp_profile, String comp_package, String comp_location) {
        this.comp_name = comp_name;
        this.comp_profile = comp_profile;
        this.comp_package = comp_package;
        this.comp_location = comp_location;
    }

    public StudentCompanyClassFinalYear() {}

    public String getComp_name() {
        return comp_name;
    }

    public void setComp_name(String comp_name) {
        this.comp_name = comp_name;
    }

    public String getComp_profile() {
        return comp_profile;
    }

    public void setComp_profile(String comp_profile) {
        this.comp_profile = comp_profile;
    }

    public String getComp_package() {
        return comp_package;
    }

    public void setComp_package(String comp_package) {
        this.comp_package = comp_package;
    }

    public String getComp_location() {
        return comp_location;
    }

    public void setComp_location(String comp_location) {
        this.comp_location = comp_location;
    }
}
