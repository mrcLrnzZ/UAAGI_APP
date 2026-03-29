package com.example.uaagi_app.network.dto;

import com.example.uaagi_app.network.dto.PreEmpDto.Certificate;
import com.example.uaagi_app.network.dto.PreEmpDto.Education;
import com.example.uaagi_app.network.dto.PreEmpDto.ProfessionalSkills;
import com.example.uaagi_app.network.dto.PreEmpDto.WorkExperience;

import java.util.List;

public class UpdateProfileDTO {
    private String lastName;
    private String firstName;
    private String middleName;
    private String dob;
    private String gender;
    private int age;
    private String religion;
    private String civilStatus;
    private String nationality;
    private String cellNo;
    private String telNo;
    private String email;
    private String currentAddress;
    private String permanentAddress;
    
    private List<Education> education;
    private List<WorkExperience> workExperience;
    private List<Certificate> certificate;
    private List<ProfessionalSkills> professionalSkills;

    public UpdateProfileDTO() {}

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getReligion() { return religion; }
    public void setReligion(String religion) { this.religion = religion; }

    public String getCivilStatus() { return civilStatus; }
    public void setCivilStatus(String civilStatus) { this.civilStatus = civilStatus; }

    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }

    public String getCellNo() { return cellNo; }
    public void setCellNo(String cellNo) { this.cellNo = cellNo; }

    public String getTelNo() { return telNo; }
    public void setTelNo(String telNo) { this.telNo = telNo; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCurrentAddress() { return currentAddress; }
    public void setCurrentAddress(String currentAddress) { this.currentAddress = currentAddress; }

    public String getPermanentAddress() { return permanentAddress; }
    public void setPermanentAddress(String permanentAddress) { this.permanentAddress = permanentAddress; }

    public List<Education> getEducation() { return education; }
    public void setEducation(List<Education> education) { this.education = education; }

    public List<WorkExperience> getWorkExperience() { return workExperience; }
    public void setWorkExperience(List<WorkExperience> workExperience) { this.workExperience = workExperience; }

    public List<Certificate> getCertificate() { return certificate; }
    public void setCertificate(List<Certificate> certificate) { this.certificate = certificate; }

    public List<ProfessionalSkills> getProfessionalSkills() { return professionalSkills; }
    public void setProfessionalSkills(List<ProfessionalSkills> professionalSkills) { this.professionalSkills = professionalSkills; }
}
