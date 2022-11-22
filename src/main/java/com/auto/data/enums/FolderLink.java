package com.auto.data.enums;

import java.util.Random;

public enum FolderLink {

    CAR_RENTAL("/Car Rental/Actions"),
    MUSIC_LIBRARY("/Music Library/Actions"),
    SCRIPTING_TECHNICAL("/Scripting techniques sample/Actions"),
    SCRUM_BOARD("/Scrum Board/Actions");

    private String value;

    private static final Random random = new Random();

    FolderLink(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static FolderLink randomFolderLink() {
        return values()[random.nextInt(values().length)];
    }
}
