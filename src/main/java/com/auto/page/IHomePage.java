package com.auto.page;


import com.auto.model.Page;

import java.util.List;

public interface IHomePage {

    boolean isNavigated();

    void logout();

//    void createNewPage(String value);
//
//    void createChildPage(String parent, String value);

    void openAddPageDialog();

    boolean ismainPageDialogOpened();

    boolean isBesidePage(Page page1, Page page2);

//    void enterPageName(String value);
//
//    void clickOKButton();
//
//    void clickCancelButton();
//
//    void chooseComboboxOption(String value, String args);

//    void deletePage(String value);
//
//    void deletePage(String parentPage, String childrenPage);

//    void moveToPage(String value);

//    boolean childPageExists(String parentPage, String childrenPage);

    boolean childPageExists(Page page);

    void deletePage(Page page);

    void moveToPage(Page page);

    List<String> getPageIds();
}
