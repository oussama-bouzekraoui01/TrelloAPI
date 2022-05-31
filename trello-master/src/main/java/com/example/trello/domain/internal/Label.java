package com.example.trello.domain.internal;

public class Label {
    private String value;

    public Label() {
    }

    public Label(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
