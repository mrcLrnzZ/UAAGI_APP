package com.example.uaagi_app.data.model.PreEmploymentForm;

public class UserInfo {
    private String userId;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String dob;
    private String age;
    private String gender;
    private String religion;
    private String civilStatus;
    private String nationality;
    private String height;
    private String weight;
    private String bloodType;
    private String cellNo;
    private String telNo;
    private String currentAddress;
    private String permanentAddress;

    public UserInfo(String userId, String email, String firstName, String middleName, String lastName, String dob, String age, String gender, String religion, String civilStatus, String nationality, String height, String weight, String bloodType, String cellNo, String telNo, String currentAddress, String permanentAddress) {
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
        this.permanentAddress = permanentAddress != null ? permanentAddress : "";;
    }
    public UserInfo() {
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
    }

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

