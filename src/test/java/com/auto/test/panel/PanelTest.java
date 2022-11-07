package com.auto.test.panel;

import com.auto.model.Page;
import com.auto.model.Panel;
import com.auto.model.User;
import com.auto.page.*;
import com.auto.test.BrowserTestBase;
import com.auto.utils.FakerUtils;
import com.auto.utils.UserUtils;
import com.logigear.statics.Selaium;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

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
        Selaium.closeWebDriver();
    }

    @Test(description = "'Add New Panel' form is on focused all other control/form is disabled or locked.")
    public void DA_PANEL_TC027() {
        homePage.moveToPanelsPage();
        panelPage.clickAddNewLink();
        softAssert.assertFalse(homePage.isAddPageDialogOpened(), "Add Page Dialog is opened");
    }
}
