package com.auto.model;

import com.github.javafaker.Faker;

public class ChildPage {
    private static Faker faker = new Faker();
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle() {
        this.title = faker.file().fileName();
    }
}
