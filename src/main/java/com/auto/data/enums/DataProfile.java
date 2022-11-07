package com.auto.data.enums;

public enum DataProfile {

    ACTION_BY_STATUS("Action Implementation By Status"),
    FUNCTIONAL_TESTS("Functional Tests"),
    TEST_CASE_EXECUTION("Test Case Execution"),
    TEST_CASE_FAIL_TREND("Test Case Execution Failure Trend"),
    TEST_CASE_HISTORY("Test Case Execution History"),
    TEST_CASE_RESULT("Test Case Execution Results"),
    TEST_CASE_TREND("Test Case Execution Trend"),
    MODULE_EXECUTION("Test Module Execution"),
    MODULE_FAIL_TREND("Test Module Execution Failure Trend"),
    MODULE_FAIL_TREND_BY_BUILD("Test Module Execution Failure Trend by Build"),
    MODULE_HISTORY("Test Module Execution History"),
    MODULE_RESULT("Test Module Execution Results"),
    MODULE_RESULT_REPORT("Test Module Execution Results Report"),
    MODULE_EXECUTION_TREND("Test Module Execution Trend"),
    MODULE_EXECUTION_TREND_BY_BUILD("Test Module Execution Trend by Build"),
    IMPLEMENT_BY_PRIORITY("Test Module Implementation By Priority"),
    IMPLEMENT_BY_STATUS("Test Module Implementation By Status"),
    MODULE_STATUS_PER_ASSIGNED_USERS("Test Module Status per Assigned Users"),
    TEST_OBJECTIVE_EXECUTION("Test Objective Execution");

    private String value;

    DataProfile(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
