package com.auto.test.panel;

import com.auto.data.enums.*;
import com.auto.data.enums.MenuItem;
import com.auto.model.DataProfile;
import com.auto.model.Page;
import com.auto.model.Panel;
import com.auto.model.User;
import com.auto.page.*;
import com.auto.test.BrowserTestBase;
import com.auto.utils.DriverUtils;
import com.auto.utils.FakerUtils;
import com.auto.utils.MessageLoader;
import com.auto.utils.UserUtils;
import com.logigear.statics.Selaium;
import io.qameta.allure.Step;
import javafx.scene.layout.Pane;
import lombok.extern.java.Log;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.awt.*;
import java.util.logging.Logger;


public class PanelTest extends BrowserTestBase {

    SoftAssert softAssert = new SoftAssert();

    private User user;
    private ILoginPage loginPage;
    private IHomePage homePage;
    private IDialogPage dialogPage;
    private IPanelPage panelPage;

    @BeforeClass(alwaysRun = true)
    public void before() {
        loginPage = PageFactory.getLoginPage();
        homePage = PageFactory.getHomePage();
        dialogPage = PageFactory.getDialogPage();
        panelPage = PageFactory.getPanelPage();
        user = UserUtils.getUser();

        loginPage.login(user);
    }

    @AfterClass(alwaysRun = true)
    public void after() {
        DriverUtils.deletePanel(panelPage.getPanelIds());
        DriverUtils.deletePage(homePage.getPageIds());
        Selaium.driver().close();
    }

    @Test(description = "'Add New Panel' form is on focused all other control/form is disabled or locked.")
    public void DA_PANEL_TC028() {
        homePage.moveToPanelItemPage(MenuItem.PANELS.value());
        panelPage.clickLinkButton(MenuItem.ADD_NEW.value());
        softAssert.assertFalse(homePage.isAddPageDialogOpened(), "Add Page Dialog is opened");
        homePage.logout();
        softAssert.assertFalse(loginPage.isLoginButtonDisplayed(), "User is at Login Page");
        softAssert.assertAll();
    }

