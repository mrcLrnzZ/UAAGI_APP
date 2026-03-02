package com.example.uaagi_app.network.dto.PreEmpDto;

public class ProfessionalSkills {
    private String skillId;
    private String preempId;
    private String category;
    private String level;
    private String description;

    public ProfessionalSkills(String skillId, String preempId, String category, String level, String description) {
        this.skillId = skillId != null ? skillId : "";
        this.preempId = preempId != null ? preempId : "";
        this.category = category != null ? category : "";
        this.level = level != null ? level : "";
        this.description = description != null ? description : "";
    }

    public String getSkillId() {
        return skillId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
    }

    public String getPreempId() {
        return preempId;
    }

    public void setPreempId(String preempId) {
        this.preempId = preempId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}