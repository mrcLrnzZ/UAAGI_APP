package com.example.uaagi_app.network.dto.JobEnums;

import com.google.gson.annotations.SerializedName;

public enum JobType {
    @SerializedName("Full-time")
    ENTRY_LEVEL("Full-time"),
    @SerializedName("Part-time")
    MID_LEVEL("Part-time"),
    @SerializedName("Internship")
    INTERN("Internship");
    private final String displayName;
    JobType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
    public static JobType fromString(String value) {
        for (JobType c : JobType.values()) {
            if (c.getDisplayName().equalsIgnoreCase(value)) return c;
        }
        return null;
    }
}