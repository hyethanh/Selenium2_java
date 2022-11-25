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
    ADMINISTRATOR("administrator");

    private String value;

    LinkText(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
