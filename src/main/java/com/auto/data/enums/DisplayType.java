package com.auto.data.enums;

public enum DisplayType {

    CHAR("Chart"),
    INDICATOR("Indicator"),
    REPORT("Report"),
    HEAT_MAP("Heat Map");

    private String value;

    DisplayType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
