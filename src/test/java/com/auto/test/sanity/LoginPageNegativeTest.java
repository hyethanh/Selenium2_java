package com.auto.test.sanity;

import com.auto.utils.*;
import com.auto.model.User;
import com.auto.page.ILoginPage;
import com.auto.page.PageFactory;
import com.auto.test.BrowserTestBase;
import org.testng.annotations.*;

public class LoginPageNegativeTest extends BrowserTestBase {

    private User invalidUser;
    private ILoginPage loginPage;

    @Test(description = "Fail to login specific repository successfully via Dashboard login page with incorrect credentials")
    public void DA_LOGIN_TC002() {
        invalidUser = UserUtils.instance().getInvalidUser(1);
        loginPage.enterUserAccount(invalidUser);
        loginPage.clickLoginButton();
        Assertion.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("invalid.username.password"), "Can not get alert message");
        DriverUtils.acceptAlert();
    }

    @Test(description = "Fail to log in successfully via Dashboard login page with correct username and incorrect password")
    public void DA_LOGIN_TC003() {
        invalidUser = UserUtils.instance().getInvalidUser(2);
        loginPage.enterUserAccount(invalidUser);
        loginPage.clickLoginButton();
        Assertion.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("invalid.username.password"), "Can not get alert message");
        DriverUtils.acceptAlert();
    }

    @Test(description = "Unable to login when no input entered to Password and Username field")
    public void DA_LOGIN_TC010() {
        loginPage.clickLoginButton();
        Assertion.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("blank.username.password"), "Can not get alert message");
    }


    @BeforeMethod
    public void before() {
        loginPage = PageFactory.getLoginPage();
        DriverUtils.refresh();
    }
}
