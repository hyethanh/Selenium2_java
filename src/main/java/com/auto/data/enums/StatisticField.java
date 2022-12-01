package com.auto.data.enums;

import java.util.Random;

public enum StatisticField {

    ASSIGNED_USER("Assigned user"),
    STATUS("Status"),
    LAST_UPDATE_BY("Last updated by"),
    CREATED_BY("Created by");
    private String value;

    private static final Random random = new Random();

    StatisticField(String value) {
        this.value = value;
    }

    public String value() {
        return "\u00A0 "+value;
    }

    public static StatisticField randomStatisticField() {
        return values()[random.nextInt(values().length)];
    }
}
