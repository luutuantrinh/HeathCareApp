package com.tdc.edu.vn.heathcareapp.Model;

public class Follow {
    String id_follow, sender, receiver, timestamp;
    Boolean request_status;

    public Follow() {

    }

    public Follow(String id_follow, String sender, String receiver, String timestamp, Boolean request_status) {
        this.id_follow = id_follow;
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = timestamp;
        this.request_status = request_status;
    }

    public String getId_follow() {
        return id_follow;
    }

    public void setId_follow(String id_follow) {
        this.id_follow = id_follow;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getRequest_status() {
        return request_status;
    }

    public void setRequest_status(Boolean request_status) {
        this.request_status = request_status;
    }
}
