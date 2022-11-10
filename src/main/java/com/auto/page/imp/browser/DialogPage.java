package com.auto.page.imp.browser;

import com.auto.data.enums.PageCombobox;
import com.auto.element.Element;
import com.auto.model.Page;
import com.auto.model.Panel;
import com.auto.page.IHomePage;
import com.auto.page.IDialogPage;
import com.auto.page.IPanelPage;
import com.auto.utils.StringUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class DialogPage implements IDialogPage {

    private IHomePage homePage = new HomePage() ;
    private IPanelPage panelPage = new PanelPage();
    private Element okButton = new Element(By.id("OK"));
    private Element cancelButton = new Element(By.id("Cancel"));
    private Element pageNameText = new Element(By.id("name"));
    private Element dialogCombobox = new Element("//td[text()=\"%s\"]/following-sibling::td/select");
    private Element addPageDialogComboboxOption = new Element(By.xpath("//select[@id='parent']/option"));
    private Element addSeriesPanelComboboxOption = new Element(By.xpath("//select[@id='cbbSeriesField']//option[not (text()='Select a field...' )]"));
    private Element addPageDialogComboboxOptionWithText = new Element("//select[@id='parent']/option[text()=\"%s\"]");
    private Element addSeriesPanelComboboxOptionWithText = new Element("//select[@id='cbbSeriesField']//option[not (text()='Select a field...' ) and text()=\"%s\"]");
    private Element panelDisplayedName = new Element(By.id("txtDisplayName"));
    private Element addNewPanelDialog = new Element(By.xpath("//div[@id='div_panelPopup']"));

    @Step("Enter page name")
    public void enterPageName(String value) {
        pageNameText.enter(value);
    }

    @Step("Enter panel displayed name")
    public void enterPanelName(String value) {
        panelDisplayedName.clear();
        panelDisplayedName.enter(value);
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
        dialogCombobox.set(comboBoxName);
        dialogCombobox.click();
        addPageDialogComboboxOption.waitForVisible();

        dialogCombobox.select(option);
        addPageDialogComboboxOptionWithText.set(StringUtils.replaceSpaceCharWithNBSP(option));
    }

    @Step("Choose an option in Create Panel drop down")
    public void chooseComboBoxPanelPage(String comboBoxName, String option) {
        dialogCombobox.set(comboBoxName);
        dialogCombobox.click();
        addSeriesPanelComboboxOption.waitForVisible();

        dialogCombobox.select(option);
        addSeriesPanelComboboxOptionWithText.set(StringUtils.replaceSpaceCharWithNBSP(option));
    }

    @Step("Create a new page")
    public void createNewPage(Page page) {
        homePage.openAddPageDialog();
        enterPageInformationPage(page);
        clickOKButton();
        okButton.waitForInvisible();
    }

    @Step("Create a new panel")
    public void createNewPanel(Panel panel) {
        homePage.moveToPanelsPage();
        panelPage.clickAddNewLink();
        enterPanelInformation(panel);
        clickOKButton();
        okButton.waitForInvisible();
    }

    @Step("Edit an existed page")
    public void editExistedPage(Page page) {
        enterPageInformationPage(page);
        clickOKButton();
        okButton.waitForInvisible();
    }

    @Step("Wait to close add new panel dialog close")
    public void waitToCreatePanelDialogClose() {
        addNewPanelDialog.waitForInvisible();
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
        if (page.getColumn() != 2) {
            chooseComboboxOption(PageCombobox.COLUMNS.value(), Integer.toString(page.getColumn()));
        }
    }

    @Step("Enter panel information")
    public void enterPanelInformation(Panel panel) {
        enterPanelName(panel.getName());
        if (panel.getChartSeries() != null) {
            chooseComboBoxPanelPage(PageCombobox.SERIES.value(), panel.getChartSeries().value());
        }
    }
}
