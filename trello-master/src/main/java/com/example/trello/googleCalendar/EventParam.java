package com.example.trello.googleCalendar;

public class EventParam {
    private String summary;
    private String location;
    private String description;
    private String start ;
    private String end;
    private String[] participant;

    public EventParam(String summary, String location, String description, String start, String end, String[] participant) {
        this.summary = summary;
        this.location = location;
        this.description = description;
        this.start = start;
        this.end = end;
        this.participant = participant;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String[] getParticipant() {
        return participant;
    }

    public void setParticipant(String[] participant) {
        this.participant = participant;
    }
}
