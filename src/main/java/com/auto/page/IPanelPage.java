package com.auto.page;

import com.auto.model.Panel;

import java.util.List;

public interface IPanelPage {

    void enterProfileName(String value);

    void clickFinishButton();

    void clickLinkButton(String value);

    boolean isPanelDisplayedInTable(Panel panel);

    List<String> getPanelIds();
}
