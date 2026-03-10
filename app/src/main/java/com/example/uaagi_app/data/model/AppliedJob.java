package com.example.uaagi_app.data.model;

public class AppliedJob {
    private String jobId;
    private String jobTitle;
    private String companyName;
    private String location;
    private String appliedDate;
    private String appliedPlatform;
    private String employerResponseTime;
    private boolean responseUnlikely;
    private String status; // "applied", "interviewing", "offer_received", "hired", "not_selected", "no_longer_interested"

    public AppliedJob() {
    }

    public AppliedJob(String jobId, String jobTitle, String companyName, String location,
                      String appliedDate, String appliedPlatform, String employerResponseTime,
                      boolean responseUnlikely, String status) {
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.location = location;
        this.appliedDate = appliedDate;
        this.appliedPlatform = appliedPlatform;
        this.employerResponseTime = employerResponseTime;
        this.responseUnlikely = responseUnlikely;
        this.status = status;
    }

    // Getters and Setters
    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(String appliedDate) {
        this.appliedDate = appliedDate;
    }

    public String getAppliedPlatform() {
        return appliedPlatform;
    }

    public void setAppliedPlatform(String appliedPlatform) {
        this.appliedPlatform = appliedPlatform;
    }

    public String getEmployerResponseTime() {
        return employerResponseTime;
    }

    public void setEmployerResponseTime(String employerResponseTime) {
        this.employerResponseTime = employerResponseTime;
    }

    public boolean isResponseUnlikely() {
        return responseUnlikely;
    }

    public void setResponseUnlikely(boolean responseUnlikely) {
        this.responseUnlikely = responseUnlikely;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}