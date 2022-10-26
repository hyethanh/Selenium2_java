package com.auto.test.sanity;

import com.auto.model.User;
import com.auto.model.UserModel;
import com.auto.page.IHomePage;
import com.auto.page.ILoginPage;
import com.auto.page.PageFactory;
import com.auto.test.BrowserTestBase;
import com.auto.utils.Assertion;
import com.logigear.statics.Selaium;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginPageTest extends BrowserTestBase {
    private UserModel user;
    private ILoginPage loginPage;
    private IHomePage homePage;


    @Test(description = "Able to login specific repository successfully via Dashboard login page with correct credentials")
    public void DA_LOGIN_TC001() {
        loginPage.enterUserAccount(user);
        loginPage.clickLoginButton();
        Assertion.assertTrue(homePage.isNavigated(), "Login unsuccessful");

        homePage.logout();
        Assertion.assertTrue(loginPage.isLoginButtonDisplayed(), "User has logged in to the system");
    }

    @BeforeClass
    public void before() {
        loginPage = PageFactory.getLoginPage();
        homePage = PageFactory.getHomePage();
        user = User.instance().getUser();
    }

    @AfterClass
    public void after() {
    }
}
