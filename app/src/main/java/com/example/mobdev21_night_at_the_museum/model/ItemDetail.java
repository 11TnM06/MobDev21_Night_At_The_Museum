package com.example.mobdev21_night_at_the_museum.model;

public class ItemDetail {
    private String title;
    private String description;

    public ItemDetail() {
    }

    public ItemDetail(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void mapFrom(String string) {
        String[] split = string.split(":");
        if (split.length == 2) {
            title = split[0] + ": ";
            description = split[1].trim();
        }
    }

    @Override
    public String toString() {
        return "<b>" + title + "</b>: " + description + "<br>";
    }
}