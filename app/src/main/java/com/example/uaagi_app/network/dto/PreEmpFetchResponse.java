package com.example.uaagi_app.network.dto;


import com.example.uaagi_app.network.dto.PreEmpDto.*;

public class PreEmpFetchResponse {
    private UserInfo userInfo;
    private Certificate certificate;
    private Qualification qualification;
    private Seminar seminar;
    private GovermentId govId;
    private ContactReference contactReference;
    private WorkExperience workExperience;
    private ProfessionalSkills professionalSkills;
    private Education education;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public Qualification getQualification() {
        return qualification;
    }

    public void setQualification(Qualification qualification) {
        this.qualification = qualification;
    }

    public Seminar getSeminar() {
        return seminar;
    }

    public void setSeminar(Seminar seminar) {
        this.seminar = seminar;
    }

    public GovermentId getGovId() {
        return govId;
    }

    public void setGovId(GovermentId govId) {
        this.govId = govId;
    }

    public ContactReference getContactReference() {
        return contactReference;
    }

    public void setContactReference(ContactReference contactReference) {
        this.contactReference = contactReference;
    }

    public WorkExperience getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(WorkExperience workExperience) {
        this.workExperience = workExperience;
    }

    public ProfessionalSkills getProfessionalSkills() {
        return professionalSkills;
    }

    public void setProfessionalSkills(ProfessionalSkills professionalSkills) {
        this.professionalSkills = professionalSkills;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }
}
