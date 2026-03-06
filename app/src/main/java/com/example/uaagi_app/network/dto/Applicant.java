package com.example.uaagi_app.network.dto;

import com.google.gson.annotations.SerializedName;

public class Applicant {
    @SerializedName("applicantion_id")
    private final int applicantionId;
    @SerializedName("user_id")
    private final int userId;
    @SerializedName("job_id")
    private final int jobId;
    @SerializedName("apply_method")
    private final String applyMethod;
    @SerializedName("submission_date")
    private final String submissionDate;
    @SerializedName("status")
    private final String status;
    @SerializedName("reason")
    private final String reason;
    @SerializedName("updated_by")
    private final int updatedBy;
    @SerializedName("updated_at")
    private final String updatedAt;
    @SerializedName("email")
    private final String email;
    @SerializedName("job_title")
    private final String jobTitle;
    @SerializedName("department")
    private final String department;
    @SerializedName("company")
    private final String company;
    @SerializedName("location")
    private final String location;
    @SerializedName("remote_option")
    private final String remoteOption;
    @SerializedName("schedule_id")
    private final int scheduleId;
    @SerializedName("interview_date")
    private final String interviewDate;
    @SerializedName("interview_location")
    private final String interviewLocation;
    @SerializedName("interview_status")
    private final String interviewStatus;
    @SerializedName("interview_type")
    private final String interviewType;
    @SerializedName("interviewer")
    private final String interviewer;
    @SerializedName("interview_start")
    private final String interviewStart;
    @SerializedName("interview_end")
    private final String interviewEnd;

    private Applicant(Builder builder) {
        this.applicantionId = builder.applicantionId;
        this.userId = builder.userId;
        this.jobId = builder.jobId;
        this.applyMethod = builder.applyMethod;
        this.submissionDate = builder.submissionDate;
        this.status = builder.status;
        this.reason = builder.reason;
        this.updatedBy = builder.updatedBy;
        this.updatedAt = builder.updatedAt;
        this.email = builder.email;
        this.jobTitle = builder.jobTitle;
        this.department = builder.department;
        this.company = builder.company;
        this.location = builder.location;
        this.remoteOption = builder.remoteOption;
        this.scheduleId = builder.scheduleId;
        this.interviewDate = builder.interviewDate;
        this.interviewLocation = builder.interviewLocation;
        this.interviewStatus = builder.interviewStatus;
        this.interviewType = builder.interviewType;
        this.interviewer = builder.interviewer;
        this.interviewStart = builder.interviewStart;
        this.interviewEnd = builder.interviewEnd;
    }

    public int getApplicantionId() {
        return applicantionId;
    }

    public int getUserId() {
        return userId;
    }

    public int getJobId() {
        return jobId;
    }

    public String getApplyMethod() {
        return applyMethod;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public String getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getEmail() {
        return email;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getDepartment() {
        return department;
    }

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }

    public String getRemoteOption() {
        return remoteOption;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public String getInterviewDate() {
        return interviewDate;
    }

    public String getInterviewLocation() {
        return interviewLocation;
    }

    public String getInterviewStatus() {
        return interviewStatus;
    }

    public String getInterviewType() {
        return interviewType;
    }

    public String getInterviewer() {
        return interviewer;
    }

    public String getInterviewStart() {
        return interviewStart;
    }

    public String getInterviewEnd() {
        return interviewEnd;
    }

    public static class Builder {
        private int applicantionId;
        private int userId;
        private int jobId;
        private String applyMethod;
        private String submissionDate;
        private String status;
        private String reason;
        private int updatedBy;
        private String updatedAt;
        private String email;
        private String jobTitle;
        private String department;
        private String company;
        private String location;
        private String remoteOption;
        private int scheduleId;
        private String interviewDate;
        private String interviewLocation;
        private String interviewStatus;
        private String interviewType;
        private String interviewer;
        private String interviewStart;
        private String interviewEnd;
        public Builder() {}
        public Builder applicantionId(int applicantionId) { this.applicantionId = applicantionId; return this; }
        public Builder userId(int userId) { this.userId = userId; return this; }
        public Builder jobId(int jobId) { this.jobId = jobId; return this; }
        public Builder applyMethod(String applyMethod) { this.applyMethod = applyMethod; return this; }
        public Builder submissionDate(String submissionDate) { this.submissionDate = submissionDate; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder reason(String reason) { this.reason = reason; return this; }
        public Builder updatedBy(int updatedBy) { this.updatedBy = updatedBy; return this; }
        public Builder updatedAt(String updatedAt) { this.updatedAt = updatedAt; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder jobTitle(String jobTitle) { this.jobTitle = jobTitle; return this; }
        public Builder department(String department) { this.department = department; return this; }
        public Builder company(String company) { this.company = company; return this; }
        public Builder location(String location) { this.location = location; return this; }
        public Builder remoteOption(String remoteOption) { this.remoteOption = remoteOption; return this; }
        public Builder scheduleId(int scheduleId) { this.scheduleId = scheduleId; return this; }
        public Builder interviewDate(String interviewDate) { this.interviewDate = interviewDate; return this; }
        public Builder interviewLocation(String interviewLocation) { this.interviewLocation = interviewLocation; return this; }
        public Builder interviewStatus(String interviewStatus) { this.interviewStatus = interviewStatus; return this; }
        public Builder interviewType(String interviewType) { this.interviewType = interviewType; return this; }
        public Builder interviewer(String interviewer) { this.interviewer = interviewer; return this; }
        public Builder interviewStart(String interviewStart) { this.interviewStart = interviewStart; return this; }
        public Builder interviewEnd(String interviewEnd) { this.interviewEnd = interviewEnd; return this; }
        public Applicant build() {
            return new Applicant(this);
        }
    }
}
