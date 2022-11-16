package com.auto.page.imp.browser;

import com.auto.data.enums.ChartType;
import com.auto.data.enums.MenuItem;
import com.auto.data.enums.Combobox;
import com.auto.element.Element;
import com.auto.model.Page;
import com.auto.model.Panel;
import com.auto.page.IHomePage;
import com.auto.page.IDialogPage;
import com.auto.page.IPanelPage;
import com.auto.utils.StringUtils;
import com.google.common.collect.Ordering;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DialogPage implements IDialogPage {

    private IHomePage homePage = new HomePage() ;
    private IPanelPage panelPage = new PanelPage();
    private Element okButton = new Element(By.id("OK"));
    private Element cancelButton = new Element(By.id("Cancel"));
    private Element pageNameText = new Element(By.id("name"));
    private Element dialogCombobox = new Element("//td[text()=\"%s\"]/following-sibling::td/select");
    private Element addPageDialogComboboxOption = new Element(By.xpath("//select[@id='parent']/option"));
    private Element panelComboboxOption = new Element("//td[text()='%s']/following-sibling::td/select//option[not (text()='Select a field...' )]");
    private Element addPageDialogComboboxOptionWithText = new Element("//select[@id='parent']/option[text()=\"%s\"]");
    private Element panelComboboxOptionWithText = new Element("//td[text()='%s']/following-sibling::td/select//option[not (text()='Select a field...' ) and text()=\"%s\"]");
    private Element panelDisplayedName = new Element(By.id("txtDisplayName"));
    private Element panelChartTitleTextBox = new Element(By.id("txtChartTitle"));
    private Element addNewPanelDialog = new Element(By.xpath("//div[@id='div_panelPopup']"));
    private Element panelSettingForm = new Element("//td[text()='Display Name *']//ancestor::table[@id='infoSettings']//label[text()=' %s']");
    private Element createPanelButton = new Element(By.xpath("//span[text()='Create new panel']"));
    private Element captionTextBox = new Element(By.id("txtValueYAxis"));
//    private Element checkboxButton = new Element("//label[text()=' %s\u00A0\u00A0']/input");
    private Element checkboxButton = new Element("//label[contains(text(),' %s')]/input");

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
        panelComboboxOption.set(comboBoxName);
        panelComboboxOption.waitForVisible();

        dialogCombobox.select(option);
        panelComboboxOptionWithText.set(comboBoxName, StringUtils.replaceSpaceCharWithNBSP(option));
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
        homePage.moveToPanelItemPage(MenuItem.PANELS.value());
        panelPage.clickLinkButton(MenuItem.ADD_NEW.value());
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

    @Step("Enter page information")
    protected void enterPageInformationPage(Page page) {
        enterPageName(page.getName());
        if (!page.getDisplayAfter().isEmpty()) {
            chooseComboboxOption(Combobox.DISPLAY_AFTER.value(), page.getDisplayAfter());
        }
        if (page.getParent() != null) {
            chooseComboboxOption(Combobox.PARENT_PAGE.value(), page.getParent().getName());
        }
        if (page.getColumn() != 2) {
            chooseComboboxOption(Combobox.COLUMNS.value(), Integer.toString(page.getColumn()));
        }
    }

    @Step("Enter panel information")
    public void enterPanelInformation(Panel panel) {
        enterPanelName(panel.getName());
        if (panel.getChartSeries() != null) {
            chooseComboBoxPanelPage(Combobox.SERIES.value(), panel.getChartSeries().value());
        }
        if (!panel.getChartTitle().isEmpty()) {
            panelChartTitleTextBox.enter(panel.getChartTitle());
        }
        if (panel.getChartType() != null) {
            chooseComboBoxPanelPage(Combobox.CHART_TYPE.value(), panel.getChartType().value());
        }
        if (panel.getDataProfile() != null) {
            chooseComboBoxPanelPage(Combobox.DATA_PROFILE.value(), panel.getDataProfile().value());
        }
    }

    @Step("Open Add New Panel dialog's combobox")
    public void clickAddNewPanelDialogComBoBox(String value) {
        dialogCombobox.set(value);
        dialogCombobox.click();
        panelComboboxOption.set(value);
        panelComboboxOption.waitForVisible();
    }

    @Step("Click create new panel button from choose panel dialog")
    public void clickCreateNewPanelButton() {
        createPanelButton.click();
    }

    @Step("Wait to close add new panel dialog close")
    public void waitToCreatePanelDialogClose() {
        addNewPanelDialog.waitForInvisible();
    }

    @Step("Verify Panel Setting Form displays above Display Name")
    public boolean isPanelSettingDisplayed(String value) {
        panelSettingForm.set(value);
        return panelSettingForm.isDisplayed();
    }

    @Step("Verify list is sorted alphabetically")
    public boolean comboboxOptionsSortedAlphabetically(String comboboxName) {
        panelComboboxOption.set(comboboxName);
        return Ordering.natural().isOrdered(panelComboboxOption.elements().stream().
                map(WebElement::getText).collect(Collectors.toList()));
    }

    @Step("Verify combobox lists full options")
    public boolean chartTypeComoboxOptionsIsFullyListed() {
        panelComboboxOption.set(Combobox.CHART_TYPE.value());
        List<String> list = Arrays.stream(ChartType.values()).map(ChartType::value).collect(Collectors.toList());
        return list.containsAll(panelComboboxOption.elements().stream().map(WebElement::getText).collect(Collectors.toList())) && panelComboboxOption.elements().stream().map(WebElement::getText).collect(Collectors.toList()).containsAll(list);
    }

    @Step("Verify Combobox in Chart Settings is enabled or not")
    public boolean isComboboxEnabled(String comboboxName) {
        dialogCombobox.set(comboboxName);
        return dialogCombobox.isEnabled();
    }
    @Step("Verify Caption text box in Chart Settings is enabled or not")
    public boolean isCaptionTextBoxEnabled() {
        return captionTextBox.isEnabled();
    }

    @Step("Verify checkbox is enabled or not")
    public boolean isCheckboxEnabled(String value) {
        checkboxButton.set(value);
        return checkboxButton.isEnabled();
    }
}
