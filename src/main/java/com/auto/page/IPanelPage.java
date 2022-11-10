package com.auto.page;

import com.auto.model.Panel;

import java.util.List;

public interface IPanelPage {

    void clickAddNewLink();

    boolean isPanelDisplayedInTable(Panel panel);

    List<String> getPanelIds();
}
