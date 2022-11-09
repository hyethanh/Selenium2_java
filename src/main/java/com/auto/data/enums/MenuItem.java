package com.auto.data.enums;

public enum MenuItem {

    ADD_PAGE("Add Page"),
    ADD_PANEL("Add Panel"),
    EDIT("Edit"),
    DELETE("Delete"),
    ADMINISTER("Administer"),
    PANELS("Panels"),
    ADMINISTRATOR("administrator");

    private String value;

    MenuItem(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
