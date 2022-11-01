package com.auto.page;

import com.auto.model.Page;

public interface IMainPage {
    void enterPageName(String value);

    void clickOKButton();

    void clickCancelButton();

    void chooseComboboxOption(String value, String args);

    void createNewPage(Page page);
}
