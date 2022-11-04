package com.auto.test.sanity;

import com.auto.utils.UserUtils;
import com.auto.model.User;
import com.auto.page.IHomePage;
import com.auto.page.ILoginPage;
import com.auto.page.PageFactory;
import com.auto.test.BrowserTestBase;
import com.logigear.statics.Selaium;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class LoginPageTest extends BrowserTestBase {

    SoftAssert softAssert = new SoftAssert();
    private ILoginPage loginPage;
    private IHomePage homePage;


    @BeforeClass
    public void before() {
        loginPage = PageFactory.getLoginPage();
        homePage = PageFactory.getHomePage();
    }

    @AfterMethod
    public void after() {
        Selaium.closeWebDriver();
    }

    @Test(description = "Able to login specific repository successfully via Dashboard login page with correct credentials")
    public void DA_LOGIN_TC001() {

        User user = new User();
        user.username(UserUtils.getUsername("valid.username"));
        user.password(UserUtils.getPassword("valid.password"));

        loginPage.enterUserAccount(user);
        loginPage.clickLoginButton();
        softAssert.assertTrue(homePage.isNavigated(), "Login unsuccessful");

        homePage.logout();
        softAssert.assertTrue(loginPage.isLoginButtonDisplayed(), "User is in login page");
        softAssert.assertAll();
    }
}
