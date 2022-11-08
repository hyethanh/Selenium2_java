package com.auto.data.enums;

public enum MenuItem {

    ADD("add"),
    EDIT("edit"),
    DELETE("delete"),
    ADMINISTRATOR("administrator");

    private String value;

    MenuItem(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
