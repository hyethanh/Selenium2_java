package com.auto.page;

import com.auto.data.enums.Combobox;
import com.auto.data.enums.DataLabel;
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

    void enterPageInformationPage(Page page);

    boolean isPanelSettingDisplayed(String value);

    boolean comboboxOptionsSortedAlphabetically(String comboboxName);

    void enterPanelInformation(Panel panel);

    void clickCreateNewPanelButton();

    boolean chartTypeComboboxOptionsAreFullyListed();

    boolean isComboboxEnabled(String comboboxName);

    boolean isCaptionTextBoxEnabled();

    boolean isCheckboxEnabled(String value);

    boolean isStayUnchanged(Panel panel);

    void clickRadioButton(String value);

    void clickLabelOptionButton(DataLabel label);

    void chooseChartTypeCombobox(Panel panel);

    void chooseLabelOption(Panel panel);

    void clickLinkText(String value);

    boolean comboboxOptionsAreFullyListed(Combobox comboboxName, String ...values);

    void enterPanelHeight(String value);

    void enterFolderLink(String value);
}
