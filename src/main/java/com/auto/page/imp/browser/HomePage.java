package com.auto.page.imp.browser;

import com.auto.page.IHomePage;
import com.logigear.element.Element;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class HomePage extends GeneralPage implements IHomePage {

    private Element logoutButton = new Element(By.xpath("//a[text()='Logout']"));
    private Element accountTab = new Element(By.cssSelector("a[href='#Welcome']"));
    private Element globalSettingTab = new Element(By.cssSelector("li[class='mn-setting']"));
    private Element addPageButton = new Element(By.xpath("//a[@class='add' and text()='Add Page']"));
    private Element okButton = new Element(By.id("OK"));
    private Element pageNameText = new Element(By.id("name"));
    private Element addPageDialog = new Element(By.id("div_popup"));
    private Element mainTabs = new Element(By.xpath("//div[@id='main-menu']/div/ul/li"));
    private Element dialogCombobox = new Element(By.xpath("//td[text()='%s']/following-sibling::td/select"));


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

    @Step("Open add page dialog")
    @Override
    public void openDialog() {
        globalSettingTab.hover();
        addPageButton.click();
    }

    @Step("Enter page name")
    @Override
    public void enterPageName(String value) {
        addPageDialog.waitForVisible();
        pageNameText.enter(value);
    }

    @Step("Click OK to create new page")
    @Override
    public void clickOKButton() {
        okButton.click();
    }

    @Step("Choose an option in dropdown list")
    @Override
    public void chooseComboboxOption(String value) {
        dialogCombobox.set(value);
    }

    @Step("Create a new page")
    @Override
    public void createNewPage(String value) {
        globalSettingTab.waitForVisible();
        globalSettingTab.hover();
        addPageButton.click();
        addPageDialog.waitForVisible();
        pageNameText.enter(value);
        okButton.click();
    }

    @Step("Verify click Add Page button")
    @Override
    public boolean isAddPageDialogOpened() {
        globalSettingTab.hover();
        return addPageButton.isDisplayed();
    }

    @Step("Verify tab is beside")
    @Override
    public boolean isBeside(String first_value, String second_value) {
        List<String> tabNameList = getTabsName();
        int first_element_index = getIndexInList(tabNameList, first_value);
        int second_element_index = getIndexInList(tabNameList, second_value);
        if (second_element_index - first_element_index == 1) {
            return true;
        }
        return false;
    }

    public List<String> getTabsName() {
        mainTabs.waitForVisible();
        return mainTabs.elements().stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public int getIndexInList(List<String> list, String value) {
        for (String i : list) {
            if (i.equals(value)) {
                return list.indexOf(i);
            }
        }
        return -1;
    }
}
