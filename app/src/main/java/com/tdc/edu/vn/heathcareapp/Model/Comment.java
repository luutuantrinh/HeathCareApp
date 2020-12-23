package com.tdc.edu.vn.heathcareapp.Model;

public class Comment {
    String id_comment, id_post, user_id, content_cmt, timestamp;

    public Comment() {

    }

    public Comment(String id_comment, String id_post, String user_id, String content_cmt, String timestamp) {
        this.id_comment = id_comment;
        this.id_post = id_post;
        this.user_id = user_id;
        this.content_cmt = content_cmt;
        this.timestamp = timestamp;
    }

    public String getId_comment() {
        return id_comment;
    }

    public void setId_comment(String id_comment) {
        this.id_comment = id_comment;
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

    public String getContent_cmt() {
        return content_cmt;
    }

    public void setContent_cmt(String content_cmt) {
        this.content_cmt = content_cmt;
    }


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
