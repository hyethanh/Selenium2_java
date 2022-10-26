package com.auto.utils;

import com.logigear.element.Element;
import com.logigear.statics.Selaium;
import org.openqa.selenium.StaleElementReferenceException;

public class DriverUtils {

    private static String alertMessage;

    public static String getAlertMessage() {
        try {
            alertMessage = Selaium.driver().switchTo().alert().getText();
        } catch (Exception ex) {
            ex.getMessage();
        }
        return alertMessage;
    }

    public static void acceptAlert() {
        Selaium.driver().switchTo().alert().accept();
    }

    public static void dismissAlert() {
        Selaium.driver().switchTo().alert().dismiss();
    }

    public static void refresh() {
        Selaium.refresh();
    }

    public static void stalenessOf(Element element) {
        try{
            element.waitForVisible();
        } catch (StaleElementReferenceException e) {
            System.out.printf(e.getMessage());
            element.element();
        }
    }
}
