package com.auto.page;

import com.auto.model.User;

public interface ILoginPage {

    void enterUserAccount(User user);

    void clickLoginButton();

    boolean isLoginButtonDisplayed();

//    String getAlertMessage();
//
//    void acceptAlert();

}
