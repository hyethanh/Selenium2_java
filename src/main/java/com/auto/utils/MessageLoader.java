package com.auto.utils;

import com.google.gson.JsonObject;

public class MessageLoader {

    static JsonObject messages;

    static {
        messages = JsonUtils.to(Constants.MESSAGES, JsonObject.class);
    }

    public static String getMessage(String key) {
        return messages.get(key).getAsString();
    }
}
