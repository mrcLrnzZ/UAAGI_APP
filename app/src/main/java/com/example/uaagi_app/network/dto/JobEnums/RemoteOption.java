package com.example.uaagi_app.network.dto.JobEnums;

public enum RemoteOption {
    ON_SITE_ONLY("On-site only"),
    FULLY_REMOTE("Fully-Remote"),
    HYBRID("Hybrid");
    private final String displayName;
    RemoteOption(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }
    public static RemoteOption fromString(String value) {
        for (RemoteOption c : RemoteOption.values()) {
            if (c.getDisplayName().equalsIgnoreCase(value)) return c;
        }
        return null;
    }
}