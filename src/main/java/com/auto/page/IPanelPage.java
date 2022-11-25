package com.auto.page;

import com.auto.data.enums.LinkText;
import com.auto.model.Panel;

import java.util.List;

public interface IPanelPage {

    void enterProfileName(String value);

    void clickFinishButton();

    void clickLinkButton(LinkText value);

    boolean isPanelDisplayedInTable(Panel panel);

    void clickEditPanelButton();

    boolean isPanelCreated(Panel panel);

    List<String> getPanelIds();

    List<String> getPanelContentIds();

    boolean isFolderPathAsSelected(String link);
}
