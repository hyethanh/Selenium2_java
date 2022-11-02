package com.auto.test.sanity;

import com.auto.utils.UserUtils;
import com.auto.model.User;
import com.auto.page.IHomePage;
import com.auto.page.ILoginPage;
import com.auto.page.PageFactory;
import com.auto.test.BrowserTestBase;
import com.auto.utils.Assertion;
import com.logigear.statics.Selaium;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginPageTest extends BrowserTestBase {
    private User user;
    private ILoginPage loginPage;
    private IHomePage homePage;


    @BeforeClass
    public void before() {
        loginPage = PageFactory.getLoginPage();
        homePage = PageFactory.getHomePage();
        user = UserUtils.instance().getUser();
    }

    @Test(description = "Able to login specific repository successfully via Dashboard login page with correct credentials")
    public void DA_LOGIN_TC001() {
        loginPage.enterUserAccount(user);
        loginPage.clickLoginButton();
        Assertion.assertTrue(homePage.isNavigated(), "Login unsuccessful");

        homePage.logout();
        Assertion.assertTrue(loginPage.isLoginButtonDisplayed(), "Verify login to the system");
    }
}
