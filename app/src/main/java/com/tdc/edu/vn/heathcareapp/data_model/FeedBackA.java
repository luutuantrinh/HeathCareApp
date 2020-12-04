package com.tdc.edu.vn.heathcareapp.data_model;

public class FeedBackA {
    private  String sNameCheck;
    private  boolean ischeck = false;

    public FeedBackA(String sNameCheck, boolean ischeck) {
        this.sNameCheck = sNameCheck;
        this.setIscheck(ischeck);
    }

    public void setsNameCheck(String sNameCheck) {
        this.sNameCheck = sNameCheck;
    }


    public String getsNameCheck() {
        return sNameCheck;
    }


    public boolean isIscheck() {
        return ischeck;
    }


    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }
}
