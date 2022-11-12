package com.auto.data.enums;

public enum Combobox {
    DISPLAY_AFTER("Display After"),
    PARENT_PAGE("Parent Page"),
    COLUMNS("Number of Columns"),
    SERIES("Series *"),
    DATA_PROFILE("Data Profile");


    private String value;

    Combobox(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
