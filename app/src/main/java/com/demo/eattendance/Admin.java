package com.demo.eattendance;

public class Admin {

    String aid;
    String apass;

    public Admin(String aid, String apass) {
        this.aid = aid;
        this.apass = apass;
    }

    public Admin() {
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getApass() {
        return apass;
    }

    public void setApass(String apass) {
        this.apass = apass;
    }

}
