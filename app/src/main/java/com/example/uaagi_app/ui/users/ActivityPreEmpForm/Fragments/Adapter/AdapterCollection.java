package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.PreEmploymentForm.*;
import com.example.uaagi_app.data.viewmodel.JobViewModel;
import com.example.uaagi_app.network.Services.JobService;
import com.example.uaagi_app.network.dto.JobFetchResponse;
import com.example.uaagi_app.ui.users.FragmentsCareers.JobDesc;
import com.example.uaagi_app.utils.Helpers;


import java.util.List;
import java.util.Objects;

public class AdapterCollection {
    public static GenericRecyclerAdapter<ContactReference> createCharacterReferenceAdapter(List<ContactReference> references) {

        return new GenericRecyclerAdapter<>(
                references,
                R.layout.item_character_reference_preview,
                (view, reference, position) -> {

                    TextView name = view.findViewById(R.id.tvPreviewRefName);
                    TextView occupation = view.findViewById(R.id.tvPreviewRefOccupation);
                    TextView company = view.findViewById(R.id.tvPreviewRefCompany);
                    TextView phone = view.findViewById(R.id.tvPreviewRefPhone);

                    if (name != null) name.setText(Helpers.safeText(reference.getName()));
                    if (occupation != null) occupation.setText(Helpers.safeText(reference.getOccupation()));
                    if (company != null) company.setText(Helpers.safeText(reference.getCompany()));
                    if (phone != null) phone.setText(Helpers.safeText(reference.getPhone()));

                }
        );
    }

    public static GenericRecyclerAdapter<GovId> createGovIdAdapter(List<GovId> govIds) {

        return new GenericRecyclerAdapter<>(
                govIds,
                R.layout.item_gov_id_preview,
                (view, govId, position) -> {

                    TextView type = view.findViewById(R.id.tvPreviewGovIdType);
                    TextView number = view.findViewById(R.id.tvPreviewGovIdNumber);

                    if (type != null) type.setText(Helpers.safeText(govId.getType()));
                    if (number != null) number.setText(Helpers.safeText(govId.getNumber()));
                }
        );
    }

    public static GenericRecyclerAdapter<Seminar> createSeminarAdapter(List<Seminar> seminars) {

        return new GenericRecyclerAdapter<>(
                seminars,
                R.layout.item_seminar_preview,
                (view, seminar, position) -> {

                    TextView type = view.findViewById(R.id.tvPreviewSeminarType);
                    TextView title = view.findViewById(R.id.tvPreviewSeminarTitle);
                    TextView organizer = view.findViewById(R.id.tvPreviewSeminarOrganizer);
                    TextView date = view.findViewById(R.id.tvPreviewSeminarDate);
                    TextView description = view.findViewById(R.id.tvPreviewSeminarDescription);

                    if (type != null) type.setText(Helpers.safeText(seminar.getType()));
                    if (title != null) title.setText(Helpers.safeText(seminar.getTitle()));
                    if (organizer != null) organizer.setText(Helpers.safeText(seminar.getOrganizer()));
                    if (date != null) date.setText(Helpers.safeText(seminar.getDate()));
                    if (description != null) description.setText(Helpers.safeText(seminar.getDescription()));

                }
        );
    }

    public static GenericRecyclerAdapter<Qualification> createQualificationAdapter(List<Qualification> qualifications) {

        return new GenericRecyclerAdapter<>(
                qualifications,
                R.layout.item_qualification_preview,
                (view, qualification, position) -> {

                    TextView type = view.findViewById(R.id.tvPreviewQualType);
                    TextView title = view.findViewById(R.id.tvPreviewQualTitle);
                    TextView authority = view.findViewById(R.id.tvPreviewQualAuthority);
                    TextView date = view.findViewById(R.id.tvPreviewQualDate);
                    TextView description = view.findViewById(R.id.tvPreviewQualDescription);

                    if (type != null) type.setText(Helpers.safeText(qualification.getType()));
                    if (title != null) title.setText(Helpers.safeText(qualification.getTitle()));
                    if (authority != null) authority.setText(Helpers.safeText(qualification.getAuthority()));
                    if (date != null) date.setText(Helpers.safeText(qualification.getDate()));
                    if (description != null) description.setText(Helpers.safeText(qualification.getDescription()));
                }
        );
    }

    public static GenericRecyclerAdapter<Certificate> createCertificateAdapter(List<Certificate> certificates) {

        return new GenericRecyclerAdapter<>(
                certificates,
                R.layout.item_certificate_preview,
                (view, certificate, position) -> {

                    TextView name = view.findViewById(R.id.tvPreviewCertName);
                    TextView organization = view.findViewById(R.id.tvPreviewCertOrganization);
                    TextView date = view.findViewById(R.id.tvPreviewCertDate);
                    TextView expiry = view.findViewById(R.id.tvPreviewCertExpiry);
                    TextView description = view.findViewById(R.id.tvPreviewCertDescription);

                    if (name != null) name.setText(Helpers.safeText(certificate.getName()));
                    if (organization != null) organization.setText(Helpers.safeText(certificate.getOrganization()));
                    if (date != null) date.setText(Helpers.safeText(certificate.getDate()));
                    if (expiry != null) expiry.setText(Helpers.safeText(certificate.getExpiryDate()));
                    if (description != null) description.setText(Helpers.safeText(certificate.getDescription()));
                }
        );
    }

