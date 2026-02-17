package com.example.uaagi_app.data.model.PreEmploymentForm;

public class EmergencyContact {
    private String name;
    private String contact;
    private String relationship;

    public EmergencyContact(String name, String contact, String relationship) {
        this.name = name != null ? name : "";
        this.contact = contact != null ? contact : "";
        this.relationship = relationship != null ? relationship : "";
    }
    public EmergencyContact() {
        this.name = "";
        this.contact = "";
        this.relationship = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
}

