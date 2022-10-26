package com.auto.data.enums;

public enum Navigation {
    HOME("Home"),
    OVERVIEW("Overview"),
    DISPLAYAFTER("Display After");

    private String value;

    Navigation(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
