package com.example.uaagi_app.network.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class JobsRequest {

    public int id;

    public String jobTitle;
    public String department;

    public String company;
    public String jobType;
    public String experienceLevel;

    public String location;
    public String remoteOption;

    public BigDecimal minSalary;
    public BigDecimal maxSalary;
    public String salaryBasis;
    public String salaryPeriod;

    public String benefits;
    public String jobSummary;
    public String jobDescription;
    public String requirements;
    public String preferredQualifications;

    public LocalDate applicationDeadline;
    public String contactEmail;
    public String applicationInstructions;

    public int postedBy;
    public LocalDateTime createdAt;

    public int applicants;
    public String status;

    public Integer deletedBy;
    public Integer restoredBy;
    public LocalDateTime restoredAt;
    public LocalDateTime deletedAt;

}

