package com.auto.page;


import java.util.List;

public interface IHomePage extends IGeneralPage {

    boolean isNavigated();

    void logout();

    void createNewPage(String value);

    void openAddPageDialog();

    boolean isAddPageDialogOpened();

    boolean isBesideTab(String tab1, String tab2);

    void enterPageName(String value);

    void clickOKButton();
    void clickCancelButton();

    void chooseComboboxOption(String value, String args);

    List<String> getPageIds();
}
