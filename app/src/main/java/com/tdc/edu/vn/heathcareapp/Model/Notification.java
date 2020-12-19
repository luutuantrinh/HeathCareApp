package com.tdc.edu.vn.heathcareapp.Model;

public class Notification {
    String id_notification, user_id, timestamp;
    Boolean seen;

    public Notification() {

    }

    public Notification(String id_notification, String user_id, String timestamp, Boolean seen) {
        this.id_notification = id_notification;
        this.user_id = user_id;
        this.timestamp = timestamp;
        this.seen = seen;
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
