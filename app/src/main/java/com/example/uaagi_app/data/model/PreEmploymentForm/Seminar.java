package com.example.uaagi_app.data.model.PreEmploymentForm;

public class Seminar {
    private String type;
    private String title;
    private String organizer;
    private String date;
    private String description;

    public Seminar(String type, String title, String organizer, String date, String description) {
        this.type = type != null ? type : "";
        this.title = title != null ? title : "";
        this.organizer = organizer != null ? organizer : "";
        this.date = date != null ? date : "";
        this.description = description != null ? description : "";
    }
    public Seminar() {
        this.type = "";
        this.title = "";
        this.organizer = "";
        this.date = "";
        this.description = "";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
//$Seminars[] = [
//        'type'        => isset($seminar['type']) ? trim($seminar['type']) : '',
//        'title'       => isset($seminar['title']) ? trim($seminar['title']) : '',
//        'organizer'   => isset($seminar['organizer']) ? trim($seminar['organizer']) : '',
//        'date'        => isset($seminar['date']) ? trim($seminar['date']) : '',
//        'description' => isset($seminar['description']) ? trim($seminar['description']) : ''
//        ];
