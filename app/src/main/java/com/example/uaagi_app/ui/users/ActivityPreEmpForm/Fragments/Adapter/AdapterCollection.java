package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter;

import android.text.TextUtils;
import android.widget.TextView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.PreEmploymentForm.*;
import com.example.uaagi_app.utils.Helpers;


import java.util.List;

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

                    name.setText(Helpers.safeText(reference.getName()));
                    occupation.setText(Helpers.safeText(reference.getOccupation()));
                    company.setText(Helpers.safeText(reference.getCompany()));
                    phone.setText(Helpers.safeText(reference.getPhone()));

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

                    type.setText(Helpers.safeText(govId.getType()));
                    number.setText(Helpers.safeText(govId.getNumber()));
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

                    type.setText(Helpers.safeText(seminar.getType()));
                    title.setText(Helpers.safeText(seminar.getTitle()));
                    organizer.setText(Helpers.safeText(seminar.getOrganizer()));
                    date.setText(Helpers.safeText(seminar.getDate()));
                    description.setText(Helpers.safeText(seminar.getDescription()));

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

                    type.setText(Helpers.safeText(qualification.getType()));
                    title.setText(Helpers.safeText(qualification.getTitle()));
                    authority.setText(Helpers.safeText(qualification.getAuthority()));
                    date.setText(Helpers.safeText(qualification.getDate()));
                    description.setText(Helpers.safeText(qualification.getDescription()));
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

                    name.setText(Helpers.safeText(certificate.getName()));
                    organization.setText(Helpers.safeText(certificate.getOrganization()));
                    date.setText(Helpers.safeText(certificate.getDate()));
                    expiry.setText(Helpers.safeText(certificate.getExpiryDate()));
                    description.setText(Helpers.safeText(certificate.getDescription()));
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

                    category.setText(Helpers.safeText(skill.getCategory()));
                    level.setText(Helpers.safeText(skill.getLevel()));
                    description.setText((Helpers.safeText(skill.getDescription())));
                }
        );
    }
    public static GenericRecyclerAdapter<WorkExperience> createWorkExperienceAdapter(List<WorkExperience> workExperiences){
        return new GenericRecyclerAdapter<>(
                workExperiences,
                R.layout.item_work_experience_preview,
                (view, workExperience, position) -> {

                    TextView company = view.findViewById(R.id.tvPreviewWorkCompany);
                    TextView workPosition = view.findViewById(R.id.tvPreviewWorkPosition);
                    TextView durationtv = view.findViewById(R.id.tvPreviewWorkDuration);
                    TextView description = view.findViewById(R.id.tvPreviewWorkDescription);

                    company.setText(Helpers.safeText(workExperience.getCompany()));
                    workPosition.setText(Helpers.safeText(workExperience.getPosition()));
                    String duration = "";
                    if (!TextUtils.isEmpty(workExperience.getStartDate()) && !TextUtils.isEmpty(workExperience.getEndDate())) {
                        duration = workExperience.getStartDate() + " - " + workExperience.getEndDate();
                    } else if (!TextUtils.isEmpty(workExperience.getStartDate())) {
                        duration = workExperience.getStartDate() + " - Present";
                    } else {
                        duration = "—";
                    }
                    durationtv.setText(Helpers.safeText(duration));
                    description.setText(Helpers.safeText(workExperience.getDescription()));

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

                    level.setText(Helpers.safeText(education.getLevel()));
                    school.setText(Helpers.safeText(education.getSchool()));
                    year.setText(Helpers.safeText(education.getGradYear()));
                    status.setText(Helpers.safeText(education.getStatus()));
                    achievement.setText(Helpers.safeText(education.getAchievement()));
                }
        );
    }
}
