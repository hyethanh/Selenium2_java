package com.auto.test.mainpage;

import com.auto.data.enums.MenuItem;
import com.auto.data.enums.PageCombobox;
import com.auto.model.Page;
import com.auto.model.User;
import com.auto.page.IHomePage;
import com.auto.page.ILoginPage;
import com.auto.page.IMainPage;
import com.auto.page.PageFactory;
import com.auto.test.BrowserTestBase;
import com.auto.utils.*;
import com.logigear.statics.Selaium;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class AddPageTest extends BrowserTestBase {

    private User user;
    private ILoginPage loginPage;
    private IHomePage homePage;
    private IMainPage mainPage;

    Page page = new Page(FakerUtils.name());
    Page secondPage = new Page(FakerUtils.name(), page.getName());


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
        DriverUtils.deletePage(homePage.getPageIds());
        Selaium.closeWebDriver();
    }

    @Test(description = "Able to add additional pages besides 'Overview' page successfully")
    public void DA_MP_TC012() {
        mainPage.createNewPage(page);
        Assertion.assertTrue(homePage.isBesidePage(Page.overviewPage(), page),
                            "A new page does not beside Overview page");
    }

    @Test(description = "The newly added main parent page is positioned at the location specified as set with " +
                        "'Displayed After' field of 'New Page' form on the main page bar 'Parent Page' dropped down menu")
    public void DA_MP_TC013() {
        mainPage.createNewPage(page);
        mainPage.createNewPage(secondPage);
        Assertion.assertTrue(homePage.isBesidePage(page, secondPage), "Verify the second page is added after the first page");
    }
}
