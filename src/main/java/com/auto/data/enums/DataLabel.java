package com.auto.data.enums;

public enum DataLabel {

    SERIES("Series"),
    CATEGORIES("Categories"),
    VALUE("Value"),
    PERCENTAGE("Percentage");
    private String value;

    DataLabel(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
