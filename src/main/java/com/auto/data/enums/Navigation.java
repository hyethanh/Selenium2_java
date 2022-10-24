package com.auto.data.enums;

public enum Navigation {
    HOME("Home"),
    INVOICES("Invoices"),
    SETTINGS("Settings"),
    ITEMS("Items"),
    OVERVIEW("Overview"),
    EXPENSES("Expenses");

    private String value;

    Navigation(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
