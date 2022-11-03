package com.auto.test.mainpage;

import com.auto.model.Page;
import com.auto.model.User;
import com.auto.page.IHomePage;
import com.auto.page.ILoginPage;
import com.auto.page.IMainPage;
import com.auto.page.PageFactory;
import com.auto.test.BrowserTestBase;
import com.auto.utils.Assertion;
import com.auto.utils.DriverUtils;
import com.auto.utils.FakerUtils;
import com.auto.utils.UserUtils;
import com.logigear.statics.Selaium;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EditPageTest extends BrowserTestBase {

    private User user;
    private ILoginPage loginPage;
    private IHomePage homePage;
    private IMainPage mainPage;


    @BeforeMethod(alwaysRun = true)
    public void before() {
        loginPage = PageFactory.getLoginPage();
        homePage = PageFactory.getHomePage();
        mainPage = PageFactory.getMainPage();
        user = UserUtils.getUser();

        loginPage.login(user);
    }

    @AfterMethod(alwaysRun = true)
    public void after() {
        Selaium.closeWebDriver();
    }

    @Test(description = "Able to edit the name of the page (Parent/Sibling) successfully")
    public void DA_MP_TC021() {
        Page page1 = new Page(FakerUtils.name(), Page.overviewPage());
        mainPage.createNewPage(page1);

        Page page2 = new Page(FakerUtils.name(), page1);
        System.out.println(page2.getName());
        mainPage.createNewPage(page2);

        Page page3 = new Page(FakerUtils.name());
        mainPage.editExistedPage(page1, page3);

        Assertion.asserFalse(homePage.pageExists(page1), "Verify the first page is updated successfully");
        Assertion.assertTrue(homePage.pageExists(page3), "Verify the first page is updated successfully");
    }
}
