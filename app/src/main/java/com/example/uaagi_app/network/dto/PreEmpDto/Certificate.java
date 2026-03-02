package com.example.uaagi_app.network.dto.PreEmpDto;

import com.google.gson.annotations.SerializedName;

public class Certificate {
    @SerializedName("cert_id")
    private String certId;
    @SerializedName("preemp_id")
    private String preempId;
    private String name;
    private String organization;
    @SerializedName("issue_date")
    private String issueDate;
    @SerializedName("expiry_date")
    private String expiryDate;
    private String description;

    public Certificate(String certId, String preempId, String name, String organization, String issueDate, String expiryDate, String description) {
        this.certId = certId != null ? certId : "";
        this.preempId = preempId != null ? preempId : "";
        this.name = name != null ? name : "";
        this.organization = organization != null ? organization : "";
        this.issueDate = issueDate != null ? issueDate : "";
        this.expiryDate = expiryDate != null ? expiryDate : "";
        this.description = description != null ? description : "";
    }

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
    }

    public String getPreempId() {
        return preempId;
    }

    public void setPreempId(String preempId) {
        this.preempId = preempId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}