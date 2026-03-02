package com.example.uaagi_app.network.dto.PreEmpDto;

import com.google.gson.annotations.SerializedName;

public class GovermentId {
    @SerializedName("gov_id")
    private String govId;
    @SerializedName("preemp_id")
    private String preempId;
    @SerializedName("gov_type")
    private String type;
    @SerializedName("gov_number")
    private String number;

    public GovermentId(String govId, String preempId, String type, String number) {
        this.govId = govId != null ? govId : "";
        this.preempId = preempId != null ? preempId : "";
        this.type = type != null ? type : "";
        this.number = number != null ? number : "";
    }

    public String getGovId() {
        return govId;
    }

    public void setGovId(String govId) {
        this.govId = govId;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
