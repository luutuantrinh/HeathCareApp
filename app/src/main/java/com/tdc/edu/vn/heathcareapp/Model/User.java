package com.tdc.edu.vn.heathcareapp.Model;

public class User {
    int user_id, createAt, age, sex;
    String first_name, last_name, image_id, email, location;


    public User() {

    }

    public User(int user_id, String first_name, String last_name, int sex, int age, String email, String image_id, String location, int createAt) {
        this.user_id = user_id;
        this.createAt = createAt;
        this.age = age;
        this.sex = sex;
        this.first_name = first_name;
        this.last_name = last_name;
        this.image_id = image_id;
        this.email = email;
        this.location = location;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCreateAt() {
        return createAt;
    }

    public void setCreateAt(int createAt) {
        this.createAt = createAt;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
