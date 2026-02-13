package com.example.uaagi_app.network.dto.JobEnums;

public enum JobType {
    ENTRY_LEVEL("Ful-time"),
    MID_LEVEL("Part-time"),
    SENIOR_LEVEL("Internship");
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