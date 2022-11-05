package com.auto.test.sanity;

import com.auto.utils.*;
import com.auto.model.User;
import com.auto.page.ILoginPage;
import com.auto.page.PageFactory;
import com.auto.test.BrowserTestBase;
import com.logigear.statics.Selaium;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

public class LoginPageNegativeTest extends BrowserTestBase {

    SoftAssert softAssert = new SoftAssert();

    private ILoginPage loginPage;

    @BeforeMethod
    public void before() {
        loginPage = PageFactory.getLoginPage();
    }

    @AfterMethod
    public void after() {
        Selaium.closeWebDriver();
    }


    @Test(description = "Fail to login specific repository successfully via Dashboard login page with incorrect credentials")
    public void DA_LOGIN_TC002() {
        User user = new User();
        user.username(UserUtils.getUsername("invalid.username"));
        user.password(UserUtils.getPassword("invalid.password"));

        loginPage.enterUserAccount(user);
        loginPage.clickLoginButton();
        softAssert.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("invalid.username.password"), "Can not get alert message");
        DriverUtils.acceptAlert();

        softAssert.assertAll();
    }

    @Test(description = "Fail to log in successfully via Dashboard login page with correct username and incorrect password")
    public void DA_LOGIN_TC003() {
        User user = new User();
        user.username(UserUtils.getUsername("valid.username"));
        user.password(UserUtils.getPassword("invalid.password"));

        loginPage.enterUserAccount(user);
        loginPage.clickLoginButton();
        softAssert.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("invalid.username.password"), "Can not get alert message");
        DriverUtils.acceptAlert();

        softAssert.assertAll();
    }

    @Test(description = "Unable to login when no input entered to Password and Username field")
    public void DA_LOGIN_TC010() {
        loginPage.clickLoginButton();
        Assertion.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("blank.username.password"), "Can not get alert message");
    }
}
