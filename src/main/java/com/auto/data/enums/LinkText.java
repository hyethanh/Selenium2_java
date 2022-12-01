package com.auto.data.enums;

public enum LinkText {

    ADD_PAGE("Add Page"),
    ADD_PANEL("Add Panel"),
    EDIT("Edit"),
    DELETE("Delete"),
    ADMINISTER("Administer"),
    PANELS("Panels"),
    DATA_PROFILES("Data Profiles"),
    ADD_NEW("Add New"),
    ADMINISTRATOR("administrator"),
    CHECK_ALL("Check All"),
    UNCHECK_ALL("UnCheck All");

    private String value;

    LinkText(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
