package com.auto.page.imp.browser;

import com.auto.data.enums.MenuItem;
import com.auto.data.enums.PageCombobox;
import com.auto.page.IHomePage;
import com.auto.utils.DriverUtils;
import com.auto.utils.StringUtils;
import com.logigear.element.Element;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends GeneralPage implements IHomePage {

    private Element logoutButton = new Element(By.xpath("//a[text()='Logout']"));
    private Element globalSettingTab = new Element(By.cssSelector("li[class='mn-setting']"));
    private Element addPageButton = new Element(By.xpath("//a[@class='add' and text()='Add Page']"));
    private Element deleteButton = new Element(By.xpath("//a[@class='delete' and text()='Delete']"));
    private Element okButton = new Element(By.id("OK"));
    private Element cancelButton = new Element(By.id("Cancel"));
    private Element pageNameText = new Element(By.id("name"));
    private Element createdTabs = new Element(By.xpath("//div[@id='main-menu']/div/ul/li [not (@class='mn-setting' or (@class='mn-panels'))]/a[not (text()='Overview' or text()='Execution\u00A0Dashboard')]"));
   private Element pageTabRelativePosition = new Element("//li[a[text()='%s']]/following-sibling::li/a[text()='%s']");
    private Element pageTab = new Element("//li[a[text()='%s']]");
    private Element childPageTab = new Element("//li[a[text()='%s']]/ul/descendant::a");
    private Element addPageDialogCombobox = new Element("//td[text()='%s']/following-sibling::td/select");
    private Element addPageDialogComboboxOption = new Element(By.xpath("//select[@id='parent']/option"));
    private Element addPageDialogComboboxOptionWithText = new Element("//select[@id='parent']/option[text()='%s']");


    protected void moveToPage(String value) {
        pageTab.set(StringUtils.replaceSpaceCharWithNBSP(value));
        DriverUtils.stalenessOf(pageTab);
        pageTab.click();
    }

    @Step("Verify login successfully")
    @Override
    public boolean isNavigated() {
        return logoutButton.exists();
    }

    @Step("Logout the account")
    @Override
    public void logout() {
        moveToPage(MenuItem.ADMINISTRATOR.value());
        DriverUtils.stalenessOf(logoutButton);
        logoutButton.click();
    }

    @Step("Open add page dialog")
    @Override
    public void openAddPageDialog() {
        //Code works
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
        okButton.waitForInvisible();
    }

    @Step("Click Cancel to close new page dialog")
    @Override
    public void clickCancelButton() {
        DriverUtils.stalenessOf(cancelButton);
        cancelButton.click();
    }

    @Step("Choose an option in dropdown list")
    @Override
    public void chooseComboboxOption(String comboBoxName, String option) {
        addPageDialogCombobox.set(comboBoxName);
        addPageDialogCombobox.click();
        addPageDialogComboboxOption.waitForVisible();

        addPageDialogCombobox.select(option);
        addPageDialogComboboxOptionWithText.set(option.replace(" ", "\u00A0"));
        addPageDialogComboboxOptionWithText.waitForInvisible();
    }

    @Step("Create a new page")
    @Override
    public void createNewPage(String value) {
        openAddPageDialog();
        enterPageName(value);
        clickOKButton();
        moveToPage(value);
    }

    @Step("Verify click Add Page button")
    @Override
    public boolean isAddPageDialogOpened() {
        globalSettingTab.hover();
        return addPageButton.isDisplayed();
    }

    @Step("Verify tab is beside")
    @Override
    public boolean isBesideTab(String tabNameBefore, String tabNameAfter) {
        tabNameBefore = StringUtils.replaceSpaceCharWithNBSP(tabNameBefore);
        tabNameAfter = StringUtils.replaceSpaceCharWithNBSP(tabNameAfter);

        pageTabRelativePosition.set(tabNameBefore, tabNameAfter);
        pageTabRelativePosition.waitForVisible();

        return pageTabRelativePosition.exists() && pageTabRelativePosition.isDisplayed();
    }

    @Step("Create a child page")
    public void createChildPage(String parentPageName, String childPageName) {
        enterPageName(childPageName);
        chooseComboboxOption(PageCombobox.PARENT_PAGE.value(), parentPageName);
        clickOKButton();
    }

    @Step("Delete page")
    @Override
    public void deletePage(String value) {
        moveToPage(value);
        DriverUtils.stalenessOf(globalSettingTab);
        globalSettingTab.hover();
        deleteButton.click();
    }

    @Step("Delete page has a child")
    @Override
    public void deletePage(String parentPage, String childrenPage) {
        moveToPage(parentPage);
        DriverUtils.stalenessOf(pageTab);
        pageTab.hover();
        childPageTab.set(parentPage.replace(" ", "\u00A0"), childrenPage.replace(" ", "\u00A0"));
        childPageTab.click();

        DriverUtils.stalenessOf(globalSettingTab);
        globalSettingTab.hover();
        DriverUtils.stalenessOf(deleteButton);
        deleteButton.click();
    }

    public List<String> getPageIds() {
        if (createdTabs.elements().size() != 0) {
            List<WebElement> tabList = createdTabs.elements();
            List<String> tabIds = new ArrayList<>();
            for (WebElement tab : tabList) {
                String[] temp = tab.getAttribute("href").split("/");
                String id = temp[temp.length - 1].split("\\.")[0];
                tabIds.add(id);
            }
            return tabIds;
        }
        return null;
    }
}
