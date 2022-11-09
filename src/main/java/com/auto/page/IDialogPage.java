package com.auto.page;

import com.auto.model.Page;
import com.auto.model.Panel;

public interface IDialogPage {
    void enterPageName(String value);

    void clickOKButton();

    void clickCancelButton();

    void chooseComboboxOption(String value, String args);

    void createNewPage(Page page);

    void createNewPanel(Panel panel);

    void editExistedPage(Page page);

    void enterPanelInformation(Panel panel);
}
