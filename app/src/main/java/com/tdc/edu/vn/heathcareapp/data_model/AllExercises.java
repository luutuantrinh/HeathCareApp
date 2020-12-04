package com.tdc.edu.vn.heathcareapp.data_model;

public class AllExercises {
    private  int resourceID;
    private String sTitle;


    public AllExercises(int resourceID, String sTitle) {
        this.setResourceID(resourceID);
        this.setsTitle(sTitle);

    }

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public String getsTitle() {
        return sTitle;
    }

    public void setsTitle(String sTitle) {
        this.sTitle = sTitle;
    }

}
