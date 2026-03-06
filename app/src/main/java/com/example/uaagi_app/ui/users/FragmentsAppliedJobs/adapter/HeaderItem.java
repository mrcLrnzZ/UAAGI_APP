package com.example.uaagi_app.ui.users.FragmentsAppliedJobs.adapter;

public class HeaderItem implements AppliedJobListItem {

    private String title;

    public HeaderItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}