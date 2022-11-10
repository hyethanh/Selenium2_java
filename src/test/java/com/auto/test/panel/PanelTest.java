package com.auto.test.panel;

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
import lombok.extern.java.Log;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

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
        Selaium.closeWebDriver();
    }

    @Test(description = "'Add New Panel' form is on focused all other control/form is disabled or locked.")
    public void DA_PANEL_TC028() {
        homePage.moveToPanelsPage();
        panelPage.clickAddNewLink();
        softAssert.assertFalse(homePage.isAddPageDialogOpened(), "Add Page Dialog is opened");
        homePage.logout();
        softAssert.assertFalse(loginPage.isLoginButtonDisplayed(), "User is at Login Page");
        softAssert.assertAll();
    }

    @Test(description = "Unable to create new panel when (*) required field is not filled")
    public void DA_PANEL_TC029() {
        Panel blankSeriesPanel = new Panel(FakerUtils.name(), null);
        Panel blankNamePanel = new Panel("");

        homePage.moveToPanelsPage();
        panelPage.clickAddNewLink();
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

        homePage.moveToPanelsPage();
        panelPage.clickAddNewLink();
        System.out.println(invalidNamePanel.getChartSeries().value());
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
}
