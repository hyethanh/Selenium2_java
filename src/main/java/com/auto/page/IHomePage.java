package com.auto.page;


public interface IHomePage extends IGeneralPage {

    boolean isNavigatedToHomePage();

    void logout();

    void createNewPage(String value);

    void openDialog();
    boolean isAddPageDialogOpened();


    boolean isBeside(String first_value, String second_value);


}
