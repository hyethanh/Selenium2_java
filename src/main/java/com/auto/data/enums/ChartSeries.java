package com.auto.data.enums;

import java.util.Random;

public enum ChartSeries {

    NAME("Name"),
    LOCATION("Location"),
    DESCRIPTION("Description"),
    REVISION_TIMESTAMP("Revision Timestamp"),
    ASSIGNED_USER("Assigned user"),
    STATUS("Status"),
    LAST_UPDATE_DATE("Last update date"),
    LAST_UPDATE_BY("Last updated by"),
    CREATION_DATE("Creation date"),
    CREATION_BY("Created by"),
    NOTES("Notes"),
    URL("URL");

    private String value;

    private static final Random random = new Random();

    ChartSeries(String value) {
        this.value = value;
    }

    public String value() {
        return "\u00A0 "+value;
    }

    public static ChartSeries randomSeries() {
        return values()[random.nextInt(values().length)];
    }
}
