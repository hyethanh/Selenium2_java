package com.auto.data.enums;

public enum ChartType {

    PIE("Pie"),
    SINGLE_BAR("Single Bar"),
    STACKED_BAR("Stacked Bar"),
    GROUP_BAR("Group Bar"),
    LINE("Line");

    private String value;

    ChartType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
