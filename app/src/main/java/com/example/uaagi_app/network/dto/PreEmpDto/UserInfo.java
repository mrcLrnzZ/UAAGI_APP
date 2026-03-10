package com.example.uaagi_app.network.dto.PreEmpDto;

import com.google.gson.annotations.SerializedName;

public class UserInfo {
    @SerializedName("preemp_id")
    private String preempId;
    @SerializedName("user_id")
    private String userId;
    private String email;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("middle_name")
    private String middleName;
    @SerializedName("last_name")
    private String lastName;
    private String dob;
    private String age;
    private String gender;
    private String religion;
    @SerializedName("civil_status")
    private String civilStatus;
    private String nationality;
    private String height;
    private String weight;
    @SerializedName("blood_type")
    private String bloodType;
    @SerializedName("cell_no")
    private String cellNo;
    @SerializedName("tel_no")
    private String telNo;
    @SerializedName("current_address")
    private String currentAddress;
    @SerializedName("permanent_address")
    private String permanentAddress;
    @SerializedName("emergency_name")
    private String emergencyName;
    @SerializedName("emergency_relationship")
    private String emergencyRelationship;
    @SerializedName("emergency_contact")
    private String emergencyContact;
    @SerializedName("msword")
    private String msWord;
    @SerializedName("msexcel")
    private String msExcel;
    @SerializedName("msppt")
    private String msPpt;
    @SerializedName("msoutlook")
    private String msOutlook;
    @SerializedName("created_at")
    private String createdAt;

    public UserInfo(String preempId,String userId, String email, String firstName, String middleName, String lastName, String dob, String age,
                    String gender, String religion, String civilStatus, String nationality, String height, String weight, String bloodType,
                    String cellNo, String telNo, String currentAddress, String permanentAddress,
                    String emergencyName, String emergencyRelationship, String emergencyContact, String msWord, String msExcel, String msPpt,
                    String msOutlook, String createdAt
    ) {
        this.preempId = preempId != null ? preempId : "";
        this.userId = userId != null ? userId : "";
        this.email = email != null ? email : "";
        this.firstName = firstName != null ? firstName : "";
        this.middleName = middleName != null ? middleName : "";
        this.lastName = lastName != null ? lastName : "";
        this.dob = dob != null ? dob : "";
        this.age = age != null ? age : "";
        this.gender = gender != null ? gender : "";
        this.religion = religion != null ? religion : "";
        this.civilStatus = civilStatus != null ? civilStatus : "";
        this.nationality = nationality != null ? nationality : "";
        this.height = height != null ? height : "";
        this.weight = weight != null ? weight : "";
        this.bloodType = bloodType != null ? bloodType : "";
        this.cellNo = cellNo != null ? cellNo : "";
        this.telNo = telNo != null ? telNo : "";
        this.currentAddress = currentAddress != null ? currentAddress : "";
        this.permanentAddress = permanentAddress != null ? permanentAddress : "";
        this.emergencyName = emergencyName != null ? emergencyName : "";
        this.emergencyRelationship = emergencyRelationship != null ? emergencyRelationship : "";
        this.emergencyContact = emergencyContact != null ? emergencyContact : "";
        this.msWord = msWord != null ? msWord : "";
        this.msExcel = msExcel != null ? msExcel : "";
        this.msPpt = msPpt != null ? msPpt : "";
        this.msOutlook = msOutlook != null ? msOutlook : "";
        this.createdAt = createdAt != null ? createdAt : "";

    }
    public UserInfo() {
        this.preempId = "";
        this.userId = "";
        this.email = "";
        this.firstName = "";
        this.middleName = "";
        this.lastName = "";
        this.dob = "";
        this.age = "";
        this.gender = "";
        this.religion = "";
        this.civilStatus = "";
        this.nationality = "";
        this.height = "";
        this.weight = "";
        this.bloodType = "";
        this.cellNo = "";
        this.telNo = "";
        this.currentAddress = "";
        this.permanentAddress = "";
        this.emergencyName = "";
        this.emergencyRelationship = "";
        this.emergencyContact = "";
        this.msWord = "";
        this.msExcel = "";
        this.msPpt = "";
        this.msOutlook = "";
        this.createdAt = "";
    }

    public void setPreempId(String preempId) { this.preempId = preempId; }
    public String getPreempId() { return preempId; }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmergencyName() {
        return emergencyName;
    }

    public void setEmergencyName(String emergencyName) {
        this.emergencyName = emergencyName;
    }

    public String getEmergencyRelationship() {
        return emergencyRelationship;
    }

    public void setEmergencyRelationship(String emergencyRelationship) {
        this.emergencyRelationship = emergencyRelationship;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getMsWord() {
        return msWord;
    }

    public void setMsWord(String msWord) {
        this.msWord = msWord;
    }

    public String getMsExcel() {
        return msExcel;
    }

    public void setMsExcel(String msExcel) {
        this.msExcel = msExcel;
    }

    public String getMsPpt() {
        return msPpt;
    }

    public void setMsPpt(String msPpt) {
        this.msPpt = msPpt;
    }

    public String getMsOutlook() {
        return msOutlook;
    }

    public void setMsOutlook(String msOutlook) {
        this.msOutlook = msOutlook;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getCellNo() {
        return cellNo;
    }

    public void setCellNo(String cellNo) {
        this.cellNo = cellNo;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }
}

