package com.auto.page.imp.browser;

import com.auto.element.Element;
import com.auto.model.User;
import com.auto.page.ILoginPage;
import com.auto.utils.DriverUtils;
import com.auto.utils.ExecutionContext;
//import com.logigear.element.Element;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class LoginPage implements ILoginPage {

    private final Element usernameTextBox = new Element(By.id("username"));

    private final Element passwordTextBox = new Element(By.id("password"));

    private final Element loginButton = new Element(By.className("btn-login"));

    @Step("Login to Website")
    public void enterUserAccount(User user) {
        usernameTextBox.enter(user.username());
        passwordTextBox.enter(user.password());
        ExecutionContext.setUser(user);
    }

    @Step("Click Login button")
    public void clickLoginButton() {
        loginButton.click();
    }

    @Step("Login to TA Dashboard")
    public void login(User user) {
        enterUserAccount(user);
        clickLoginButton();
    }


    @Step("Verify user has not logged in to Dashboard")
    public boolean isLoginButtonDisplayed() {
        loginButton.waitForVisible();
        return loginButton.isDisplayed();
    }


    public void input(Element element, String value) {
        element.clear();
        element.enter(value);
    }
}
