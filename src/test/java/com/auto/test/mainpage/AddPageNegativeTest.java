package com.auto.test.mainpage;

import com.auto.model.User;
import com.auto.page.IHomePage;
import com.auto.page.ILoginPage;
import com.auto.page.PageFactory;
import com.auto.test.BrowserTestBase;
import com.auto.utils.UserUtils;
import com.logigear.statics.Selaium;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class AddPageNegativeTest extends BrowserTestBase {

    SoftAssert softAssert = new SoftAssert();

    private User user;
    private ILoginPage loginPage;
    private IHomePage homePage;

    @BeforeClass(alwaysRun = true)
    public void before() {
        loginPage = PageFactory.getLoginPage();
        homePage = PageFactory.getHomePage();
        user = UserUtils.getUser();

        loginPage.login(user);
    }

    @AfterMethod(alwaysRun = true)
    public void after() {
        Selaium.closeWebDriver();
    }

    @Test(description = "Unable to open more than 1 'New Page' dialog")
    public void DA_MP_TC011() {
        homePage.openAddPageDialog();
        softAssert.assertFalse(homePage.ismainPageDialogOpened(), "One more than 1 new page dialog is open");
        softAssert.assertAll();
    }
}
