package com.tdc.edu.vn.heathcareapp.Model;

public class AllExercises {
    private  String url;
    private String sTitle;


    public AllExercises() {
    }

    public AllExercises(String url, String sTitle) {
        this.url = url;
        this.sTitle = sTitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getsTitle() {
        return sTitle;
    }

    public void setsTitle(String sTitle) {
        this.sTitle = sTitle;
    }
}
