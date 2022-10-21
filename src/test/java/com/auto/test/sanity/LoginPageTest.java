package com.auto.test.sanity;

import com.auto.data.enums.Navigation;
import com.auto.model.User;
import com.auto.model.UserModel;
import com.auto.page.IHomePage;
import com.auto.page.ILoginPage;
import com.auto.page.PageFactory;
import com.auto.test.BrowserTestBase;
import com.auto.utils.Assertion;
import com.auto.utils.Constants;
import com.logigear.statics.Selaium;
import io.qameta.allure.Allure;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginPageTest extends BrowserTestBase {
    private UserModel user, invalidUser;
    private ILoginPage loginPage;
    private IHomePage homePage;


    @Test(description = "Able to login specific repository successfully via Dashboard login page with correct credentials")
    public void DA_LOGIN_TC001() {
        loginPage.login(user);
        Assertion.assertTrue(homePage.isNavigatedToHomePage(), "Login unsuccessfully and can not navigate to Home Page");

        homePage.logout();
        Assertion.assertTrue(loginPage.isLoginButtonDisplayed(), "User has logged in to the system");
    }

    @Test(description = "Fail to login specific repository successfully via Dashboard login page with incorrect credentials")
    public void DA_LOGIN_TC002() {
        loginPage.login(invalidUser);

        Assertion.assertEquals(loginPage.getAlertMessage(), "Username or password is invalid", "Something wrong happens");
    }

    @BeforeClass
    public void before() {
        loginPage = PageFactory.getLoginPage();
        homePage = PageFactory.getHomePage();
        user = User.instance().getTAUser(Constants.USERNAME, Constants.PASSWORD);
        invalidUser = User.instance().getTAUser(Constants.INVALID_USERNAME, Constants.INVALID_PASSWORD);
    }

    @AfterClass
    public void after() {
        Selaium.closeWebDriver();
    }
}
