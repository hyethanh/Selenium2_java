package com.auto.page;


import com.auto.model.Page;

import java.util.List;

public interface IHomePage {

    boolean isNavigated();

    void logout();

    void openAddPageDialog();

    boolean isAddPageDialogOpened();

    boolean isBesidePage(Page page1, Page page2);

    boolean pageExists(Page page);

    void deletePage(Page page);

    void moveToPageAndClickDelete(Page page);

    void moveToPageAndClickEdit(Page page);

    void moveToPage(Page page);

    boolean isPageBreadcrumb(Page... pages);

    List<String> getPageIds();
}
