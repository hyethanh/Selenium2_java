package com.auto.page.imp.browser;

import com.auto.data.enums.PageCombobox;
import com.auto.element.Element;
import com.auto.model.Page;
import com.auto.page.IHomePage;
import com.auto.page.IMainPage;
import com.auto.utils.StringUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class MainPage implements IMainPage {

    private IHomePage homePage = new HomePage() ;
    private Element okButton = new Element(By.id("OK"));
    private Element cancelButton = new Element(By.id("Cancel"));
    private Element pageNameText = new Element(By.id("name"));
    private Element addPageDialogCombobox = new Element("//td[text()=\"%s\"]/following-sibling::td/select");
    private Element addPageDialogComboboxOption = new Element(By.xpath("//select[@id='parent']/option"));
    private Element addPageDialogComboboxOptionWithText = new Element("//select[@id='parent']/option[text()=\"%s\"]");


    @Step("Enter page name")
    public void enterPageName(String value) {
        pageNameText.enter(value);
    }

    @Step("Click OK to create new page")
    public void clickOKButton() {
        okButton.click();
        okButton.waitForInvisible();
    }

    @Step("Click Cancel to close new page dialog")
    public void clickCancelButton() {
        cancelButton.click();
    }

    @Step("Choose an option in dropdown list")
    public void chooseComboboxOption(String comboBoxName, String option) {
        addPageDialogCombobox.set(comboBoxName);
        addPageDialogCombobox.click();
        addPageDialogComboboxOption.waitForVisible();

        addPageDialogCombobox.select(option);
        addPageDialogComboboxOptionWithText.set(StringUtils.replaceSpaceCharWithNBSP(option));
    }

    @Step("Create a new page")
    public void createNewPage(Page page) {
        homePage.openAddPageDialog();
        enterPageInformationPage(page);
    }

    @Step("Edit an existed page")
    public void editExistedPage(Page page, Page newPage) {
        homePage.moveToPageAndClickEdit(page);
        enterPageInformationPage(newPage);
    }

    @Step("Enter page information")
    protected void enterPageInformationPage(Page page) {
        enterPageName(page.getName());
        if (!page.getDisplayAfter().isEmpty()) {
            chooseComboboxOption(PageCombobox.DISPLAY_AFTER.value(), page.getDisplayAfter());
        }
        if (page.getParent() != null) {
            chooseComboboxOption(PageCombobox.PARENT_PAGE.value(), page.getParent().getName());
        }
        clickOKButton();
    }
}
