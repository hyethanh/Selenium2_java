package com.auto.data.enums;

import java.util.Random;

public enum Category {

    NAME("Name"),
    LOCATION("Location"),
    TITLE("Title"),
    RECENT_RESULT("Recent result"),
    NOTES("Notes"),
    SOURCE("Source"),
    URL("URL");

    private String value;

    private static final Random random = new Random();

    Category(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static Category randomCategory() {
        return values()[random.nextInt(values().length)];
    }
}
