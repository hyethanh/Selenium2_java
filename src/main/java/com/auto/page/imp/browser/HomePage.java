package com.auto.page.imp.browser;

import com.auto.data.enums.Navigation;
import com.auto.page.IHomePage;
import com.auto.utils.Constants;
import com.auto.utils.DriverUtils;
import com.logigear.element.Element;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
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
    private Element mainTab = new Element("//a[text()='%s']");
    private Element tab = new Element("//li[a[text()='%s']]/following-sibling::li/a[text()='%s']");
    private Element addPageDialogCombobox = new Element("//td[text()='%s']/following-sibling::td/select/option[text()='%s']");


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
    public void openAddPageDialog() {
        globalSettingTab.waitForVisible(Duration.ofSeconds(Constants.LONG_TIME));
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
        mainTab.set(value.replace(" ", "\u00A0"));
        mainTab.waitForVisible();
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
        tab.set(tab1, tab2);
        return tab.exists();
    }
}
