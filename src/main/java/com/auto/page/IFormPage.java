package com.auto.page;

import com.auto.data.enums.Folder;
import com.auto.data.enums.PanelType;
import com.auto.model.Panel;

public interface IFormPage {

    void clickExpandIconFolder(String folder);

    void clickActionsLinkText();

    void chooseFolderInForm(Folder folder);

    void closeChooseFolderForm();

    boolean isFolderCorrectInSelectFolderForm();

    boolean isPanelInChoosePanelsForm(PanelType panelType, Panel panel);

    void clickCreateNewPanelButton();

    void clickHideChoosePanelsButton();

    void choosePanelFromChoosePanelsForm(Panel panel);
}
