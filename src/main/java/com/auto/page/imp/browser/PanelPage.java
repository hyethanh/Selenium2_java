package com.auto.page.imp.browser;

import com.auto.element.Element;
import com.auto.model.Panel;
import com.auto.page.IPanelPage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PanelPage implements IPanelPage {

    private Element profileNameTextBox = new Element(By.id("txtProfileName"));
    private Element finishButton = new Element(By.xpath("//input[@value='Finish']"));
    private Element linkButton = new Element("//a[text()='%s']");
    private Element createdPanels = new Element(By.xpath("//td[@class='center']/preceding-sibling::td[@class='chkCol']/input"));
    private Element createdPanelTable = new Element(By.xpath("//td[@class='center']/preceding-sibling::td[not (@class='chkCol')]/a"));
    private Element editButton = new Element(By.xpath("//li[@class='edit'and @title='Edit Panel']"));

    @Step("Click Add New link to create a new panel")
    public void clickLinkButton(String value) {
        linkButton.set(value);
        linkButton.click();
    }

    @Step("Verify table has the created panel")
    public boolean isPanelDisplayedInTable(Panel panel) {
        List<String> panelNameList = new ArrayList<>();
        for (WebElement element : createdPanelTable.elements()) {
            panelNameList.add(element.getText());
        }
        for (String name : panelNameList) {
            if (name.equals(panel.getName())) {
                return true;
            }
        }
        return false;
    }

    @Step("Enter profile name")
    public void enterProfileName(String value) {
        profileNameTextBox.enter(value);
    }

    @Step("Click Finish button")
    public void clickFinishButton() {
        finishButton.click();
        finishButton.waitForInvisible();
    }

    @Step("Click Edit Panel button")
    public void clickEditPanelButton() {
        editButton.click();
    }

    public List<String> getPanelIds() {
        if (createdPanels.exists()) {
            List<String> list = createdPanels.elements().stream().map(p -> p.getAttribute("value")).collect(Collectors.toList());
            return list;
        }
        return null;
    }
}