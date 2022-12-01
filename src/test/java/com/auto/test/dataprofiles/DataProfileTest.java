package com.auto.test.dataprofiles;

import com.auto.data.enums.LinkText;
import com.auto.model.User;
import com.auto.page.*;
import com.auto.test.BrowserTestBase;
import com.auto.utils.DriverUtils;
import com.auto.utils.UserUtils;
import com.logigear.statics.Selaium;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class DataProfileTest extends BrowserTestBase {

    SoftAssert softAssert = new SoftAssert();
    private User user;
    private ILoginPage loginPage;
    private IHomePage homePage;
    private IDataProfilePage dataProfilePage;

    @BeforeMethod(alwaysRun = true)
    public void before() {
        loginPage = PageFactory.getLoginPage();
        homePage = PageFactory.getHomePage();
        dataProfilePage = PageFactory.getDataProfilePage();
        user = UserUtils.getUser();

        loginPage.login(user);
    }

    @AfterMethod(alwaysRun = true)
    public void after() {
        Selaium.closeWebDriver();
    }

    @Test(description = "All Pre-set Data Profiles are populated correctly")
    public void DA_DP_TC065() {
        homePage.moveToPanelItemPage(LinkText.DATA_PROFILES);
        softAssert.assertTrue(dataProfilePage.areDataProfilesListedCorrectly(), "Check pre-set data profiles");

        softAssert.assertAll();
    }
}
