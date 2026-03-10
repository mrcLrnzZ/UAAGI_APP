package com.example.uaagi_app.network.dto.PreEmpDto;

import com.google.gson.annotations.SerializedName;

public class Education {
    @SerializedName("edu_id")
    private String eduId;
    @SerializedName("preemp_id")
    private String preempId;
    private String level;
    private String school;
    @SerializedName("grad_year")
    private String gradYear;
    private String achievement;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("status")
    private String status;

    public Education(String eduId, String preempId, String level, String school, String gradYear, String achievement, String createdAt, String status) {
        this.eduId = eduId != null ? eduId : "";
        this.preempId = preempId != null ? preempId : "";
        this.level = level != null ? level : "";
        this.school = school != null ? school : "";
        this.gradYear = gradYear != null ? gradYear : "";
        this.achievement = achievement != null ? achievement : "";
        this.createdAt = createdAt != null ? createdAt : "";
        this.status = status != null ? status : "";
    }

    public String getEduId() {
        return eduId;
    }

    public void setEduId(String eduId) {
        this.eduId = eduId;
    }

    public String getPreempId() {
        return preempId;
    }

    public void setPreempId(String preempId) {
        this.preempId = preempId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getGradYear() {
        return gradYear;
    }

    public void setGradYear(String gradYear) {
        this.gradYear = gradYear;
    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
