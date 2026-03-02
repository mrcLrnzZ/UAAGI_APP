package com.example.uaagi_app.network.dto.PreEmpDto;

import com.google.gson.annotations.SerializedName;

public class Qualification {
    @SerializedName("qual_id")
    private String qualId;
    @SerializedName("preemp_id")
    private String preempId;
    private String type;
    private String title;
    private String authority;
    @SerializedName("issue_date")
    private String issueDate;
    private String description;

    public Qualification(String qualId, String preempId, String type, String title, String authority, String issueDate, String description) {
        this.qualId = qualId != null ? qualId : "";
        this.preempId = preempId != null ? preempId : "";
        this.type = type != null ? type : "";
        this.title = title != null ? title : "";
        this.authority = authority != null ? authority : "";
        this.issueDate = issueDate != null ? issueDate : "";
        this.description = description != null ? description : "";
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

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQualId() {
        return qualId;
    }

    public void setQualId(String qualId) {
        this.qualId = qualId;
    }

    public String getPreempId() {
        return preempId;
    }

    public void setPreempId(String preempId) {
        this.preempId = preempId;
    }
}