    public static GenericRecyclerAdapter<ProfessionalSkills> createProfessionalSkillsAdapter(List<ProfessionalSkills> skills) {

        return new GenericRecyclerAdapter<>(
                skills,
                R.layout.item_professional_skill_preview,
                (view, skill, position) -> {

                    TextView category = view.findViewById(R.id.tvPreviewSkillCategory);
                    TextView level = view.findViewById(R.id.tvPreviewSkillLevel);
                    TextView description = view.findViewById(R.id.tvPreviewSkillDescription);

                    if (category != null) category.setText(Helpers.safeText(skill.getCategory()));
                    if (level != null) level.setText(Helpers.safeText(skill.getLevel()));
                    if (description != null) description.setText((Helpers.safeText(skill.getDescription())));
                }
        );
    }

    public static GenericRecyclerAdapter<WorkExperience> createWorkExperienceAdapter(List<WorkExperience> workExperiences) {
        return new GenericRecyclerAdapter<>(
                workExperiences,
                R.layout.item_work_experience_preview,
                (view, workExperience, position) -> {

                    TextView company = view.findViewById(R.id.tvPreviewWorkCompany);
                    TextView workPosition = view.findViewById(R.id.tvPreviewWorkPosition);
                    TextView durationtv = view.findViewById(R.id.tvPreviewWorkDuration);
                    TextView description = view.findViewById(R.id.tvPreviewWorkDescription);

                    if (company != null) company.setText(Helpers.safeText(workExperience.getCompany()));
                    if (workPosition != null) workPosition.setText(Helpers.safeText(workExperience.getPosition()));
                    String duration = "";
                    if (!TextUtils.isEmpty(workExperience.getStartDate()) && !TextUtils.isEmpty(workExperience.getEndDate())) {
                        duration = workExperience.getStartDate() + " - " + workExperience.getEndDate();
                    } else if (!TextUtils.isEmpty(workExperience.getStartDate())) {
                        duration = workExperience.getStartDate() + " - Present";
                    } else {
                        duration = "—";
                    }
                    if (durationtv != null) durationtv.setText(Helpers.safeText(duration));
                    if (description != null) description.setText(Helpers.safeText(workExperience.getDescription()));

                }
        );
    }

    public static GenericRecyclerAdapter<Education> createEducationAdapter(List<Education> educations) {

        return new GenericRecyclerAdapter<>(
                educations,
                R.layout.item_education_preview,
                (view, education, position) -> {

                    TextView level = view.findViewById(R.id.tvPreviewEducationLevel);
                    TextView school = view.findViewById(R.id.tvPreviewEducationSchool);
                    TextView year = view.findViewById(R.id.tvPreviewEducationYear);
                    TextView status = view.findViewById(R.id.tvPreviewEducationStatus);
                    TextView achievement = view.findViewById(R.id.tvPreviewEducationAchievement);

                    if (level != null) level.setText(Helpers.safeText(education.getLevel()));
                    if (school != null) school.setText(Helpers.safeText(education.getSchool()));
                    if (year != null) year.setText(Helpers.safeText(education.getGradYear()));
                    if (status != null) status.setText(Helpers.safeText(education.getStatus()));
                    if (achievement != null) achievement.setText(Helpers.safeText(education.getAchievement()));
                }
        );
    }

    public static GenericRecyclerAdapter<JobFetchResponse> createSavedJobsAdapter(List<JobFetchResponse> savedJobs, FragmentManager fragmentManager, Context context, JobViewModel jobViewModel) {
        final GenericRecyclerAdapter<JobFetchResponse>[] adapterRef = new GenericRecyclerAdapter[1];
        
        adapterRef[0] = new GenericRecyclerAdapter<>(
                savedJobs,
                R.layout.item_saved_job,
                (view, job, position) -> {
                    TextView jobTitle = view.findViewById(R.id.tvJobTitle);
                    TextView jobCompanyName = view.findViewById(R.id.tvCompanyName);
                    TextView jobLocation = view.findViewById(R.id.tvLocation);
                    ImageView ivBookmark = view.findViewById(R.id.ivBookmark);
                    TextView tvAppliedDate = view.findViewById(R.id.tvAppliedDate);
                    boolean isIntern = Objects.equals(job.getJobType().getDisplayName(), "Internship");
                    if (jobTitle != null) jobTitle.setText(Helpers.safeText(job.getJobTitle()));
                    if (jobCompanyName != null) jobCompanyName.setText(Helpers.safeText(job.getCompany() != null ? job.getCompany().getDisplayName() : ""));
                    if (jobLocation != null) jobLocation.setText(Helpers.safeText(job.getLocation()));
                    if (tvAppliedDate != null) tvAppliedDate.setText("Posted on " + Helpers.formatToOrdinalDate(String.valueOf(job.getCreatedAt())));

                    if (ivBookmark != null) {
                        ivBookmark.setOnClickListener(v -> {
                            Helpers.actionUnsaveJob(context, job.getId(), new JobService.FeedbackCallback() {
                                @Override
                                public void feedback(String message) {
                                    if (adapterRef[0] != null) {
                                        adapterRef[0].removeItem(job);
                                    }
                                    jobViewModel.removeSavedJob(job);
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(String errorMessage) {
                                    Toast.makeText(context, "Action failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                                }
                            });
                        });
                    }

                    View.OnClickListener navigateToDetails = v -> {
                        try {
                            Fragment jobDesc = JobDesc.newInstance(job.getId(), isIntern);
                            if (fragmentManager != null) {
                                fragmentManager.beginTransaction()
                                        .replace(R.id.fragment_container, jobDesc)
                                        .addToBackStack(null)
                                        .commit();
                            }
                        } catch (Exception e) {
                            Log.e("AdapterCollection", "Error navigating: " + e.getMessage());
                        }
                    };

                    view.setOnClickListener(navigateToDetails);

                }
        );
        return adapterRef[0];
    }

}
