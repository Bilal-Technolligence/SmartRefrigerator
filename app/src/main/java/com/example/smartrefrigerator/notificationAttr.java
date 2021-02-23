package com.example.smartrefrigerator;

public class notificationAttr {
    String description;
    String title;
    String datetime;
    String id;

    public notificationAttr() {
    }

    public notificationAttr(String description, String title, String datetime, String id) {
        this.description = description;
        this.title = title;
        this.datetime = datetime;
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
