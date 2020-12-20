package com.tdc.edu.vn.heathcareapp.Model;

public class Like {
    String id_like, id_post, id_user, timestamp;

    public Like(String id_like, String id_post, String id_user, String timestamp) {
        this.id_like = id_like;
        this.id_post = id_post;
        this.id_user = id_user;
        this.timestamp = timestamp;
    }

    public Like() {

    }

    public String getId_like() {
        return id_like;
    }

    public void setId_like(String id_like) {
        this.id_like = id_like;
    }

    public String getId_post() {
        return id_post;
    }

    public void setId_post(String id_post) {
        this.id_post = id_post;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Like{" +
                "id_like='" + id_like + '\'' +
                ", id_post='" + id_post + '\'' +
                ", id_user='" + id_user + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
