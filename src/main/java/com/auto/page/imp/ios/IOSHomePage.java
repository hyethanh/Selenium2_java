package com.auto.page.imp.ios;

import com.auto.page.IHomePage;


public class IOSHomePage extends IOSGeneralPage implements IHomePage {

    @Override
    public boolean isNavigatedToHomePage() {
        return false;
    }

    @Override
    public void logout() {

    }
}
