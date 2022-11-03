package com.auto.page.imp.browser;

import com.auto.page.IHomePage;
import com.logigear.element.Element;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class HomePage extends GeneralPage implements IHomePage {

    private Element logoutButton = new Element(By.xpath("//a[text()='Logout']"));

    private Element accountTab = new Element(By.cssSelector("a[href='#Welcome']"));

    @Step("Verify login successfully")
    @Override
    public boolean isNavigated() {
        return logoutButton.exists();
    }

    @Step("Logout the account")
    @Override
    public void logout() {
        accountTab.hover();
        logoutButton.waitForVisible();
        logoutButton.click();
    }
}
