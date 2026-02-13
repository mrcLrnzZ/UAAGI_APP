package com.example.uaagi_app.data.model.PreEmploymentForm;

public class Seminar {
    private String type;
    private String title;
    private String organizer;
    private String date;
    private String description;

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
