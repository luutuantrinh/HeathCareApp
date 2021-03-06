package com.tdc.edu.vn.heathcareapp.Model;

public class Post {
    private String id_post, user_id, image_id, content_post, day_create;
    private int total_like;

    public Post() {

    }

    public Post(String id_post, String user_id, String image_id, String content_post, String day_create, int total_like) {
        this.id_post = id_post;
        this.user_id = user_id;
        this.image_id = image_id;
        this.content_post = content_post;
        this.day_create = day_create;
        this.total_like = total_like;
    }

    public String getId_post() {
        return id_post;
    }

    public void setId_post(String id_post) {
        this.id_post = id_post;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getContent_post() {
        return content_post;
    }

    public void setContent_post(String content_post) {
        this.content_post = content_post;
    }

    public String getDay_create() {
        return day_create;
    }

    public void setDay_create(String day_create) {
        this.day_create = day_create;
    }

    public int getTotal_like() {
        return total_like;
    }

    public void setTotal_like(int total_like) {
        this.total_like = total_like;
    }
}
