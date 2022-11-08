package com.auto.utils;

import com.google.gson.JsonObject;

import static java.lang.String.format;

public class MessageLoader {

    static JsonObject messages;

    static {
        messages = JsonUtils.to(Constants.MESSAGES, JsonObject.class);
    }

    public static String getMessage(String key) {
        return messages.get(key).getAsString();
    }

    public static String getMessage(String key, String value) {
        return format(messages.get(key).getAsString(), value);
    }
}
