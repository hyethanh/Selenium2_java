package com.auto.utils;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class Assertion {

    public static SoftAssert softAssertion= new SoftAssert();

    public static void assertTrue(boolean condition, String message) {
        try {
            softAssertion.assertTrue(condition, message);
            Allure.step(message);
        } catch (AssertionError ex) {
            Allure.step(message, Status.FAILED);
            throw ex;
        }
    }

    public static void asserFalse(boolean condition, String message) {
        try {
            softAssertion.assertFalse(condition, message);
            softAssertion.assertAll();
        } catch (AssertionError ex) {
            Allure.step(message, Status.FAILED);
            throw ex;
        }
    }

    public static void assertEquals(String actual, String expect, String message) {
        try {
            softAssertion.assertEquals(actual, expect, message);
            Allure.step(message);
        } catch (AssertionError ex) {
            Allure.step(message, Status.FAILED);
            throw ex;
        }
    }

    public static void assertAll(String message) {
        try {
            softAssertion.assertAll();
            Allure.step(message);
        } catch (AssertionError ex) {
            Allure.step(message, Status.FAILED);
            throw ex;
        }
    }
}
