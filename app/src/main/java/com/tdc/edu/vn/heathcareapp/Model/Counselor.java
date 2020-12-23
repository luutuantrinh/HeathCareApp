package com.tdc.edu.vn.heathcareapp.Model;

public class Counselor {
    int id_counselor;
    String name_counselor, des_counselor;

    public Counselor(int id_counselor, String name_counselor, String des_counselor) {
        this.id_counselor = id_counselor;
        this.name_counselor = name_counselor;
        this.des_counselor = des_counselor;
    }

    public int getId_counselor() {
        return id_counselor;
    }

    public void setId_counselor(int id_counselor) {
        this.id_counselor = id_counselor;
    }

    public String getName_counselor() {
        return name_counselor;
    }

    public void setName_counselor(String name_counselor) {
        this.name_counselor = name_counselor;
    }

    public String getDes_counselor() {
        return des_counselor;
    }

    public void setDes_counselor(String des_counselor) {
        this.des_counselor = des_counselor;
    }
}
