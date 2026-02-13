package com.example.uaagi_app.data.model.PreEmploymentForm;

import java.util.List;

//HOW TO USE
//Education edu = new Education();
//edu.setLevel("College");
//edu.setSchool("Ateneo");
//edu.setGradYear("2024");
//
//        List<Education> list = new ArrayList<>();
//list.add(edu);
//
//        PreEmpFormRequest request = new PreEmpFormRequest();
//request.setEducations(list);
//request.setUserInfo(userInfo);
public class PreEmpFormDataModel {

    private UserInfo userInfo;
    private List<Education> educations;
    private List<WorkExperience> workExp;
    private List<Skill> skills;
    private List<Certificate> certificates;
    private List<Qualification> qualifications;
    private List<Seminar> seminars;
    private List<GovId> govIds;
    private List<ContactReference> contactReferences;
    private EmergencyContact emergency;
    private OfficeSkills officeSkills;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<Education> getEducations() {
        return educations;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }

    public List<WorkExperience> getWorkExp() {
        return workExp;
    }

    public void setWorkExp(List<WorkExperience> workExp) {
        this.workExp = workExp;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<Certificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<Certificate> certificates) {
        this.certificates = certificates;
    }

    public List<Qualification> getQualifications() {
        return qualifications;
    }

    public void setQualifications(List<Qualification> qualifications) {
        this.qualifications = qualifications;
    }

    public List<Seminar> getSeminars() {
        return seminars;
    }

    public void setSeminars(List<Seminar> seminars) {
        this.seminars = seminars;
    }

    public List<GovId> getGovIds() {
        return govIds;
    }

    public void setGovIds(List<GovId> govIds) {
        this.govIds = govIds;
    }

    public List<ContactReference> getContactReferences() {
        return contactReferences;
    }

    public void setContactReferences(List<ContactReference> contactReferences) {
        this.contactReferences = contactReferences;
    }

    public EmergencyContact getEmergency() {
        return emergency;
    }

    public void setEmergency(EmergencyContact emergency) {
        this.emergency = emergency;
    }

    public OfficeSkills getOfficeSkills() {
        return officeSkills;
    }

    public void setOfficeSkills(OfficeSkills officeSkills) {
        this.officeSkills = officeSkills;
    }
}
