package com.auto.page.imp.chrome;

import com.auto.model.UserModel;
import com.auto.page.ILoginPage;
import com.auto.utils.ExecutionContext;
import com.logigear.element.Element;
import com.logigear.statics.Selaium;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ChromeLoginPage implements ILoginPage {

    String alertMessage;

    private final Element usernameTextBox = new Element(By.id("username"));

    private final Element passwordTextBox = new Element(By.id("password"));

    private final Element loginButton = new Element(By.className("btn-login"));

    @Step("Login to Website")
    @Override
    public void enterUserAccount(UserModel user) {
        usernameTextBox.enter(user.getUsername());
        passwordTextBox.enter(user.getPassword());
        ExecutionContext.setUser(user);
    }

    @Step("Click Login button")
    @Override
    public void clickLoginButton() {
        loginButton.click();
    }


    @Step("Verify user has not logged in to Dashboard")
    @Override
    public boolean isLoginButtonDisplayed() {
        loginButton.waitForVisible();
        return loginButton.isDisplayed();
    }

    @Step("Get alert message")
    @Override
    public String getAlertMessage() {
        try {
            alertMessage = Selaium.driver().switchTo().alert().getText();
        } catch (Exception ex) {
            ex.getMessage();
        }
        return alertMessage;
    }

    @Step("Accept to close alert")
    @Override
    public void acceptAlert() {
        Selaium.driver().switchTo().alert().accept();
    }
}
