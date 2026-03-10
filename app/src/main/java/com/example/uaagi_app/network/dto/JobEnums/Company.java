package com.example.uaagi_app.network.dto.JobEnums;

import com.google.gson.annotations.SerializedName;

public enum Company {
    @SerializedName("BAIC Philippines")
    BAIC_PHILIPPINES("BAIC Philippines"),
    @SerializedName("Foton Philippines")
    FOTON_PHILIPPINES("Foton Philippines"),
    @SerializedName("Chery Auto Philippines")
    CHERY_AUTO_PHILIPPINES("Chery Auto Philippines"),
    @SerializedName("Lynk & Co Philippines")
    LYNK_AND_CO_PHILIPPINES("Lynk & Co Philippines"),
    @SerializedName("Mutt Motorcycle Philippines")
    MUTT_MOTORCYCLE_PHILIPPINES("Mutt Motorcycle Philippines");

    private final String displayName;

    Company(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }
    public static Company fromString(String value) {
        for (Company c : Company.values()) {
            if (c.getDisplayName().equalsIgnoreCase(value)) return c;
        }
        return null;
    }
}
