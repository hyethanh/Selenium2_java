package com.auto.page.imp.browser;

import com.auto.data.enums.PageCombobox;
import com.auto.element.Element;
import com.auto.model.Page;
import com.auto.page.IHomePage;
import com.auto.page.IDialogPage;
import com.auto.utils.Constants;
import com.auto.utils.StringUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class DialogPage implements IDialogPage {

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
        clickOKButton();
        okButton.waitForInvisible();
    }

    @Step("Edit an existed page")
    public void editExistedPage(Page page) {
        enterPageInformationPage(page);
        clickOKButton();
        okButton.waitForInvisible();
    }

    public void chooseDisplayAfterCombobox(Page page) {
        if (!page.getDisplayAfter().isEmpty()) {
            chooseComboboxOption(PageCombobox.DISPLAY_AFTER.value(), page.getDisplayAfter());
        }
    }

    public void chooseParentPageCombobox(Page page) {
        if (page.getParent() != null) {
            chooseComboboxOption(PageCombobox.PARENT_PAGE.value(), page.getParent().getName());
        }
    }

    public void chooseColumnCombobox(Page page) {
        if (page.getColumn() != Constants.DEFAULT_PAGE_COLUMN) {
            chooseComboboxOption(PageCombobox.COLUMNS.value(), Integer.toString(page.getColumn()));
        }
    }


    @Step("Enter page information")
    public void enterPageInformationPage(Page page) {
        enterPageName(page.getName());
        chooseDisplayAfterCombobox(page);
        chooseParentPageCombobox(page);
        chooseColumnCombobox(page);
    }
}
