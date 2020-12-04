package com.tdc.edu.vn.heathcareapp.data_model;

public class WorkoutGuided {
    private  int resourceID;
    private String sTitle;
    private String sDes;

    public WorkoutGuided(int resourceID, String sTitle, String sDes) {
        this.setResourceID(resourceID);
        this.setsTitle(sTitle);
        this.setsDes(sDes);
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

    public String getsDes() {
        return sDes;
    }

    public void setsDes(String sDes) {
        this.sDes = sDes;
    }
}
