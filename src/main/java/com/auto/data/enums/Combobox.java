package com.auto.data.enums;

public enum Combobox {
    DISPLAY_AFTER("Display After"),
    PARENT_PAGE("Parent Page"),
    COLUMNS("Number of Columns"),
    SERIES("Series *"),
    CATEGORY("Category *"),
    DATA_PROFILE("Data Profile"),
    CHART_TYPE("Chart Type");

    private String value;
    Combobox(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
