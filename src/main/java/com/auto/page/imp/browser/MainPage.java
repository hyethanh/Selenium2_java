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
    @Override
    public void clickOKButton() {
        okButton.click();
        okButton.waitForInvisible();
    }

    @Step("Click Cancel to close new page dialog")
    @Override
    public void clickCancelButton() {
        cancelButton.click();
    }

    @Step("Choose an option in dropdown list")
    @Override
    public void chooseComboboxOption(String comboBoxName, String option) {
        addPageDialogCombobox.set(comboBoxName);
        addPageDialogCombobox.click();
        addPageDialogComboboxOption.waitForVisible();

        addPageDialogCombobox.select(option);
        addPageDialogComboboxOptionWithText.set(StringUtils.replaceSpaceCharWithNBSP(option));
//        addPageDialogComboboxOptionWithText.waitForInvisible();
    }

    @Step("Create a new page")
    public void createNewPage(Page page) {
        homePage.openAddPageDialog();
        enterPageName(page.getName());
        chooseComboboxOption(PageCombobox.DISPLAY_AFTER.value(), page.getDisplayAfter());
        clickOKButton();
        homePage.moveToPage(page);
    }
}
