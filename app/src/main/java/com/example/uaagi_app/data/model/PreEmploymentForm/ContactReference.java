package com.example.uaagi_app.data.model.PreEmploymentForm;

public class ContactReference {
    private String name;
    private String occupation;
    private String company;
    private String phone;

    public ContactReference(String name, String occupation, String company, String phone) {
        this.name = name;
        this.occupation = occupation;
        this.company = company;
        this.phone = phone;
    }

    public ContactReference() {
        this.name = name != null ? name : "";
        this.occupation = occupation != null ? occupation : "";
        this.company = company != null ? company : "";
        this.phone = phone != null ? phone : "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
