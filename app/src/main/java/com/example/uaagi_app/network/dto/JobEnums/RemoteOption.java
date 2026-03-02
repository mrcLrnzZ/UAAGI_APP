package com.example.uaagi_app.network.dto.JobEnums;

import com.google.gson.annotations.SerializedName;

public enum RemoteOption {
    @SerializedName("On-site only")
    ON_SITE_ONLY("On-site only"),
    @SerializedName("Fully-Remote")
    FULLY_REMOTE("Fully-Remote"),
   @SerializedName("Hybrid")
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