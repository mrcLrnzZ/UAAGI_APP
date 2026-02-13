package com.example.uaagi_app.network.dto;

import com.example.uaagi_app.network.dto.JobEnums.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class JobRequest {

    private final int id;
    private final String jobTitle;
    private final String department;
    private final Company company;
    private final JobType jobType;
    private final ExperienceLevel experienceLevel;
    private final String location;
    private final RemoteOption remoteOption;
    private final BigDecimal minSalary;
    private final BigDecimal maxSalary;
    private final SalaryBasis salaryBasis;
    private final SalaryBasis salaryPeriod;
    private final String benefits;
    private final String jobSummary;
    private final String jobDescription;
    private final String requirements;
    private final String preferredQualifications;
    private final LocalDate applicationDeadline;
    private final String contactEmail;
    private final String applicationInstructions;
    private final int postedBy;
    private final LocalDateTime createdAt;
    private final int applicants;
    private final Status status;
    private final Integer deletedBy;
    private final Integer restoredBy;
    private final LocalDateTime restoredAt;
    private final LocalDateTime deletedAt;

    // Private constructor for builder
    private JobRequest(Builder builder) {
        this.id = builder.id;
        this.jobTitle = builder.jobTitle;
        this.department = builder.department;
        this.company = builder.company;
        this.jobType = builder.jobType;
        this.experienceLevel = builder.experienceLevel;
        this.location = builder.location;
        this.remoteOption = builder.remoteOption;
        this.minSalary = builder.minSalary;
        this.maxSalary = builder.maxSalary;
        this.salaryBasis = builder.salaryBasis;
        this.salaryPeriod = builder.salaryPeriod;
        this.benefits = builder.benefits;
        this.jobSummary = builder.jobSummary;
        this.jobDescription = builder.jobDescription;
        this.requirements = builder.requirements;
        this.preferredQualifications = builder.preferredQualifications;
        this.applicationDeadline = builder.applicationDeadline;
        this.contactEmail = builder.contactEmail;
        this.applicationInstructions = builder.applicationInstructions;
        this.postedBy = builder.postedBy;
        this.createdAt = builder.createdAt;
        this.applicants = builder.applicants;
        this.status = builder.status;
        this.deletedBy = builder.deletedBy;
        this.restoredBy = builder.restoredBy;
        this.restoredAt = builder.restoredAt;
        this.deletedAt = builder.deletedAt;
    }

    // Builder Class
    public static class Builder {
        private int id;
        private String jobTitle;
        private String department;
        private Company company;
        private JobType jobType;
        private ExperienceLevel experienceLevel;
        private String location;
        private RemoteOption remoteOption;
        private BigDecimal minSalary;
        private BigDecimal maxSalary;
        private SalaryBasis salaryBasis;
        private SalaryBasis salaryPeriod;
        private String benefits;
        private String jobSummary;
        private String jobDescription;
        private String requirements;
        private String preferredQualifications;
        private LocalDate applicationDeadline;
        private String contactEmail;
        private String applicationInstructions;
        private int postedBy;
        private LocalDateTime createdAt;
        private int applicants;
        private Status status;
        private Integer deletedBy;
        private Integer restoredBy;
        private LocalDateTime restoredAt;
        private LocalDateTime deletedAt;

        public Builder() {}

        public Builder id(int id) { this.id = id; return this; }
        public Builder jobTitle(String jobTitle) { this.jobTitle = jobTitle; return this; }
        public Builder department(String department) { this.department = department; return this; }
        public Builder company(Company company) { this.company = company; return this; }
        public Builder jobType(JobType jobType) { this.jobType = jobType; return this; }
        public Builder experienceLevel(ExperienceLevel experienceLevel) { this.experienceLevel = experienceLevel; return this; }
        public Builder location(String location) { this.location = location; return this; }
        public Builder remoteOption(RemoteOption remoteOption) { this.remoteOption = remoteOption; return this; }
        public Builder minSalary(BigDecimal minSalary) { this.minSalary = minSalary; return this; }
        public Builder maxSalary(BigDecimal maxSalary) { this.maxSalary = maxSalary; return this; }
        public Builder salaryBasis(SalaryBasis salaryBasis) { this.salaryBasis = salaryBasis; return this; }
        public Builder salaryPeriod(SalaryBasis salaryPeriod) { this.salaryPeriod = salaryPeriod; return this; }
        public Builder benefits(String benefits) { this.benefits = benefits; return this; }
        public Builder jobSummary(String jobSummary) { this.jobSummary = jobSummary; return this; }
        public Builder jobDescription(String jobDescription) { this.jobDescription = jobDescription; return this; }
        public Builder requirements(String requirements) { this.requirements = requirements; return this; }
        public Builder preferredQualifications(String preferredQualifications) { this.preferredQualifications = preferredQualifications; return this; }
        public Builder applicationDeadline(LocalDate applicationDeadline) { this.applicationDeadline = applicationDeadline; return this; }
        public Builder contactEmail(String contactEmail) { this.contactEmail = contactEmail; return this; }
        public Builder applicationInstructions(String applicationInstructions) { this.applicationInstructions = applicationInstructions; return this; }
        public Builder postedBy(int postedBy) { this.postedBy = postedBy; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder applicants(int applicants) { this.applicants = applicants; return this; }
        public Builder status(Status status) { this.status = status; return this; }
        public Builder deletedBy(Integer deletedBy) { this.deletedBy = deletedBy; return this; }
        public Builder restoredBy(Integer restoredBy) { this.restoredBy = restoredBy; return this; }
        public Builder restoredAt(LocalDateTime restoredAt) { this.restoredAt = restoredAt; return this; }
        public Builder deletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; return this; }

        public JobRequest build() {
            return new JobRequest(this);
        }
    }

    // Getters (optional setters can be added if mutable)
    public int getId() { return id; }
    public String getJobTitle() { return jobTitle; }
    public String getDepartment() { return department; }
    public Company getCompany() { return company; }
    public JobType getJobType() { return jobType; }
    public ExperienceLevel getExperienceLevel() { return experienceLevel; }
    public String getLocation() { return location; }
    public RemoteOption getRemoteOption() { return remoteOption; }
    public BigDecimal getMinSalary() { return minSalary; }
    public BigDecimal getMaxSalary() { return maxSalary; }
    public SalaryBasis getSalaryBasis() { return salaryBasis; }
    public SalaryBasis getSalaryPeriod() { return salaryPeriod; }
    public String getBenefits() { return benefits; }
    public String getJobSummary() { return jobSummary; }
    public String getJobDescription() { return jobDescription; }
    public String getRequirements() { return requirements; }
    public String getPreferredQualifications() { return preferredQualifications; }
    public LocalDate getApplicationDeadline() { return applicationDeadline; }
    public String getContactEmail() { return contactEmail; }
    public String getApplicationInstructions() { return applicationInstructions; }
    public int getPostedBy() { return postedBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public int getApplicants() { return applicants; }
    public Status getStatus() { return status; }
    public Integer getDeletedBy() { return deletedBy; }
    public Integer getRestoredBy() { return restoredBy; }
    public LocalDateTime getRestoredAt() { return restoredAt; }
    public LocalDateTime getDeletedAt() { return deletedAt; }
}

//USES
//JobDTO job = new JobDTO.Builder()
//        .jobTitle("Software Engineer")
//        .department("IT")
//        .company(JobDTO.Company.BAIC_PHILIPPINES)
//        .jobType(JobDTO.JobType.FULL_TIME)
//        .experienceLevel(JobDTO.ExperienceLevel.ENTRY_LEVEL)
//        .location("Manila")
//        .remoteOption(JobDTO.RemoteOption.HYBRID)
//        .minSalary(new BigDecimal("30000"))
//        .maxSalary(new BigDecimal("50000"))
//        .salaryBasis(JobDTO.SalaryBasis.PER_MONTH)
//        .salaryPeriod(JobDTO.SalaryBasis.PER_MONTH)
//        .benefits("Health insurance, 13th month pay")
//        .jobSummary("Develop software applications")
//        .jobDescription("Full development lifecycle")
//        .requirements("Java, Spring, SQL")
//        .applicationDeadline(LocalDate.of(2026, 3, 31))
//        .contactEmail("hr@baic.ph")
//        .applicationInstructions("Send resume to email")
//        .postedBy(1)
//        .status(JobDTO.Status.ACTIVE)
//        .build();
