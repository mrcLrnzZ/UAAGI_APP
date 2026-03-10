package com.example.uaagi_app.data.model.PreEmploymentForm;

public class Qualification {
    private String type;
    private String title;
    private String authority;
    private String date;
    private String description;

    public Qualification(String type, String title, String authority, String date, String description) {
        this.type = type != null ? type : "";
        this.title = title != null ? title : "";
        this.authority = authority != null ? authority : "";
        this.date = date != null ? date : "";
        this.description = description != null ? description : "";
    }
    public Qualification() {
        this.type = "";
        this.title = "";
        this.authority = "";
        this.date = "";
        this.description = "";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

