package com.coffee_secrets.obj;

public class Inquiry {

    private String userid;
    private String inquiry;

    public Inquiry(){

    }

    public Inquiry(String userid, String inquiry) {
        this.userid = userid;
        this.inquiry = inquiry;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getInquiry() {
        return inquiry;
    }

    public void setInquiry(String inquiry) {
        this.inquiry = inquiry;
    }
}
