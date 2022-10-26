package com.auto.page;

import com.auto.model.UserModel;

public interface ILoginPage {

    void enterUserAccount(UserModel user);

    void clickLoginButton();

    boolean isLoginButtonDisplayed();

//    String getAlertMessage();
//
//    void acceptAlert();

}
