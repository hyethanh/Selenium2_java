package com.auto.test.mainpage;

import com.auto.model.Page;
import com.auto.model.User;
import com.auto.page.IHomePage;
import com.auto.page.ILoginPage;
import com.auto.page.IDialogPage;
import com.auto.page.PageFactory;
import com.auto.test.BrowserTestBase;
import com.auto.utils.*;
import com.logigear.statics.Selaium;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class AddPageTest extends BrowserTestBase {

    SoftAssert softAssert = new SoftAssert();
    private User user;
    private ILoginPage loginPage;
    private IHomePage homePage;
    private IDialogPage dialogPage;

    Page page = new Page(FakerUtils.name());
    Page secondPage = new Page(FakerUtils.name(), page.getName());


    @BeforeMethod(alwaysRun = true)
    public void before() {
        loginPage = PageFactory.getLoginPage();
        homePage = PageFactory.getHomePage();
        dialogPage = PageFactory.getDialogPage();
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
        dialogPage.createNewPage(page);
        softAssert.assertTrue(homePage.isBesidePage(Page.overviewPage(), page),
                            "A new page does not beside Overview page");
    }

    @Test(description = "The newly added main parent page is positioned at the location specified as set with " +
                        "'Displayed After' field of 'New Page' form on the main page bar 'Parent Page' dropped down menu")
    public void DA_MP_TC013() {
        dialogPage.createNewPage(page);
        dialogPage.createNewPage(secondPage);
        softAssert.assertTrue(homePage.isBesidePage(page, secondPage), "Verify the second page is added after the first page");
    }

    @Test(description = "Able to add additional sibling pages to the parent page successfully")
    public void DA_MP_TC018() {
        Page page = new Page(FakerUtils.name());
        dialogPage.createNewPage(page);

        Page childPage = new Page(FakerUtils.name(), page);
        dialogPage.createNewPage(childPage);

        Page childPage2 = new Page(FakerUtils.name(), page);
        dialogPage.createNewPage(childPage2);
        softAssert.assertTrue(homePage.pageExists(childPage2),"Verify the second child page is added successfully");

        softAssert.assertAll();
    }

    @Test(description = "Able to add additional sibling page levels to the parent page successfully.")
    public void DA_MP_TC019() {
        Page page = new Page(FakerUtils.name(), Page.overviewPage());
        dialogPage.createNewPage(page);
        softAssert.assertTrue(homePage.pageExists(page), "Verify Overview is parent page of current added page");

        softAssert.assertAll();
    }
}
