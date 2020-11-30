package com.tdc.edu.vn.heathcareapp.Model;

public class NutritionTower {
    int id_nt;
    String title_nt, content_nt;

    public NutritionTower(int id_nt, String title_nt, String content_nt) {
        this.id_nt = id_nt;
        this.title_nt = title_nt;
        this.content_nt = content_nt;
    }

    public int getId_nt() {
        return id_nt;
    }

    public void setId_nt(int id_nt) {
        this.id_nt = id_nt;
    }

    public String getTitle_nt() {
        return title_nt;
    }

    public void setTitle_nt(String title_nt) {
        this.title_nt = title_nt;
    }

    public String getContent_nt() {
        return content_nt;
    }

    public void setContent_nt(String content_nt) {
        this.content_nt = content_nt;
    }
}
