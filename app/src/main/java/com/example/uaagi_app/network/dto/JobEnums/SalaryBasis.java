package com.example.uaagi_app.network.dto.JobEnums;

import com.google.gson.annotations.SerializedName;

public enum SalaryBasis {
    @SerializedName("per Year")
    PER_YEAR("Per Year"),
    @SerializedName("Per Month")
    PER_MONTH("Per Month"),
    @SerializedName("Per Hour")
    PER_HOUR("Per Hour");
    private final String displayName;
    SalaryBasis(String displayName){
        this.displayName = displayName;
    }
    public String getDisplayName(){
        return displayName;
    }
    public static SalaryBasis fromString(String value) {
        for (SalaryBasis c : SalaryBasis.values()) {
            if (c.getDisplayName().equalsIgnoreCase(value)) return c;
        }
        return null;
    }
}
