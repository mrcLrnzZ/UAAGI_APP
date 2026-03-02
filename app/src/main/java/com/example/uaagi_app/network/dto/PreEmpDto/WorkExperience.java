package com.example.uaagi_app.network.dto.PreEmpDto;

import com.google.gson.annotations.SerializedName;

public class WorkExperience {
    @SerializedName("work_id")
    private String workId;
    @SerializedName("preemp_id")
    private String preempId;
    private String company;
    private String position;
    @SerializedName("start_date")
    private String startDate;
    @SerializedName("end_date")
    private String endDate;
    private String description;

    public WorkExperience(String workId, String preempId, String company, String position, String startDate, String endDate, String description) {
        this.workId = workId != null ? workId : "";
        this.preempId = preempId != null ? preempId : "";
        this.company = company != null ? company : "";
        this.position = position != null ? position : "";
        this.startDate = startDate != null ? startDate : "";
        this.endDate = endDate != null ? endDate : "";
        this.description = description != null ? description : "";
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getPreempId() {
        return preempId;
    }

    public void setPreempId(String preempId) {
        this.preempId = preempId;
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