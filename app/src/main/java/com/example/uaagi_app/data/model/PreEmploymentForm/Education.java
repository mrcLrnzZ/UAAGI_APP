package com.example.uaagi_app.data.model.PreEmploymentForm;

public class Education {
    private String level;
    private String school;
    private String gradYear;
    private String status;
    private String achievement;
    public Education() {
        this.level = "";
        this.school = "";
        this.gradYear = "";
        this.status = "";
        this.achievement = "";
    }
    public Education(String level, String school, String gradYear, String status, String achievement) {
        this.level = level != null ? level : "";
        this.school = school != null ? school : "";
        this.gradYear = gradYear != null ? gradYear : "";
        this.status = status != null ? status : "";
        this.achievement = achievement != null ? achievement : "";
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }
}
