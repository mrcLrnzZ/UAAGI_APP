package com.example.uaagi_app.network.dto.JobEnums;

import com.google.gson.annotations.SerializedName;

public enum Status {
    @SerializedName("Active")
    ACTIVE("Active"),
    @SerializedName("Closed")
    CLOSED("Closed"),
    @SerializedName("Paused")
    PAUSED("Paused");
    private final String displayName;
    Status(String displayName){
        this.displayName = displayName;
    }
    public String getDisplayName(){
        return displayName;
    }
    public static Status fromString(String value) {
        for (Status c : Status.values()) {
            if (c.getDisplayName().equalsIgnoreCase(value)) return c;
        }
        return null;
    }
}

