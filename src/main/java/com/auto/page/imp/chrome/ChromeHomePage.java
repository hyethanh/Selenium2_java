package com.auto.page.imp.chrome;

import com.auto.page.IHomePage;
import com.auto.utils.Assertion;
import com.logigear.element.Element;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ChromeHomePage extends ChromeGeneralPage implements IHomePage {

    private Element logoutTab = new Element(By.xpath("//a[text()='Logout']"));

    private Element accountTab = new Element(By.cssSelector("a[href='#Welcome']"));

    @Step("Verify login successfully")
    @Override
    public boolean isNavigatedToHomePage() {
        return logoutTab.isDisplayed();
    }

    @Step("Logout the account")
    @Override
    public void logout() {
        accountTab.hover();
        logoutTab.click();
    }

}
