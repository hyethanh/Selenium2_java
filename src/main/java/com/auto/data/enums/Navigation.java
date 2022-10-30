package com.auto.data.enums;

public enum Navigation {
    HOME("Home"),
    OVERVIEW("Overview"),
    DISPLAY_AFTER("Display After"),
    PARENT_PAGE("Parent Page");

    private String value;

    Navigation(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
