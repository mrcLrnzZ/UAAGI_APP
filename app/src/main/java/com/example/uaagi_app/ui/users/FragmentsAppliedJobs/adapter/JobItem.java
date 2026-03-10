package com.example.uaagi_app.ui.users.FragmentsAppliedJobs.adapter;

import com.example.uaagi_app.network.dto.JobFetchResponse;

public class JobItem implements AppliedJobListItem {

    private JobFetchResponse job;

    public JobItem(JobFetchResponse job) {
        this.job = job;
    }

    public JobFetchResponse getJob() {
        return job;
    }
}