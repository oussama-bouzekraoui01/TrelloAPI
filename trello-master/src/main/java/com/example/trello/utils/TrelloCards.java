package com.example.trello.utils;

public class TrelloCards {

    public String name;
    public String description;
    public final String idBoard = "6220dee6e58fd671deefe515";

    public TrelloCards(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdBoard() {
        return idBoard;
    }
}
