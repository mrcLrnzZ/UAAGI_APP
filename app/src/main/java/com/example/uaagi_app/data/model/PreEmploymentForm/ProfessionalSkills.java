package com.example.uaagi_app.data.model.PreEmploymentForm;

public class ProfessionalSkills {
    private String  category;
    private String level;
    private String description;
    public ProfessionalSkills(String category, String level, String description) {
        this.category = category != null ? category : "";
        this.level = level != null ? level : "";
        this.description = description != null ? description : "";
    }
    public ProfessionalSkills() {
        this.category = "";
        this.level = "";
        this.description = "";
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}