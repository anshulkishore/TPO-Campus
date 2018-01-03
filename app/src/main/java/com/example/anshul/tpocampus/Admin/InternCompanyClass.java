package com.example.anshul.tpocampus.Admin;

/**
 * Created by ANSHUL KISHORE on 21-10-2017.
 */

public class InternCompanyClass {

    String comp_name, comp_profile, comp_stipend, comp_location;

    public InternCompanyClass(String comp_name, String comp_profile, String comp_stipend, String comp_location) {
        this.comp_name = comp_name;
        this.comp_profile = comp_profile;
        this.comp_stipend = comp_stipend;
        this.comp_location = comp_location;
    }

    public InternCompanyClass() {
    }

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

    public String getComp_stipend() {
        return comp_stipend;
    }

    public void setComp_stipend(String comp_stipend) {
        this.comp_stipend = comp_stipend;
    }

    public String getComp_location() {
        return comp_location;
    }

    public void setComp_location(String comp_location) {
        this.comp_location = comp_location;
    }
}
