package com.auto.data.enums;

public enum MenuItem {

    OVERVIEW("Overview"),
    EXECUTION_DASHBOARD("Execution Dashboard"),
    ADMINISTRATOR("administrator");


    private String value;

    MenuItem(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
