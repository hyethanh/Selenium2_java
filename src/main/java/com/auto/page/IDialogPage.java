package com.auto.page;

import com.auto.data.enums.Combobox;
import com.auto.data.enums.DataLabel;
import com.auto.data.enums.Folder;
import com.auto.model.Page;
import com.auto.model.Panel;

public interface IDialogPage {
    void enterPageName(String value);

    void clickOKButton();

    void clickCancelButton();

    void chooseComboboxOption(String value, String args);

    void chooseComboBoxPanelPage(String comboBoxName, String option);

    void clickAddNewPanelDialogComBoBox(String value);

    void createNewPage(Page page);

    void createNewPanel(Panel panel);

    void waitForPanelDialogClose();

    void waitForPanelDialogOpen();

    void editExistedPage(Page page);

    void enterPageInformation(Page page);

    boolean isPanelSettingDisplayed(String value);

    boolean comboboxOptionsSortedAlphabetically(String comboboxName);

    void enterPanelInformation(Panel panel);

    boolean chartTypeComboboxOptionsAreFullyListed();

    boolean isComboboxEnabled(String comboboxName);

    boolean isCaptionTextBoxEnabled();

    boolean isCheckboxEnabled(String value);

    boolean isPanelUnchanged(Panel panel);

    void clickRadioButton(String value);

    void clickLabelOptionButton(DataLabel label);

    void chooseChartTypeCombobox(Panel panel);

    void chooseLabelOption(Panel panel);

    void clickLinkText(String value);

    boolean comboboxOptionsAreFullyListed(Combobox comboboxName, String ...values);

    void enterPanelHeight(String value);

    void enterFolderLink(String value);

    void clickPanelConfigurationOKButton();

    void clickPanelConfigurationCancelButton();

    void clickOpenFolderIcon();

    void openCreatePanelDialogFromHomePage();

    void createNewPanelWithoutConfiguration(Panel panel);

    void chooseTypePanel(Panel panel);

    void editExistedPanel(Panel panel);

    void clickLegendOptionButton(Panel panel);

    boolean isComboboxDisplayedValueCorrect(Combobox combobox, String value);
}
