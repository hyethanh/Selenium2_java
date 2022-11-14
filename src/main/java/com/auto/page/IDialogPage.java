package com.auto.page;

import com.auto.data.enums.ChartType;
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

    void waitToCreatePanelDialogClose();

    void editExistedPage(Page page);

    boolean isPanelSettingDisplayed(String value);

    boolean comboboxOptionsSortedAlphabetically(String comboboxName);

    void enterPanelInformation(Panel panel);

    void clickCreateNewPanelButton();

    boolean chartTypeComoboxOptionsIsFullyListed();

    boolean isComboboxEnabled(String comboboxName);

    boolean isCaptionTextBoxEnabled();
}
