package com.example.uaagi_app.network.dto.PreEmpDto;

import com.google.gson.annotations.SerializedName;

public class ContactReference {
    @SerializedName("con_ref_id")
    private String conRefId;
    @SerializedName("preemp_id")
    private String preempId;
    private String name;
    private String occupation;
    private String company;
    private String phone;

    public ContactReference(String conRefId, String preempId, String name, String occupation, String company, String phone) {
        this.conRefId = conRefId != null ? conRefId : "";
        this.preempId = preempId != null ? preempId : "";
        this.name = name != null ? name : "";
        this.occupation = occupation != null ? occupation : "";
        this.company = company != null ? company : "";
        this.phone = phone != null ? phone : "";
    }

    public String getConRefId() {
        return conRefId;
    }

    public void setConRefId(String conRefId) {
        this.conRefId = conRefId;
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

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
