package com.auto.page.imp.chrome;

import com.auto.page.IHomePage;
import com.auto.utils.Assertion;
import com.logigear.element.Element;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ChromeHomePage extends ChromeGeneralPage implements IHomePage {

    private Element logoutTab = new Element(By.xpath("//a[text()='Logout']"));

    @Step("Verify login successfully")
    @Override
    public boolean isNavigatedToHomePage() {
        return logoutTab.exists();
    }
}
