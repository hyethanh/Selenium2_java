package com.auto.utils;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import org.testng.Assert;


public class Assertion {
    public static void assertTrue(boolean condition, String message) {
        try {
            Assert.assertTrue(condition, message);
            Allure.step(message);
        } catch (AssertionError ex) {
            Allure.step(message, Status.FAILED);
            throw ex;
        }
    }

    public static void asserFalse(boolean condition, String message) {
        try {
            Assert.assertFalse(condition, message);
            Allure.step(message);
        } catch (AssertionError ex) {
            Allure.step(message, Status.FAILED);
            throw ex;
        }
    }

    public  static void assertEquals(String actual, String expect, String message) {
        try {
            Assert.assertEquals(actual, expect, message);
            Allure.step(message);
        } catch (AssertionError ex) {
            Allure.step(message, Status.FAILED);
            throw ex;
        }
    }
}
