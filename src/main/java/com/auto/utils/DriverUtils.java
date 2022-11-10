package com.auto.utils;

import com.logigear.element.Element;
import com.logigear.statics.Selaium;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;

import java.time.Duration;
import java.util.List;

public class DriverUtils {
    private static String alertMessage;

    public static String getAlertMessage() {
        try {
            alertMessage = Selaium.driver().switchTo().alert().getText().trim();
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

    public static void deletePage(List<String> ids) {
        JavascriptExecutor js = Selaium.remoteWebDriver();
        for (String id : ids) {
            js.executeScript(String.format("$.ajax( {\n" +
                    "\t\t\t\ttype : \"POST\",\n" +
                    "\t\t\t\turl : \"deletepage.do\",\n" +
                    "\t\t\t\tdata : $.param({\"id\" : \"%s\"}),\n" +
                    "\t\t\t\terror : function(request, textStatus, errorThrown) {\n" +
                    "\t\t\t\t\talert(MSG_CANNOT_CONNECT_TO_SERVER);\n" +
                    "\t\t\t\t},\n" +
                    "\t\t\t\tsuccess : function(receive) {\n" +
                    "\t\t\t\t\tif (receive.substring(0, 2) == CONST_OK) {\n" +
                    "\t\t\t\t\t\tdocument.location = receive.substring(3, receive.length);\n" +
                    "\t\t\t\t\t} else {\n" +
                    "\t\t\t\t\t\tDashboard.handleReturnError(receive);\n" +
                    "\t\t\t\t\t}\n" +
                    "\t\t\t\t}\n" +
                    "\t\t\t});", id));
        }
    }

    public static void deletePanel(List<String> ids) {
        for (String id : ids) {
            System.out.printf(id);
        }
        JavascriptExecutor js = Selaium.remoteWebDriver();
        for (String id : ids) {
            js.executeScript(String.format("$.ajax( {\n" +
                    "\t\t\t\ttype : \"POST\",\n" +
                    "\t\t\t\turl : \"delete_panels.do\",\n" +
                    "\t\t\t\tdata : $.param( {\n" +
                    "\t\t\t\t\"panelids\" : \"%s\"\n" +
                    "\t\t\t}),\n" +
                    "\t\t\t\terror : function(request, textStatus, errorThrown) {\n" +
                    "\t\t\t\t\talert(MSG_CANNOT_CONNECT_TO_SERVER);\n" +
                    "\t\t\t\t},\n" +
                    "\t\t\t\tsuccess : function(receive) {\n" +
                    "\t\t\t\t\tif (receive == CONST_OK) {\n" +
                    "\t\t\t\t\t\tlocation.reload();\n" +
                    "\t\t\t\t\t} else {\n" +
                    "\t\t\t\t\t\tDashboard.handleReturnError(receive);\n" +
                    "\t\t\t\t\t}\n" +
                    "\t\t\t\t}\n" +
                    "\t\t\t});", id));
        }
    }

    public static String getCurrentPageTitle() {
        return Selaium.driverContainer().title();
    }
}
