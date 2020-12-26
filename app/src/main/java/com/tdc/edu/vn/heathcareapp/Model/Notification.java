package com.tdc.edu.vn.heathcareapp.Model;

public class Notification {
    String id_notification, user_id, from_user_id, content, post_id, url, timestamp;
    Boolean seen;

    public Notification() {

    }

    public Notification(String id_notification, String user_id, String from_user_id, String content, String post_id, String url, String timestamp, Boolean seen) {
        this.id_notification = id_notification;
        this.user_id = user_id;
        this.from_user_id = from_user_id;
        this.content = content;
        this.post_id = post_id;
        this.url = url;
        this.timestamp = timestamp;
        this.seen = seen;
    }

    public String getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(String from_user_id) {
        this.from_user_id = from_user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId_notification() {
        return id_notification;
    }

    public void setId_notification(String id_notification) {
        this.id_notification = id_notification;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }
}
