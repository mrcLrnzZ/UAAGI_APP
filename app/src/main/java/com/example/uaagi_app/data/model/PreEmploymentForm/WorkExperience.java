package com.example.uaagi_app.data.model.PreEmploymentForm;

public class WorkExperience {
    private String company;
    private String position;
    private String startDate;
    private String endDate;
    private String description;

    public WorkExperience(String company, String position, String startDate, String endDate, String description) {
        this.company = company != null ? company : "";
        this.position = position != null ? position : "";
        this.startDate = startDate != null ? startDate : "";
        this.endDate = endDate != null ? endDate : "";
        this.description = description != null ? description : "";
    }
    public WorkExperience() {
        this.company = "";
        this.position = "";
        this.startDate = "";
        this.endDate = "";
        this.description = "";
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

