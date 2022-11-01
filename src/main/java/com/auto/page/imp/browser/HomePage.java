package com.auto.page.imp.browser;

import com.auto.data.enums.MenuItem;
import com.auto.element.Element;
import com.auto.model.Page;
import com.auto.page.IMainPage;
import com.auto.page.IHomePage;
import com.auto.utils.StringUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class HomePage implements IHomePage {

    private IMainPage mainPage;

    private Element logoutButton = new Element(By.xpath("//a[text()='Logout']"));
    private Element globalSettingTab = new Element(By.cssSelector("li[class='mn-setting']"));
    private Element mainPageButton = new Element(By.xpath("//a[@class='add' and text()='Add Page']"));
    private Element deleteButton = new Element(By.xpath("//a[@class='delete' and text()='Delete']"));
//    private Element okButton = new Element(By.id("OK"));
//    private Element cancelButton = new Element(By.id("Cancel"));
//    private Element pageNameText = new Element(By.id("name"));
    private Element createdTabs = new Element(By.xpath("//div[@id='main-menu']/div/ul/li [not (@class='mn-setting' or (@class='mn-panels'))]/a[not (text()='Overview' or text()='Execution\u00A0Dashboard')]"));
    private Element pageTabRelativePosition = new Element("//li[a[text()='%s']]/following-sibling::li/a[text()=\"%s\"]");
    private Element pageTab = new Element("//li[a[text()=\"%s\"]]");
    private Element childPageTab = new Element("//li[a[text()=\"%s\"]]/ul/descendant::a[text()=\"%s\"]");
//    private Element mainPageDialogCombobox = new Element("//td[text()=\"%s\"']/following-sibling::td/select");
//    private Element mainPageDialogComboboxOption = new Element(By.xpath("//select[@id='parent']/option"));
//    private Element mainPageDialogComboboxOptionWithText = new Element("//select[@id='parent']/option[text()=\"%s\"]");


    @Step("Go to page")
    public void moveToPage(Page page) {
        if (page.getParent() == null) {
            pageTab.set(StringUtils.replaceSpaceCharWithNBSP(page.getName()));
            pageTab.hover();
            return;
        }
        moveToPage(page.getParent());
        pageTab.set(StringUtils.replaceSpaceCharWithNBSP(page.getName()));
        pageTab.hover();
    }

    @Step("Verify login successfully")
    @Override
    public boolean isNavigated() {
        return logoutButton.exists();
    }

    @Step("Logout the account")
    @Override
    public void logout() {
        moveToPage(new Page(MenuItem.ADMINISTRATOR.value()));
        logoutButton.click();
    }

    @Step("Open add page dialog")
    @Override
    public void openAddPageDialog() {
        globalSettingTab.hover();
        mainPageButton.click();
    }

    @Step("Verify click Add Page button")
    public boolean ismainPageDialogOpened() {
        globalSettingTab.hover();
        return mainPageButton.isDisplayed();
    }

    @Step("Verify page is beside")
    public boolean isBesidePage(Page pageBefore, Page pageAfter) {
        String pageNameBefore = StringUtils.replaceSpaceCharWithNBSP(pageBefore.getName());
        String pageNameAfter = StringUtils.replaceSpaceCharWithNBSP(pageAfter.getName());

        pageTabRelativePosition.set(pageNameBefore, pageNameAfter);
        pageTabRelativePosition.waitForVisible();

        return pageTabRelativePosition.exists() && pageTabRelativePosition.isDisplayed();
    }

    @Step("Delete page")
    public void deletePage(Page page) {
        moveToPage(page);

        globalSettingTab.hover();
        deleteButton.click();
    }

    public boolean childPageExists(Page page) {
        childPageTab.set(page.getParent().getName().replace(" ", "\u00A0"), page.getName().replace(" ", "\u00A0"));
        return childPageTab.exists();
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
