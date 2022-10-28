package com.auto.utils;

import com.auto.model.User;
import com.google.common.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.auto.utils.Constants.*;


public class UserUtils {
    private static UserUtils instance = null;
    private List<User> users;

    private UserUtils() {
        Type userListType = new TypeToken<ArrayList<User>>() {
        }.getType();
        this.users = JsonUtils.toList(ConfigFiles.get(ACCOUNT), userListType);
    }


    public List<User> users() {
        return this.users;
    }

    public static UserUtils instance() {
        if (instance == null) {
            instance = new UserUtils();
        }
        return instance;
    }

    public User getUser() {
        Random r = new Random();
        return this.users.get(r.nextInt(this.users.size()));
    }

    public User getInvalidUser(int index) {
        Random r = new Random();
        return this.users.get(index);
    }
}
