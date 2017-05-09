package com.darrel;

public class AnimalFactPostBody {
    private final String title;
    private final String text;

    public AnimalFactPostBody(String title, String text) {
        this.text = text;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
