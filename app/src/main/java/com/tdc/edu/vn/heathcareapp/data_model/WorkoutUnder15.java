package com.tdc.edu.vn.heathcareapp.data_model;

public class WorkoutUnder15 {
    private String url;
    private String sTitle;
    private String sDes;

    public WorkoutUnder15() {

    }

    public WorkoutUnder15(String url, String sTitle, String sDes) {
        this.url = url;
        this.sTitle = sTitle;
        this.sDes = sDes;
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

    public String getsDes() {
        return sDes;
    }

    public void setsDes(String sDes) {
        this.sDes = sDes;
    }
}
