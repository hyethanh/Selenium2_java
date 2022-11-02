package com.auto.utils;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final Map<String, String> ConfigFiles = new HashMap<>();
    public static final String ENV_ALLURE_FILE = "allure-results/environment.properties";
    public static final String BROWSER = "chrome";
    public static final String ACCOUNT = "account";
    public static final String TEST_RAIL = "testrail";
    public static final String MESSAGES = "src/test/resources/data/messages.json";
    public static final String BASE_URL = "http://localhost/TADashboard/login.jsp";

    public static final int LOADING_TIME = 5;
    public static final int LONG_TIME = 60;

    static {
        ConfigFiles.put(ACCOUNT, "src/test/resources/data/accounts.json");
        }
}
