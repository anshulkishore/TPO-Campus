package com.example.anshul.tpocampus.UserData;

/**
 * Created by ANSHUL KISHORE on 08-10-2017.
 */

public class AddUserPersonalData {
    String batch, name, regno, course, branch, dob, email, gender, category, phy_challenged, res_status, guardian, present_addr,
            parmanent_addr, marital_status, state, country, tpo_credits, verified, intern_placement, locked;

    public AddUserPersonalData(){}

    public AddUserPersonalData(String batch, String name, String regno, String course, String branch, String dob, String email,
                               String gender, String category, String phy_challenged, String res_status, String guardian,
                               String present_addr, String parmanent_addr, String marital_status, String state, String country,
                               String tpo_credits, String verified, String intern_placement, String locked){

        this.batch = batch;
        this.name = name;
        this.regno = regno;
        this.course = course;
        this.branch = branch;
        this.dob = dob;
        this.email = email;
        this.gender = gender;
        this.category = category;
        this.phy_challenged = phy_challenged;
        this.res_status = res_status;
        this.guardian = guardian;
        this.present_addr = present_addr;
        this.parmanent_addr = parmanent_addr;
        this.marital_status = marital_status;
        this.state = state;
        this.country = country;
        this.tpo_credits = tpo_credits;
        this.verified = verified;
        this.intern_placement = intern_placement;
        this.locked = locked;

    }

    public String getBatch(){
        return batch;
    }
    public String getName(){
        return name;
    }
    public String getRegno(){
        return regno;
    }
    public String getCourse(){
        return course;
    }
    public String getBranch(){
        return branch;
    }
    public String getDob(){
        return dob;
    }
    public String get_Email(){
        return email;
    }
    public String getGender(){
        return gender;
    }
    public String getCategory(){
        return category;
    }
    public String getPhy_challenged(){
        return phy_challenged;
    }
    public String getRes_status(){
        return res_status;
    }
    public String getGuardian(){
        return guardian;
    }
    public String getPresent_addr(){
        return present_addr;
    }
    public String getParmanent_addr(){
        return parmanent_addr;
    }
    public String getMarital_status(){
        return marital_status;
    }
    public String getState(){
        return state;
    }
    public String getCountry(){
        return country;
    }
    public String getTpoCredits(){
        return tpo_credits;
    }
    public String getVerified(){
        return verified;
    }
    public String getIntern_placement() {return intern_placement;}
    public String getLocked() {return locked;}

    public void setBatch(String batch){
        this.batch = batch;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setRegno(String regno){
        this.regno = regno;
    }
    public void setCourse(String course){
        this.course = course;
    }
    public void setBranch(String branch){
        this.branch = branch;
    }
    public void setDob(String dob){
        this.dob = dob;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setGender(String gender){
        this.gender = gender;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public void setPhy_challenged(String phy_challenged){
        this.phy_challenged = phy_challenged;
    }
    public void setRes_status(String res_status){
        this.res_status = res_status;
    }
    public void setGuardian(String guardian) {
        this.guardian = guardian;
    }
    public void setPresent_addr(String present_addr){
        this.present_addr = present_addr;
    }
    public void setParmanent_addr(String parmanent_addr){
        this.parmanent_addr = parmanent_addr;
    }
    public void setMarital_status(String marital_status) {
        this.marital_status = marital_status;
    }
    public void setState(String state){
        this.state = state;
    }
    public void setCountry(String country){
        this.country = country;
    }
    public void setTpoCredits(String tpo_credits){
        this.tpo_credits = tpo_credits;
    }
    public void setVerified(String verified){
        this.verified = verified;
    }
    public void setIntern_placement(String intern_placement) {this.intern_placement = intern_placement;}
    public void setLocked(String locked) {this.locked = locked;}

}
