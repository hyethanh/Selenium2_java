package com.auto.utils;

import com.auto.model.User;
import com.google.common.reflect.TypeToken;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.auto.utils.Constants.*;


public class UserUtils {
    private static UserUtils instance = null;
    private List<User> users;

    static JsonObject user;

    private UserUtils() {
        Type userListType = new TypeToken<ArrayList<User>>() {
        }.getType();
        this.users = JsonUtils.toList(ConfigFiles.get(ACCOUNT), userListType);
    }


    public List<User> users() {
        return this.users;
    }

    static {
        user = JsonUtils.to(ConfigFiles.get(ACCOUNT), JsonObject.class);
    }

    public static UserUtils instance() {
        if (instance == null) {
            instance = new UserUtils();
        }
        return instance;
    }

    public static String getUsername(String key) {
        return user.get(key).getAsString();
    }

    public static User getUser() {
        User valid_user = new User();
        valid_user.username(getUsername("valid.username"));
        valid_user.password(getPassword("valid.password"));
        return valid_user;
    }

    public static String getPassword(String key) {
        return user.get(key).getAsString();
    }
}
