package com.auto.page;

import com.auto.data.enums.Folder;

public interface IFormPage {

    void clickExpandIconFolder(String folder);

    void clickActionsLinkText();

    void chooseFolderInForm(Folder folder);

    void closeChooseFolderForm();

    boolean isFolderCorrectInSelectFolderForm();
}
