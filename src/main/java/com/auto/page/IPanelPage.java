package com.auto.page;

import com.auto.data.enums.LinkText;
import com.auto.model.Panel;

import java.util.List;

public interface IPanelPage {

    void enterProfileName(String value);

    void clickFinishButton();

    void clickLinkButton(LinkText value);

    boolean isPanelDisplayedInTable(Panel panel);

    void clickEditPanelButton(Panel panel);

    boolean isPanelCreated(Panel panel);

    List<String> getPanelIds();

    List<String> getPanelContentIds();

    boolean isFolderPathAsSelected(Panel panel, String link);

    void clickActionButton(Panel panel, LinkText action);

    void removePanel(Panel panel);
}
