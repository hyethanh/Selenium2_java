package com.auto.data.enums;

import java.util.Random;

public enum Folder {

    CAR_RENTAL("Car Rental"),
    MUSIC_LIBRARY("Music Library"),
    SCRIPTING_TECHNICAL("Scripting techniques sample"),
    SCRUM_BOARD("Scrum Board");

    private String value;

    private static final Random random = new Random();

    Folder(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static String randomFolderLink() {
        return String.format("/%s/Actions",values()[random.nextInt(values().length)]);
    }

    public static Folder randomFolder() {
        return values()[random.nextInt(values().length)];
    }
}
