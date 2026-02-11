package com.example.uaagi_app.data.model;

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
public class PreEmpForm {

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
