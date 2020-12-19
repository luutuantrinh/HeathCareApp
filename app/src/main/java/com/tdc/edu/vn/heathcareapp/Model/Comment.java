package com.tdc.edu.vn.heathcareapp.Model;

public class Comment {
    int id_cmt, user_id;
    String post_id, content_cmt, createAt_cmt;

    public Comment(int id_cmt, int user_id, String content_cmt, String createAt_cmt) {
        this.id_cmt = id_cmt;
        this.user_id = user_id;
        this.content_cmt = content_cmt;
        this.createAt_cmt = createAt_cmt;
    }

    public int getId_cmt() {
        return id_cmt;
    }

    public void setId_cmt(int id_cmt) {
        this.id_cmt = id_cmt;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getContent_cmt() {
        return content_cmt;
    }

    public void setContent_cmt(String content_cmt) {
        this.content_cmt = content_cmt;
    }

    public String getCreateAt_cmt() {
        return createAt_cmt;
    }

    public void setCreateAt_cmt(String createAt_cmt) {
        this.createAt_cmt = createAt_cmt;
    }
}
