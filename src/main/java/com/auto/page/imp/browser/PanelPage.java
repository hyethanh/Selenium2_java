package com.auto.page.imp.browser;

import com.auto.element.Element;
import com.auto.page.IPanelPage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class PanelPage implements IPanelPage {

    private Element addNewLink = new Element(By.linkText("Add New"));

    @Step("Click Add New link to create a new panel")
    public void clickAddNewLink() {
        addNewLink.click();
    }
}
