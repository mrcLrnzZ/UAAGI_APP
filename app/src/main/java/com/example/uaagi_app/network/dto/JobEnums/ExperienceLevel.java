package com.example.uaagi_app.network.dto.JobEnums;

import com.google.gson.annotations.SerializedName;

public enum ExperienceLevel {
    @SerializedName("Entry Level")
    ENTRY_LEVEL("Entry Level"),
    @SerializedName("Mid Level")
    MID_LEVEL("Mid Level"),
    @SerializedName("Senior Level")
    SENIOR_LEVEL("Senior Level");
    private final String displayName;
    ExperienceLevel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
    public static ExperienceLevel fromString(String value) {
        for (ExperienceLevel c : ExperienceLevel.values()) {
            if (c.getDisplayName().equalsIgnoreCase(value)) return c;
        }
        return null;
    }
}