package com.auto.test.sanity;

import com.auto.model.User;
import com.auto.model.UserModel;
import com.auto.page.ILoginPage;
import com.auto.page.PageFactory;
import com.auto.test.BrowserTestBase;
import com.auto.utils.Assertion;
import com.auto.utils.DriverUtils;
import com.auto.utils.MessageLoader;
import com.logigear.statics.Selaium;
import org.testng.annotations.*;

public class LoginPageNegativeTest extends BrowserTestBase {

    private UserModel invalidUser;
    private ILoginPage loginPage;

    @Test(description = "Fail to login specific repository successfully via Dashboard login page with incorrect credentials")
    public void DA_LOGIN_TC002() {
        invalidUser = User.instance().getInvalidUser(0);
        loginPage.enterUserAccount(invalidUser);
        loginPage.clickLoginButton();
        Assertion.assertEquals(loginPage.getAlertMessage(), MessageLoader.getMessage("invalid.username.password"), "Can not get alert message");
        loginPage.acceptAlert();
    }

    @Test(description = "Fail to log in successfully via Dashboard login page with correct username and incorrect password")
    public void DA_LOGIN_TC003() {
        invalidUser = User.instance().getInvalidUser(1);
        loginPage.enterUserAccount(invalidUser);
        loginPage.clickLoginButton();
        Assertion.assertEquals(loginPage.getAlertMessage(), MessageLoader.getMessage("invalid.username.password"), "Can not get alert message");
        loginPage.acceptAlert();
    }

    @Test(description = "Able to log in different repositories successfully after logging out current repository")
    public void DA_LOGIN_TC004() {
        invalidUser = User.instance().getInvalidUser(1);
        loginPage.enterUserAccount(invalidUser);
        loginPage.clickLoginButton();
        Assertion.assertEquals(loginPage.getAlertMessage(), MessageLoader.getMessage("invalid.username.password"), "Can not get alert message");
        loginPage.acceptAlert();
    }

    @Test(description = "Unable to login when no input entered to Password and Username field")
    public void DA_LOGIN_TC010() {
        loginPage.clickLoginButton();
        Assertion.assertEquals(loginPage.getAlertMessage(), MessageLoader.getMessage("blank.username.password"), "Can not get alert message");
    }


    @BeforeMethod
    public void before() {
        loginPage = PageFactory.getLoginPage();
        DriverUtils.refresh();
    }
}
