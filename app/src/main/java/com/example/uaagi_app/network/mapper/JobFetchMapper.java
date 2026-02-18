package com.example.uaagi_app.network.mapper;

import com.example.uaagi_app.network.dto.JobFetchResponse;
import com.example.uaagi_app.network.dto.JobEnums.*;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class JobFetchMapper {

    public static JobFetchResponse fromJson(JSONObject job) {
        return new JobFetchResponse.Builder()
                .id(job.optInt("id"))
                .jobTitle(job.optString("job_title"))
                .department(job.optString("department"))
                .company(Company.fromString(job.optString("company")))
                .jobType(JobType.fromString(job.optString("job_type")))
                .experienceLevel(ExperienceLevel.fromString(job.optString("experience_level")))
                .location(job.optString("location"))
                .remoteOption(RemoteOption.fromString(job.optString("remote_option")))
                .minSalary(new BigDecimal(job.optString("min_salary", "0")))
                .maxSalary(new BigDecimal(job.optString("max_salary", "0")))
                .salaryBasis(SalaryBasis.fromString(job.optString("salary_basis")))
                .salaryPeriod(SalaryBasis.fromString(job.optString("salary_period")))
                .benefits(job.optString("benefits"))
                .jobSummary(job.optString("job_summary"))
                .jobDescription(job.optString("job_description"))
                .requirements(job.optString("requirements"))
                .preferredQualifications(job.optString("preferred_qualifications"))
                .applicationDeadline(
                        LocalDate.parse(job.optString("application_deadline"))
                )
                .contactEmail(job.optString("contact_email"))
                .applicationInstructions(job.optString("application_instructions"))
                .postedBy(job.optInt("posted_by"))
                .createdAt(
                        LocalDateTime.parse(
                                job.optString("created_at").replace(" ", "T")
                        )
                )
                .applicants(job.optInt("applicants"))
                .status(Status.fromString(job.optString("status")))
                .deletedBy(job.isNull("deleted_by") ? null : job.optInt("deleted_by"))
                .restoredBy(job.isNull("restored_by") ? null : job.optInt("restored_by"))
                .restoredAt(job.isNull("restored_at") ? null :
                        LocalDateTime.parse(job.optString("restored_at").replace(" ", "T")))
                .deletedAt(job.isNull("deleted_at") ? null :
                        LocalDateTime.parse(job.optString("deleted_at").replace(" ", "T")))
                .build();
    }
}
