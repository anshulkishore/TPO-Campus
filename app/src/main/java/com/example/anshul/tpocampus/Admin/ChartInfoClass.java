package com.example.anshul.tpocampus.Admin;

/**
 * Created by ANSHUL KISHORE on 17-12-2017.
 */

public class ChartInfoClass {

    String got, total;

    public ChartInfoClass(String got, String total) {
        this.got = got;
        this.total = total;
    }

    public ChartInfoClass() {}

    public String getGot() {return got;}

    public void setGot(String got) {this.got = got;}

    public String getTotal() {return total;}

    public void setTotal(String total) {this.total = total;}
}
