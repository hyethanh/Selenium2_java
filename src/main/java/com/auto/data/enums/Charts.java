package com.auto.data.enums;

import java.util.Random;

public enum Charts {

    ACTION_BY_STATUS("Action Implementation By Status"),
    TEST_CASE_FAIL_TREND("Test Case Execution Failure Trend"),
    TEST_CASE_RESULT("Test Case Execution Results"),
    TEST_CASE_TREND("Test Case Execution Trend"),
    MODULE_FAIL_TREND("Test Module Execution Failure Trend"),
    MODULE_RESULT("Test Module Execution Results"),
    MODULE_EXECUTION_TREND("Test Module Execution Trend"),
    IMPLEMENT_BY_PRIORITY("Test Module Implementation By Priority"),
    IMPLEMENT_BY_STATUS("Test Module Implementation By Status"),
    MODULE_STATUS_PER_ASSIGNED_USERS("Test Module Status per Assigned Users");

    private String value;

    private static final Random random = new Random();

    Charts(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static Charts randomCharts() {
        return values()[random.nextInt(values().length)];
    }
}
