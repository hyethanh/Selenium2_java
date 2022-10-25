package com.auto.page;


public interface IHomePage extends IGeneralPage {

    boolean isNavigated();

    void logout();

    void createNewPage(String value);

    void openDialog();

    boolean isAddPageDialogOpened();

    boolean isBeside(String first_value, String second_value);

    void enterPageName(String value);

    void clickOKButton();

    void chooseComboboxOption(String value);
}
