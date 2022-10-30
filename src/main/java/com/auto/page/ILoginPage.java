package com.auto.page;

import com.auto.model.User;

public interface ILoginPage {

    void login(User user);

    void enterUserAccount(User user);

    void clickLoginButton();

    boolean isLoginButtonDisplayed();

}
