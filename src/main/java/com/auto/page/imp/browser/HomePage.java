package com.auto.page.imp.browser;

import com.auto.data.enums.MenuItem;
import com.auto.element.Element;
import com.auto.model.Page;
import com.auto.page.IHomePage;
import com.auto.utils.DriverUtils;
import com.auto.utils.StringUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HomePage implements IHomePage {

    private Element logoutButton = new Element(By.xpath("//a[text()='Logout']"));
    private Element globalSettingTab = new Element(By.cssSelector("li[class='mn-setting']"));
    private Element menuItemButton = new Element("//a[@class='%s']");
    private Element createdTabs = new Element(By.xpath("//li[@class='mn-panels']/preceding-sibling::li//a[not(text()=\"Overview\" or text()=\"Execution\u00A0Dashboard\")]"));
    private Element pageTabRelativePosition = new Element("//li[a[text()=\"%s\"]]/following-sibling::li/a[text()=\"%s\"]");
    private Element pageTab = new Element("//li[a[text()=\"%s\"]]");
    private Element pageBreadcrumb = new Element("//li[contains(@class,'haschild')]/descendant::a");


    protected void hoverOnTab(Page page) {
        if (page.getParent() == null) {
            pageTab.set(StringUtils.replaceSpaceCharWithNBSP(page.getName()));
            pageTab.hover();
            return;
        }
        hoverOnTab(page.getParent());
        pageTab.set(StringUtils.replaceSpaceCharWithNBSP(page.getName()));
        pageTab.hover();
    }

    protected void clickMenuItemButton(String value) {
        globalSettingTab.hover();
        menuItemButton.set(value);
        menuItemButton.click();
    }

    @Step("Verify login successfully")
    @Override
    public boolean isNavigated() {
        return logoutButton.exists();
    }

    @Step("Logout the account")
    @Override
    public void logout() {
        hoverOnTab(new Page(MenuItem.ADMINISTRATOR.value()));
        logoutButton.click();
    }

    @Step("Open add page dialog")
    @Override
    public void openAddPageDialog() {
        clickMenuItemButton(MenuItem.ADD.value());
    }

    @Step("Verify click Add Page button")
    public boolean isAddPageDialogOpened() {
        globalSettingTab.hover();
        menuItemButton.set(MenuItem.ADD.value());
        return menuItemButton.isDisplayed();
    }

    @Step("Verify page is beside")
    public boolean isBesidePage(Page pageBefore, Page pageAfter) {
        String pageNameBefore = StringUtils.replaceSpaceCharWithNBSP(pageBefore.getName());
        String pageNameAfter = StringUtils.replaceSpaceCharWithNBSP(pageAfter.getName());

        pageTabRelativePosition.set(pageNameBefore, pageNameAfter);
        pageTabRelativePosition.waitForVisible();

        return pageTabRelativePosition.exists() && pageTabRelativePosition.isDisplayed();
    }

    @Step("Move To Page And Click Delete")
    public void moveToPageAndClickDelete(Page page) {
        moveToPage(page);
        clickMenuItemButton(MenuItem.DELETE.value());
    }

    @Step("Move To Page And Click Edit")
    public void moveToPageAndClickEdit(Page page) {
        moveToPage(page);
        clickMenuItemButton(MenuItem.EDIT.value());
    }

    @Step("Delete page")
    public void deletePage(Page page) {
        moveToPageAndClickDelete(page);
        DriverUtils.acceptAlert();
        pageTab.waitForInvisible();
    }

    @Step("Go to page")
    public void moveToPage(Page page) {
        hoverOnTab(page);
        pageTab.click();
    }

    @Step("Get breadcrumb's order")
    public boolean isPageBreadcrumb(Page... pages) {
        List<String> namePageBreadcrumb = getPageNameOfBreadcrumb();
        List<String> namePageList = new ArrayList<>();
        for (Page page : pages) {
            namePageList.add(page.getName());
        }
        for (int i = 0; i < namePageBreadcrumb.size(); i++) {
            if (!namePageBreadcrumb.get(i).equals(namePageList.get(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean pageExists(Page page) {
        if (page.getParent() != null) {
            hoverOnTab(page.getParent());
        }
        pageTab.set(StringUtils.replaceSpaceCharWithNBSP(page.getName()));
        return pageTab.isDisplayed() && pageTab.exists();
    }

    protected List<String> getPageNameOfBreadcrumb() {
        List<String> namePageList = new ArrayList<>();
        for (WebElement page : pageBreadcrumb.elements()) {
            namePageList.add(page.getText());
        }
        return namePageList;
    }

    public List<String> getPageIds() {
        List<String> list = createdTabs.elements().stream().map(p -> p.getAttribute("href").split("/"))
                .map(t -> t[t.length - 1].split("\\.")[0]).collect(Collectors.toList());
        Collections.reverse(list);
        return list;
    }
}
