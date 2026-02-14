package com.example.uaagi_app.network.dto.JobEnums;

public enum SalaryBasis {
    PER_YEAR("Per Year"),
    PER_MONTH("Per Month"),
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
