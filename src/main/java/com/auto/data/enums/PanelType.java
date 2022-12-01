package com.auto.data.enums;

public enum PanelType {

    CHART("Chart"),
    INDICATOR("Indicator"),
    REPORT("Report"),
    HEAT_MAP("Heat Map");

    private String value;

    PanelType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
