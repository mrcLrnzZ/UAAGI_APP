package com.example.uaagi_app.network.dto.JobEnums;

public enum Company {
    BAIC_PHILIPPINES("BAIC Philippines"),
    FOTON_PHILIPPINES("Foton Philippines"),
    CHERY_AUTO_PHILIPPINES("Chery Auto Philippines"),
    LYNK_AND_CO_PHILIPPINES("Lynk & Co Philippines"),
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
