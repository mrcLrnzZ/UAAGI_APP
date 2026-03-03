package com.example.uaagi_app.network.dto;


import com.example.uaagi_app.network.dto.PreEmpDto.*;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PreEmpFetchResponse {
    private UserInfo userInfo;
    @SerializedName("certifications")
    private List<Certificate> certificate;
    @SerializedName("qualifications")
    private List<Qualification> qualification;
    @SerializedName("seminars")
    private List<Seminar> seminar;
    @SerializedName("govIds")
    private List<GovermentId> govId;
    @SerializedName("contactReferences")
    private List<ContactReference> contactReference;
    @SerializedName("workExperiences")
    private List<WorkExperience> workExperience;
    @SerializedName("educations")
    private List<Education> education;
    private List<ProfessionalSkills> professionalSkills;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<Certificate> getCertificate() {
        return certificate;
    }

    public void setCertificate(List<Certificate> certificate) {
        this.certificate = certificate;
    }

    public List<Qualification> getQualification() {
        return qualification;
    }

    public void setQualification(List<Qualification> qualification) {
        this.qualification = qualification;
    }

    public List<Seminar> getSeminar() {
        return seminar;
    }

    public void setSeminar(List<Seminar> seminar) {
        this.seminar = seminar;
    }

    public List<GovermentId> getGovId() {
        return govId;
    }

    public void setGovId(List<GovermentId> govId) {
        this.govId = govId;
    }

    public List<ContactReference> getContactReference() {
        return contactReference;
    }

    public void setContactReference(List<ContactReference> contactReference) {
        this.contactReference = contactReference;
    }

    public List<WorkExperience> getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(List<WorkExperience> workExperience) {
        this.workExperience = workExperience;
    }

    public List<ProfessionalSkills> getProfessionalSkills() {
        return professionalSkills;
    }

    public void setProfessionalSkills(List<ProfessionalSkills> professionalSkills) {
        this.professionalSkills = professionalSkills;
    }

    public List<Education> getEducation() {
        return education;
    }

    public void setEducation(List<Education> education) {
        this.education = education;
    }
}
