package com.auto.model;

import com.auto.utils.JsonUtils;
import com.google.common.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.auto.utils.Constants.ConfigFiles;
import static com.auto.utils.Constants.INVALID_ACCOUNT;

public class InvalidUser {

    private static InvalidUser instance = null;
    private List<UserModel> invalidUsers;


    private InvalidUser() {
        Type userListType = new TypeToken<ArrayList<UserModel>>() {
        }.getType();

        this.invalidUsers = JsonUtils.toList(ConfigFiles.get(INVALID_ACCOUNT), userListType);
    }

    public List<UserModel> users() {
        return this.invalidUsers;
    }

    public static InvalidUser instance() {
        if (instance == null) {
            instance = new InvalidUser();
        }
        return instance;
    }

    public UserModel getInvalidUser(int index) {
        Random r = new Random();
        return this.invalidUsers.get(index);
    }

}
