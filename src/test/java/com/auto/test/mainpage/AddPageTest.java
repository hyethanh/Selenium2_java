package com.auto.test.mainpage;

import com.auto.data.enums.Navigation;
import com.auto.model.Page;
import com.auto.model.User;
import com.auto.model.UserModel;
import com.auto.page.IHomePage;
import com.auto.page.ILoginPage;
import com.auto.page.PageFactory;
import com.auto.test.BrowserTestBase;
import com.auto.utils.Assertion;
import com.auto.utils.FakerUtils;

import com.logigear.statics.Selaium;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class AddPageTest extends BrowserTestBase {

    private UserModel user;
    private ILoginPage loginPage;
    private IHomePage homePage;

    String pageTitle = new Page().setTitle(FakerUtils.title());
    String firstPageTitle = new Page().setTitle(FakerUtils.title());
    String secondPageTitle = new Page().setTitle(FakerUtils.title());


    @Test(description = "Unable to open more than 1 'New Page' dialog")
    public void DA_MP_TC011() {
        loginPage.enterUserAccount(user);
        loginPage.clickLoginButton();
        homePage.openDialog();
        Assertion.asserFalse(homePage.isAddPageDialogOpened(), "One more than 1 new page dialog is open");
    }

    @Test(description = "Able to add additional pages besides 'Overview' page successfully")
    public void DA_MP_TC012() {
        loginPage.enterUserAccount(user);
        loginPage.clickLoginButton();
        homePage.createNewPage(pageTitle);
        Assertion.assertTrue(homePage.isBeside(Navigation.valueOf("OVERVIEW").value(), pageTitle),
                            "A new page does not beside Overview page");
    }

    @Test(description = "The newly added main parent page is positioned at the location specified as set with 'Displayed After' field of 'New Page' form on the main page bar 'Parent Page' dropped down menu")
    public void DA_MP_TC013() throws InterruptedException {
        loginPage.enterUserAccount(user);
        loginPage.clickLoginButton();

        homePage.openDialog();
        homePage.enterPageName(firstPageTitle);
        homePage.clickOKButton();

        homePage.openDialog();
        homePage.enterPageName(secondPageTitle);
        homePage.chooseComboboxOption("Display After");
        Thread.sleep(4000);
    }


    @BeforeClass
    public void before() {
        loginPage = PageFactory.getLoginPage();
        homePage = PageFactory.getHomePage();
        user = User.instance().getUser();
    }

    @AfterClass
    public void after() {
        Selaium.closeWebDriver();
    }
}
