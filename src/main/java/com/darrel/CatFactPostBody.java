package com.darrel;

public class CatFactPostBody {
    private final String title = "Cat Fact";
    private final String text;

    public CatFactPostBody(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
