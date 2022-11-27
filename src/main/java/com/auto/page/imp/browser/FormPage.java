package com.auto.page.imp.browser;

import com.auto.data.enums.Folder;
import com.auto.data.enums.PanelType;
import com.auto.element.Element;
import com.auto.model.Panel;
import com.auto.page.IFormPage;
import com.auto.utils.StringUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FormPage implements IFormPage {
    private Element expandIcon = new Element("//a[text()=\" %s\"]/preceding-sibling::a");
    private Element actionsLinkText = new Element(By.xpath("//a[text()=' Actions']"));
    private Element folderSelectionOKButton = new Element(By.id("btnFolderSelectionOK"));
    private Element folderSelectionCancelButton = new Element(By.id("btnFolderSelectionCancel"));
    private Element panelFolderTree = new Element(By.xpath("//div[@class='general_display_block']//td[@class='general_white_space']/a[img[@class='panel_setting_treefolder'] and not (text()=' Actions')]"));
    private Element panelItems = new Element("//div[div[text()=\"%s\"]]//a");
    private Element createPanelButton = new Element(By.xpath("//span[text()='Create new panel']"));
    private Element hideChoosePanelsFormButton = new Element(By.id("btnHidePanel"));

    @Step("Click expand icon folder")
    public void clickExpandIconFolder(String folder) {
        expandIcon.set(folder);
        expandIcon.click();
    }

    @Step("Click Actions link text")
    public void clickActionsLinkText() {
        actionsLinkText.click();
    }

    public void chooseFolderInForm(Folder folder) {
        clickExpandIconFolder(folder.value());
        clickActionsLinkText();
        folderSelectionOKButton.click();
    }

    @Step("Close Choose Panel Folder Form")
    public void closeChooseFolderForm() {
        folderSelectionCancelButton.click();
    }

    @Step("Click create new panel button from choose panel dialog")
    public void clickCreateNewPanelButton() {
        createPanelButton.click();
    }

    @Step("Click Hide button to close Choose Panel form")
    public void clickHideChoosePanelsButton() {
        hideChoosePanelsFormButton.click();
        hideChoosePanelsFormButton.waitForInvisible();
    }

    @Step("Verify corresponding item type folders is correct in Select Folder form")
    public boolean isFolderCorrectInSelectFolderForm() {
        List<String> list = Arrays.stream(Folder.values()).map(Folder::value).collect(Collectors.toList());
        return list.containsAll(panelFolderTree.elements().stream().map(p -> p.getText().trim()).collect(Collectors.toList())) &&
                (panelFolderTree.elements().stream().map(p -> p.getText().trim()).collect(Collectors.toList())).containsAll(list);
    }

    @Step("Verify panel existed in Choose Panels form")
    public boolean isPanelInChoosePanelsForm(PanelType panelType, Panel panel) {
        panelItems.set(panelType.value() + "s");
        List<String> panelList = panelItems.elements().stream().map(p -> p.getText()).collect(Collectors.toList());
        return panelList.contains(panel.getName());
    }
}
