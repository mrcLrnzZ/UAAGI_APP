package com.example.uaagi_app.network.dto.JobEnums;

public enum Status {
    ACTIVE("Active"),
    CLOSED("Closed"),
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

