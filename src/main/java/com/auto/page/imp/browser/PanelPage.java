package com.auto.page.imp.browser;

import com.auto.element.Element;
import com.auto.model.Panel;
import com.auto.page.IPanelPage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class PanelPage implements IPanelPage {

    private Element addNewLink = new Element(By.linkText("Add New"));
    private Element createdPanels = new Element(By.xpath("//td[@class='center']/preceding-sibling::td[not (@class='chkCol')]/a"));

    @Step("Click Add New link to create a new panel")
    public void clickAddNewLink() {
        addNewLink.click();
    }

    @Step("Verify table has the created panel")
    public boolean isPanelDisplayedInTable(Panel panel) {
        for (String name : getCreatedPanelList()) {
            if (name == panel.getName()) {
                return true;
            }
        }
        return false;
    }

    @Step("Get all created panels")
    protected List<String> getCreatedPanelList() {
        List<String> panelNameList = new ArrayList<>();
        for (WebElement element : createdPanels.elements()) {
            panelNameList.add(element.getText());
        }
        return panelNameList;
    }
}
