package com.auto.page;


public interface IHomePage extends IGeneralPage {

    boolean isNavigated();

    void logout();

    void createNewPage(String value);

    void openAddPageDialog();

    boolean isAddPageDialogOpened();

    boolean isBesideTab(String tab1, String tab2);

    void enterPageName(String value);

    void clickOKButton();

    void chooseComboboxOption(String value, String args);
}
