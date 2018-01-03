package com.example.anshul.tpocampus.Admin;

/**
 * Created by ANSHUL KISHORE on 10-12-2017.
 */

public class ManageStudentInfo {

    private String tpoCredits, verified, locked, intern_placement;

    public ManageStudentInfo(String tpoCredits, String verified, String locked, String intern_placement) {
        this.tpoCredits = tpoCredits;
        this.verified = verified;
        this.locked = locked;
        this.intern_placement = intern_placement;
    }

    public ManageStudentInfo() {}

    public String getTpoCredits() {
        return tpoCredits;
    }

    public void setTpoCredits(String tpoCredits) {
        this.tpoCredits = tpoCredits;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public String getIntern_placement() {
        return intern_placement;
    }

    public void setIntern_placement(String intern_placement) {
        this.intern_placement = intern_placement;
    }
}
