package com.auto.data.enums;

public enum ChartLegends {

    NONE("None"),
    TOP("Top"),
    RIGHT("Right"),
    BOTTOM("Bottom"),
    LEFT("Left");

    private String value;

    ChartLegends(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