    @Test(description = "Unable to create new panel when (*) required field is not filled")
    public void DA_PANEL_TC029() {
        Panel blankSeriesPanel = new Panel(FakerUtils.name(), (ChartSeries) null);
        Panel blankNamePanel = new Panel("");

        homePage.moveToPanelItemPage(MenuItem.PANELS.value());
        panelPage.clickLinkButton(MenuItem.ADD_NEW.value());
        dialogPage.enterPanelInformation(blankSeriesPanel);
        dialogPage.clickOKButton();
        softAssert.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("blank.series"), "No alert for blank series displays");
        DriverUtils.acceptAlert();
        dialogPage.enterPanelInformation(blankNamePanel);
        dialogPage.clickOKButton();
        Logger.getLogger("Verify cannot create new panel with blank name");
        softAssert.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("blank.name.panel"), "No alert for blank displayed name appears");

        softAssert.assertAll();
    }

    @Test(description = "No special character except '@' character is allowed to be inputted into 'Display Name' field")
    public void DA_PANEL_TC030() {
        Panel invalidNamePanel = new Panel(FakerUtils.name()+"#$%");
        Panel validNamePanel = new Panel(FakerUtils.name()+"@");

        homePage.moveToPanelItemPage(MenuItem.PANELS.value());
        panelPage.clickLinkButton(MenuItem.ADD_NEW.value());
        dialogPage.enterPanelInformation(invalidNamePanel);
        dialogPage.clickOKButton();
        softAssert.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("invalid.name"),
                        "New panel created successfully with special character except @");
        DriverUtils.acceptAlert();

        dialogPage.enterPanelInformation(validNamePanel);
        dialogPage.clickOKButton();
        dialogPage.waitToCreatePanelDialogClose();
        softAssert.assertTrue(panelPage.isPanelDisplayedInTable(validNamePanel),
                              String.format("New created panel '%s' does not displayed in table", validNamePanel.getName()));

        softAssert.assertAll();
    }

    @Test(description = "Correct panel setting form is displayed with corresponding panel type selected")
    public void DA_PANEL_TC031() {
        homePage.moveToPanelItemPage(MenuItem.PANELS.value());
        panelPage.clickLinkButton(MenuItem.ADD_NEW.value());
        softAssert.assertTrue(dialogPage.isPanelSettingDisplayed(PanelType.CHART.value()), "Chart Type is not displayed above Display Name");
        softAssert.assertTrue(dialogPage.isPanelSettingDisplayed(PanelType.INDICATOR.value()), "Indicator Type is not displayed above Display Name");
        softAssert.assertTrue(dialogPage.isPanelSettingDisplayed(PanelType.REPORT.value()), "Report Type is not displayed above Display Name");
        softAssert.assertTrue(dialogPage.isPanelSettingDisplayed(PanelType.HEAT_MAP.value()), "Heat Map Type is not displayed above Display Name");

        softAssert.assertAll();
    }

    @Test(description = "User is not allowed to create panel with duplicated 'Display Name'")
    public void DA_PANEL_TC032() {
        Panel panel1 = new Panel(FakerUtils.name());
        Panel panel2 = new Panel(panel1.getName());

        dialogPage.createNewPanel(panel1);
        panelPage.clickLinkButton(MenuItem.ADD_NEW.value());
        dialogPage.enterPanelInformation(panel2);
        dialogPage.clickOKButton();
        softAssert.assertEquals(DriverUtils.getAlertMessage(),
                                MessageLoader.getMessage("duplicated.name", String.format(panel1.getName())),
                        "The second panel is created successfully");
        DriverUtils.acceptAlert();
        dialogPage.clickCancelButton();

        softAssert.assertAll();
    }

    @Test(description = "'Data Profile' listing of 'Add New Panel' and 'Edit Panel' control/form are in alphabetical order")
    public void DA_PANEL_TC033() {
        Panel panel = new Panel(FakerUtils.name());
        homePage.moveToPanelItemPage(MenuItem.PANELS.value());
        panelPage.clickLinkButton(MenuItem.ADD_NEW.value());
        dialogPage.clickAddNewPanelDialogComBoBox(Combobox.DATA_PROFILE.value());
        softAssert.assertTrue(dialogPage.comboboxOptionsSortedAlphabetically(Combobox.DATA_PROFILE.value()),
                      "Data Profile is not listing in alphabetical order when adding new panel");
        dialogPage.clickCancelButton();
        dialogPage.createNewPanel(panel);
        panelPage.clickLinkButton(MenuItem.EDIT.value());
        dialogPage.clickAddNewPanelDialogComBoBox(Combobox.DATA_PROFILE.value());
        softAssert.assertTrue(dialogPage.comboboxOptionsSortedAlphabetically(Combobox.DATA_PROFILE.value()),
                "Data Profile is not listing in alphabetical order when editing an existed panel");

        softAssert.assertAll();
    }

    @Test(description = "Newly created data profiles are populated correctly under the 'Data Profile' dropped down menu in  'Add New Panel' and 'Edit Panel' control/form")
    public void DA_PANEL_TC034() {
        DataProfile profile = new DataProfile(FakerUtils.name());
        Panel panel = new Panel(FakerUtils.name());

        homePage.moveToPanelItemPage(MenuItem.DATA_PROFILES.value());
        panelPage.clickLinkButton(MenuItem.ADD_NEW.value());
        panelPage.enterProfileName(profile.getName());
        panelPage.clickFinishButton();
        homePage.moveToPanelItemPage(MenuItem.PANELS.value());
        panelPage.clickLinkButton(MenuItem.ADD_NEW.value());
        dialogPage.clickAddNewPanelDialogComBoBox(Combobox.DATA_PROFILE.value());
        softAssert.assertTrue(dialogPage.comboboxOptionsSortedAlphabetically(Combobox.DATA_PROFILE.value()),
                "Data Profile is not listing in alphabetical order when adding new panel");
        dialogPage.clickCancelButton();
        dialogPage.createNewPanel(panel);
        panelPage.clickLinkButton(MenuItem.EDIT.value());
        dialogPage.clickAddNewPanelDialogComBoBox(Combobox.DATA_PROFILE.value());
        softAssert.assertTrue(dialogPage.comboboxOptionsSortedAlphabetically(Combobox.DATA_PROFILE.value()),
                "Data Profile is not listing in alphabetical order when editing an existed panel");

        softAssert.assertAll();
    }

    @Test(description = "No special character except '@' character is allowed to be inputted into 'Chart Title' field")
    public void DA_PANEL_TC035() {
        Panel invalidTitlePanel = new Panel(FakerUtils.name(), FakerUtils.title() + "#$%");
        Panel validPanel = new Panel(FakerUtils.name()+"@", FakerUtils.title()+"@");
        homePage.moveToPanelItemPage(MenuItem.PANELS.value());
        panelPage.clickLinkButton(MenuItem.ADD_NEW.value());
        dialogPage.enterPanelInformation(invalidTitlePanel);
        dialogPage.clickOKButton();
        softAssert.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("invalid.title"),
                String.format("New created panel '%s' does not displayed in table", invalidTitlePanel.getName()));
        DriverUtils.acceptAlert();
        dialogPage.clickCancelButton();
        dialogPage.createNewPanel(validPanel);
        softAssert.assertTrue(panelPage.isPanelDisplayedInTable(validPanel),
                String.format("New created panel '%s' does not displayed in table", validPanel.getName()));

        softAssert.assertAll();
    }

    @Test(description = "Chart types ( Pie, Single Bar, Stacked Bar, Group Bar, Line ) are listed correctly under 'Chart Type' dropped down menu.")
    public void DA_PANEL_TC036() {
        Page page = new Page(FakerUtils.name());
        dialogPage.createNewPage(page);
        homePage.clickChoosePanelButton();
        dialogPage.clickCreateNewPanelButton();
        softAssert.assertTrue(dialogPage.chartTypeComoboxOptionsIsFullyListed());

        softAssert.assertAll();
    }
}