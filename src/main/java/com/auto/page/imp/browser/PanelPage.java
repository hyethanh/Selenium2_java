package com.auto.page.imp.browser;

import com.auto.data.enums.LinkText;
import com.auto.element.Element;
import com.auto.model.Panel;
import com.auto.page.IPanelPage;
import com.auto.utils.DriverUtils;
import com.auto.utils.StringUtils;
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
    private Element editButton = new Element("//div[div[text()=\"%s\"]]//following-sibling::div//li[@class='edit' and @title='Edit Panel']");
    private Element panelTitle = new Element("//div[@title='%s']");
    private Element createdPanelContents = new Element(By.xpath("//li[@class='widget']/div"));
    private Element projectLabel = new Element("//div[text()=\"%s\"]/ancestor::div[@class='cbox']//td[@align='left']");
    private Element folderLabel = new Element("//div[text()=\"%s\"]/ancestor::div[@class='cbox']//td[@align='right']");
    private Element panelActionButton = new Element("//td[a[text()=\"%s\"]]/following-sibling::td/a[text()=\"%s\"]");
    private Element moreOptionButton = new Element("//div[div[text()=\"%s\"]]//following-sibling::div//li[@class='more']");
//    private Element deleteButton = new Element("//div[div[text()=\"%s\"]]//following-sibling::div//span[text()='Remove']");
    private Element deleteButton = new Element(By.xpath("//span[text()='Remove']"));

    @Step("Click Add New link to create a new panel")
    public void clickLinkButton(LinkText value) {
        linkButton.set(value.value());
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
    public void clickEditPanelButton(Panel panel) {
        editButton.set(panel.getName());
        editButton.click();
    }

    @Step("Verify panel is created successfully")
    public boolean isPanelCreated(Panel panel) {
        panelTitle.set(StringUtils.replaceSpaceCharWithNBSP(panel.getName()));
        return panelTitle.isDisplayed() && panelTitle.exists();
    }

    @Step("Verify the newly created folder path is correct as selected")
    public boolean isFolderPathAsSelected(Panel panel, String link) {
        projectLabel.set(StringUtils.replaceSpaceCharWithNBSP(panel.getName()));
        folderLabel.set(StringUtils.replaceSpaceCharWithNBSP(panel.getName()));
        String project = projectLabel.getText().replace("Project: ","");
        String folder = folderLabel.getText().replace("Folder: ","");

        return link.equals(String.format("/%s/%s", project, folder));
    }

    public List<String> getPanelIds() {
        if (createdPanels.exists()) {
            List<String> list = createdPanels.elements().stream().map(p -> p.getAttribute("value")).collect(Collectors.toList());
            return list;
        }
        return null;
    }

    public List<String> getPanelContentIds() {
        if (createdPanelContents.isDisplayed() && createdPanelContents.exists()) {
            List<String> list = createdPanelContents.elements().stream().map(p -> p.getAttribute("id")).collect(Collectors.toList());
            return list;
        }
        return null;
    }

    @Step("Click Action Button")
    public void clickActionButton(Panel panel, LinkText action) {
        panelActionButton.set(panel.getName(), action.value());
        panelActionButton.click();
    }

    @Step("Remove Panel")
    public void removePanel(Panel panel) {
        moreOptionButton.set(StringUtils.replaceSpaceCharWithNBSP(panel.getName()));
        moreOptionButton.hover();
        deleteButton.click();
        DriverUtils.acceptAlert();
    }
}
