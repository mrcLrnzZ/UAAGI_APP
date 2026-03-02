package com.example.uaagi_app.network.dto.PreEmpDto;

import com.google.gson.annotations.SerializedName;

public class Seminar {
    @SerializedName("seminar_id")
    private String seminarId;
    @SerializedName("preemp_id")
    private String preempId;
    private String type;
    private String title;
    private String organizer;
    private String date;
    private String description;

    public Seminar(String seminarId, String preempId, String type, String title, String organizer, String date, String description) {
        this.seminarId = seminarId != null ? seminarId : "";
        this.preempId = preempId != null ? preempId : "";
        this.type = type != null ? type : "";
        this.title = title != null ? title : "";
        this.organizer = organizer != null ? organizer : "";
        this.date = date != null ? date : "";
        this.description = description != null ? description : "";
    }

    public String getSeminarId() {
        return seminarId;
    }

    public void setSeminarId(String seminarId) {
        this.seminarId = seminarId;
    }

    public String getPreempId() {
        return preempId;
    }

    public void setPreempId(String preempId) {
        this.preempId = preempId;
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

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
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
