package com.demo.eattendance;

import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by admin on 9/2/2018.
 */

public class Student {
    String sname;
    String sid;
    String spass;
    String classes;

    public Student(){

    }

    public Student(String sname, String sid,String classes,String spass) {
        this.sname = sname;
        this.sid = sid;
        this.classes = classes;
        this.spass = spass;
    }

    public String getSname() {
        return sname;
    }

    public String getSid() {
        return sid;
    }
    public String getClasses() {
        return classes;
    }

    public String getspass() {
        return spass;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public void setSpass(String spass) {
        this.spass = spass;
    }
}
