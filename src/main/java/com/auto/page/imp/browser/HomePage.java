package com.auto.page.imp.browser;

import com.auto.data.enums.Navigation;
import com.auto.page.IHomePage;
import com.auto.utils.DriverUtils;
import com.logigear.element.Element;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends GeneralPage implements IHomePage {

    private Element logoutButton = new Element(By.xpath("//a[text()='Logout']"));
    private Element accountTab = new Element(By.cssSelector("a[href='#Welcome']"));
    private Element globalSettingTab = new Element(By.cssSelector("li[class='mn-setting']"));
    private Element addPageButton = new Element(By.xpath("//a[@class='add' and text()='Add Page']"));
    private Element okButton = new Element(By.id("OK"));
    private Element cancelButton = new Element(By.id("Cancel"));
    private Element pageNameText = new Element(By.id("name"));
    private Element addPageDialog = new Element(By.id("div_popup"));
    private Element createdTabs = new Element(By.xpath("//div[@id='main-menu']/div/ul/li [not (@class='mn-setting' or (@class='mn-panels'))]/a[not (text()='Overview' or text()='Execution\u00A0Dashboard')]"));
    private Element pageTab = new Element("//li[a[text()='%s']]/following-sibling::li/a[text()='%s']");
    private Element addPageDialogCombobox = new Element("//td[text()='%s']/following-sibling::td/select/option[text()='%s']");


    @Step("Verify login successfully")
    @Override
    public boolean isNavigated() {
        return logoutButton.exists();
    }

    @Step("Logout the account")
    @Override
    public void logout() {
        DriverUtils.stalenessOf(accountTab);
        accountTab.hover();
        logoutButton.waitForVisible();
        logoutButton.click();
    }

    @Step("Open add page dialog")
    @Override
    public void openAddPageDialog() {
        DriverUtils.stalenessOf(globalSettingTab);
        globalSettingTab.hover();
        DriverUtils.stalenessOf(addPageButton);
        addPageButton.click();
    }

    @Step("Enter page name")
    @Override
    public void enterPageName(String value) {
        DriverUtils.stalenessOf(pageNameText);
        pageNameText.enter(value);
    }

    @Step("Click OK to create new page")
    @Override
    public void clickOKButton() {
        DriverUtils.stalenessOf(okButton);
        okButton.click();
    }

    @Step("Click Cancel to close new page dialog")
    @Override
    public void clickCancelButton() {
        DriverUtils.stalenessOf(cancelButton);
        cancelButton.click();
    }

    @Step("Choose an option in dropdown list")
    @Override
    public void chooseComboboxOption(String comboBoxName, String args) {
        addPageDialogCombobox.set(comboBoxName, args);
        addPageDialogCombobox.click();
        addPageDialogCombobox.waitForVisible();
    }

    @Step("Create a new page")
    @Override
    public void createNewPage(String value) {
        openAddPageDialog();
        enterPageName(value);
        clickOKButton();
        pageTab.set(Navigation.OVERVIEW.value(), value.replace(" ", "\u00A0"));
        pageTab.waitForVisible();
    }

    @Step("Verify click Add Page button")
    @Override
    public boolean isAddPageDialogOpened() {
        globalSettingTab.hover();
        return addPageButton.isDisplayed();
    }

    @Step("Verify tab is beside")
    @Override
    public boolean isBesideTab(String tab1, String tab2) {
        tab1 = tab1.replace(" ", "\u00A0");
        tab2 = tab2.replace(" ", "\u00A0");
        pageTab.set(tab1, tab2);
        return pageTab.exists();
    }

    public List<String> getPageIds() {
        if (createdTabs.elements().size() != 0) {
            List<WebElement> tabList = createdTabs.elements();
            List<String> tabIds = new ArrayList<>();
            for (WebElement tab:tabList) {
                String[] temp = tab.getAttribute("href").split("/");
                String id = temp[temp.length-1].split("\\.")[0];
                tabIds.add(id);
            }
            return tabIds;
        }
        return null;
    }
}
