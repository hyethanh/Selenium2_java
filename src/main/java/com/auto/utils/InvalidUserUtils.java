package com.auto.utils;

import com.auto.model.User;
import com.google.common.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.auto.utils.Constants.ConfigFiles;
import static com.auto.utils.Constants.INVALID_ACCOUNT;

public class InvalidUserUtils {

    private static InvalidUserUtils instance = null;
    private List<User> invalidUsers;


    private InvalidUserUtils() {
        Type userListType = new TypeToken<ArrayList<User>>() {
        }.getType();

        this.invalidUsers = JsonUtils.toList(ConfigFiles.get(INVALID_ACCOUNT), userListType);
    }

    public List<User> users() {
        return this.invalidUsers;
    }

    public static InvalidUserUtils instance() {
        if (instance == null) {
            instance = new InvalidUserUtils();
        }
        return instance;
    }

    public User getInvalidUser(int index) {
        Random r = new Random();
        return this.invalidUsers.get(index);
    }

}
