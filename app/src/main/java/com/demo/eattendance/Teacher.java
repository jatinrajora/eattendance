package com.demo.eattendance;

/**
 * Created by admin on 8/2/2018.
 */

public class Teacher {
    String tname;
    String tid;
    String subject;
    String classes;
    String tpass;

    public Teacher(String tname, String tid, String subject, String classes, String tpass) {
        this.tname = tname;
        this.tid = tid;
        this.subject = subject;
        this.classes = classes;
        this.tpass = tpass;
    }

    public Teacher() {
    }

    public String getTname() {
        return tname;
    }

    public String getTid() {
        return tid;
    }

    public String getSubject() {
        return subject;
    }

    public String getClasses() {
        return classes;
    }

    public String gettpass() {
        return tpass;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public void setTpass(String tpass) {
        this.tpass = tpass;
    }
}
