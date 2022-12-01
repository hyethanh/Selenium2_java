package com.auto.page.imp.browser;

import com.auto.data.enums.*;
import com.auto.element.Element;
import com.auto.model.Page;
import com.auto.model.Panel;
import com.auto.page.IFormPage;
import com.auto.page.IHomePage;
import com.auto.page.IDialogPage;
import com.auto.page.IPanelPage;
import com.auto.utils.Constants;
import com.auto.utils.StringUtils;
import com.google.common.collect.Ordering;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DialogPage implements IDialogPage {

    private IHomePage homePage = new HomePage() ;
    private IPanelPage panelPage = new PanelPage();
    private IFormPage formPage = new FormPage();

    private Element okButton = new Element(By.id("OK"));
    private Element cancelButton = new Element(By.id("Cancel"));
    private Element pageNameText = new Element(By.id("name"));
    private Element dialogCombobox = new Element("//td[text()=\"%s\"]/following-sibling::td/select");
    private Element addPageDialogComboboxOption = new Element(By.xpath("//select[@id='parent']/option"));
    private Element panelComboboxOption = new Element("//td[text()='%s']/following-sibling::td/select//option[not (text()='Select a field...' or text()='Overview' or text()='Execution Dashboard')]");
    private Element addPageDialogComboboxOptionWithText = new Element("//select[@id='parent']/option[text()=\"%s\"]");
    private Element panelComboboxOptionWithText = new Element("//td[text()='%s']/following-sibling::td/select//option[not (text()='Select a field...' ) and text()=\"%s\"]");
    private Element panelDisplayedName = new Element(By.id("txtDisplayName"));
    private Element panelChartTitleTextBox = new Element(By.id("txtChartTitle"));
    private Element addNewPanelDialog = new Element(By.xpath("//div[@id='div_panelPopup']"));
    private Element panelSettingForm = new Element("//td[text()='Display Name *']//ancestor::table[@id='infoSettings']//label[text()=' %s']");
    private Element captionTextBox = new Element("//td[text()=\"%s\"]/following-sibling::td/input");
    private Element checkboxButton = new Element("//label[contains(text(),' %s')]/input");
    private Element showTitleCheckboxButton = new Element(By.id("chkShowTitle"));
    private Element displaySettingTab = new Element(By.xpath("//a[text()='Display Settings']"));
    private Element radioButton = new Element("//input[@value=\"%s\"]");
    private Element linkText = new Element("//a[text()=\"%s\"]");
    private Element heightTextBox = new Element(By.id("txtHeight"));
    private Element folderTextBox = new Element(By.id("txtFolder"));
    private Element panelConfigurationOKButton = new Element(By.xpath("//div[@id='div_panelConfigurationDlg']//input[@id='OK']"));
    private Element panelConfigurationCancelButton = new Element(By.xpath("//div[@id='div_panelConfigurationDlg']//input[@id='Cancel']"));
    private Element openFolderIcon = new Element(By.xpath("//a[@title='Open']"));
    private Element statisticFieldCombobox = new Element(By.xpath("//select[@id='cbbStatField']"));
    private Element statisticFieldComboboxOption = new Element(By.xpath("//select[@id='cbbStatField']/optgroup/option"));
    private Element statisticFieldComboboxOptionWithText = new Element("//select[@id='cbbStatField']/optgroup/option[contains(text(),\"%s\")]");

    @Step("Enter page name")
    public void enterPageName(String value) {
        pageNameText.enter(value);
    }

    @Step("Enter panel displayed name")
    public void enterPanelName(String value) {
        panelDisplayedName.clear();
        panelDisplayedName.enter(value);
    }

    @Step("Click OK button")
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
        if (dialogCombobox.isDisplayed() && dialogCombobox.exists()) {
            dialogCombobox.click();
            panelComboboxOption.set(comboBoxName);
            panelComboboxOption.waitForVisible();

            dialogCombobox.select(option);
            panelComboboxOptionWithText.set(comboBoxName, StringUtils.replaceSpaceCharWithNBSP(option));
        }
    }

    @Step("Create a new page")
    public void createNewPage(Page page) {
        homePage.openAddPageDialog();
        enterPageInformation(page);
        clickOKButton();
        okButton.waitForInvisible();
    }

    @Step("Create a new panel")
    public void createNewPanel(Panel panel) {
        homePage.moveToPanelItemPage(LinkText.PANELS);
        panelPage.clickLinkButton(LinkText.ADD_NEW);
        enterPanelInformation(panel);
        clickOKButton();
        okButton.waitForInvisible();
    }

    @Step("Edit an existed page")
    public void editExistedPage(Page page) {
        enterPageInformation(page);
        clickOKButton();
        okButton.waitForInvisible();
    }

    @Step("Edit an existed panel")
    public void editExistedPanel(Panel panel) {
        panelPage.clickActionButton(panel, LinkText.EDIT);
        enterPanelInformation(panel);
        clickOKButton();
        okButton.waitForInvisible();
    }

    @Step("Choose Display After")
    public void chooseDisplayAfterCombobox(Page page) {
        if (!page.getDisplayAfter().isEmpty()) {
            chooseComboboxOption(Combobox.DISPLAY_AFTER.value(), page.getDisplayAfter());
        }
    }

    @Step("Choose Parent Page")
    public void chooseParentPageCombobox(Page page) {
        if (page.getParent() != null) {
            chooseComboboxOption(Combobox.PARENT_PAGE.value(), page.getParent().getName());
        }
    }

    @Step("Choose Page's Column")
    public void chooseColumnCombobox(Page page) {
        if (page.getColumn() != Constants.DEFAULT_PAGE_COLUMN) {
            chooseComboboxOption(Combobox.COLUMNS.value(), Integer.toString(page.getColumn()));
        }
    }

    @Step("Choose Type Panel")
    public void chooseTypePanel(Panel panel) {
        if (panel.getType() != null) {
            checkboxButton.set(panel.getType().value());
            checkboxButton.click();
        }
    }

    @Step("Choose Chart Type")
    public void chooseChartTypeCombobox(ChartType chartType) {
        if (chartType != null) {
            chooseComboBoxPanelPage(Combobox.CHART_TYPE.value(), chartType.value());
        }
    }

    @Step("Choose Chart Series")
    public void chooseChartSeriesCombobox(ChartSeries chartSeries) {
        if (chartSeries != null) {
            chooseComboBoxPanelPage(Combobox.SERIES.value(), chartSeries.value());
        }
    }

    @Step("Enter title for chart panel")
    public void enterChartTitle(Panel panel) {
        if (!panel.getChartTitle().isEmpty()) {
            panelChartTitleTextBox.enter(panel.getChartTitle());
        }
    }

    @Step("Choose Data Profile")
    public void chooseDataProfileCombobox(Panel panel) {
        if (panel.getDataProfile() != null) {
            chooseComboBoxPanelPage(Combobox.DATA_PROFILE.value(), panel.getDataProfile().value());
        }
    }

    @Step("Choose Panel Style")
    public void choosePanelStyle(Panel panel) {
        if (panel.getStyle() != null) {
            clickRadioButton(panel.getStyle());
        }
    }

    @Step("Click Show Title button")
    public void clickShowTitleButton(Panel panel) {
        if (panel.isShowTitle() == true) {
            showTitleCheckboxButton.click();
        }
    }

    @Step("Choose Panel Label")
    public void chooseLabelOption(Panel panel) {
        if (panel.getDataLabel() != null) {
            clickLabelOptionButton(panel.getDataLabel());
        }
    }

    @Step("Enter page information")
    public void enterPageInformation(Page page) {
        enterPageName(page.getName());
        chooseDisplayAfterCombobox(page);
        chooseParentPageCombobox(page);
        chooseColumnCombobox(page);
    }

    @Step("Enter panel information")
    public void enterPanelInformation(Panel panel) {
        enterPanelName(panel.getName());
        enterChartTitle(panel);
        chooseTypePanel(panel);
        chooseChartTypeCombobox(panel.getChartType());
        chooseChartSeriesCombobox(panel.getChartSeries());
        chooseDataProfileCombobox(panel);
        chooseLabelOption(panel);
        choosePanelStyle(panel);
        clickShowTitleButton(panel);
        chooseStatisticFieldCombobox(panel);
        clickLegendOptionButton(panel);
    }

    @Step("Open Add New Panel dialog's combobox")
    public void clickAddNewPanelDialogComBoBox(String value) {
        dialogCombobox.set(value);
        dialogCombobox.click();
        panelComboboxOption.set(value);
        panelComboboxOption.waitForVisible();
    }


    @Step("Click style button")
    public void clickRadioButton(String value) {
        radioButton.set(value);
        if (radioButton.isDisplayed()) {
            radioButton.click();
        }
    }

    @Step("Wait to close add new panel dialog close")
    public void waitForPanelDialogClose() {
        addNewPanelDialog.waitForInvisible();
    }

    @Step("Wait for panel dialog open")
    public void waitForPanelDialogOpen() {
        addNewPanelDialog.waitForVisible();
    }

    @Step("Choose Label option")
    public void clickLabelOptionButton(DataLabel label) {
        checkboxButton.set(label.value());
        checkboxButton.click();
    }

    @Step("Choose Legends option")
    public void clickLegendOptionButton(Panel panel) {
        if (panel.getChartLegends() != null) {
            checkboxButton.set(panel.getChartLegends().value());
            if (checkboxButton.exists() && checkboxButton.isDisplayed()) {
                checkboxButton.click();
            }
        }
    }

    @Step("Click link text")
    public void clickLinkText(String value) {
        linkText.set(StringUtils.replaceSpaceCharWithNBSP(value));
        linkText.click();
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
    public boolean chartTypeComboboxOptionsAreFullyListed() {
        panelComboboxOption.set(Combobox.CHART_TYPE.value());
        List<String> list = Arrays.stream(ChartType.values()).map(ChartType::value).collect(Collectors.toList());
        return list.containsAll(panelComboboxOption.elements().stream().map(WebElement::getText).collect(Collectors.toList())) && panelComboboxOption.elements().stream().map(WebElement::getText).collect(Collectors.toList()).containsAll(list);
    }

    @Step("Verify Page combobox lists full options")
    public boolean comboboxOptionsAreFullyListed(Combobox comboboxName, String ...values) {
        panelComboboxOption.set(comboboxName.value());
        List<String> list = Arrays.stream(values).collect(Collectors.toList());
        return list.containsAll(panelComboboxOption.elements().stream().map(WebElement::getText).collect(Collectors.toList())) &&
                (panelComboboxOption.elements().stream().map(WebElement::getText).collect(Collectors.toList()).containsAll(list));
    }

    @Step("Verify Combobox in Chart Settings is enabled or not")
    public boolean isComboboxEnabled(String comboboxName) {
        dialogCombobox.set(comboboxName);
        return dialogCombobox.isEnabled();
    }
    @Step("Verify Caption text box in Chart Settings is enabled or not")
    public boolean isCaptionTextBoxEnabled() {
        captionTextBox.set(Combobox.CATEGORY.value());
        return captionTextBox.isEnabled();
    }

    @Step("Verify checkbox is enabled or not")
    public boolean isCheckboxEnabled(String value) {
        checkboxButton.set(value);
        return checkboxButton.isEnabled();
    }

    public String optionComboboxSelected(Combobox combobox) {
        dialogCombobox.set(combobox.value());
        Select select = new Select(dialogCombobox.element());
        return select.getFirstSelectedOption().getText();
    }

    @Step("Verify show title checkbox is not changed")
    public boolean isShowTitleUnchanged(Panel panel) {
       return showTitleCheckboxButton.isSelected() == panel.isShowTitle();
    }

    @Step("Verify Panel Chart Type is stabled")
    public boolean isChartTypeSettingUnchanged(Panel panel) {
        if (panel.getChartType() != null) {
            return optionComboboxSelected(Combobox.CHART_TYPE).equals(panel.getChartType().value());
        }
        return true;
    }

    @Step("Verify Panel Data Profile is stabled")
    public boolean isDataProfileSettingUnchanged(Panel panel) {
        if (panel.getDataProfile() != null) {
            return (optionComboboxSelected(Combobox.DATA_PROFILE).equals(panel.getDataProfile().value()));
        }
        return true;
    }

    @Step("Verify all settings stay unchanged")
    public boolean isPanelUnchanged(Panel panel) {
        displaySettingTab.click();
        return isShowTitleUnchanged(panel) && isChartTypeSettingUnchanged(panel) && isDataProfileSettingUnchanged(panel);
    }

    @Step("Enter height value in Panel Configuration")
    public void enterPanelHeight(String value) {
        heightTextBox.clear();
        heightTextBox.enter(value);
    }

    @Step("Enter panel's folder")
    public void enterFolderLink(String value) {
        folderTextBox.clear();
        folderTextBox.enter(value);
    }

    @Step("Click Panel Configuration OK Button")
    public void clickPanelConfigurationOKButton() {
        panelConfigurationOKButton.click();
    }

    @Step("Click Panel Configuration Cancel Button")
    public void clickPanelConfigurationCancelButton() {
        panelConfigurationCancelButton.click();
        panelConfigurationOKButton.waitForInvisible();
    }

    @Step("Click Open Folder icon")
    public void clickOpenFolderIcon() {
        openFolderIcon.click();
    }

    public void openCreatePanelDialogFromHomePage() {
        homePage.clickChoosePanelButton();
        formPage.clickCreateNewPanelButton();
        waitForPanelDialogOpen();
    }

    @Step("Create new panel without configuration")
    public void createNewPanelWithoutConfiguration(Panel panel) {
        enterPanelInformation(panel);
        clickOKButton();
        clickPanelConfigurationCancelButton();
    }

    @Step("Choose Statistic Field Combobox")
    public void chooseStatisticFieldCombobox(Panel panel) {
        if (statisticFieldCombobox.isDisplayed()) {
            statisticFieldCombobox.click();
            statisticFieldComboboxOption.waitForVisible();
            statisticFieldCombobox.select(panel.getStatisticField().value());
            statisticFieldComboboxOptionWithText.set(panel.getStatisticField().value());
        }
    }

    @Step("Verify displayed combobox value is correct")
    public boolean isDisplayedComboboxValueCorrect(Combobox combobox, String value) {
        dialogCombobox.set(combobox.value());
        dialogCombobox.click();
        return dialogCombobox.getAttribute("title").contains(value);
    }

    @Step("Enter caption for combobox")
    public void enterCaption(Combobox combobox, String value) {
        captionTextBox.set(combobox.value());
        captionTextBox.clear();
        captionTextBox.enter(value);
    }

    @Step("Verify displayed combobox caption is correct")
    public boolean isDisplayedComboboxCaptionValueCorrect(Combobox combobox, String value) {
        captionTextBox.set(combobox.value());
        captionTextBox.hover();
        return captionTextBox.getAttribute("value").equals(value);
    }
}
