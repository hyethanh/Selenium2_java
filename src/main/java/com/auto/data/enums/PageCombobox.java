package com.auto.data.enums;

public enum PageCombobox {
    DISPLAY_AFTER("Display After"),
    PARENT_PAGE("Parent Page"),
    COLUMNS("Number of Columns");


    private String value;

    PageCombobox(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
