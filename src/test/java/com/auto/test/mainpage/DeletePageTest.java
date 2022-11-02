package com.auto.test.mainpage;

import com.auto.data.enums.MenuItem;
import com.auto.model.Page;
import com.auto.model.User;
import com.auto.page.IMainPage;
import com.auto.page.IHomePage;
import com.auto.page.ILoginPage;
import com.auto.page.PageFactory;
import com.auto.test.BrowserTestBase;
import com.auto.utils.*;
import com.logigear.statics.Selaium;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DeletePageTest extends BrowserTestBase {

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


    @Test(description = "Remove any main parent page except 'Overview' page successfully and " +
            "the order of pages stays persistent as long as there is not children page under it")
    public void DA_MP_TC_017() {
        Page page = new Page(FakerUtils.name());
        mainPage.createNewPage(page);

        Page childPage = new Page (FakerUtils.name(), page);
        mainPage.createNewPage(childPage);

        homePage.moveToPageAndClickDelete(page);
        Assertion.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("confirm.delete"),"Verify confirm message to delete page");
        DriverUtils.acceptAlert();
        Assertion.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("block.delete", page.getName()),"Verify warning message when deleting page has child page(s)");
        DriverUtils.acceptAlert();

        homePage.moveToPageAndClickDelete(childPage);
        Assertion.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("confirm.delete"),"Verify confirm message to delete child page");
        DriverUtils.acceptAlert();

        homePage.deletePage(page);
    }

    @Test(description = "Able to add additional sibling pages to the parent page successfully")
    public void DA_MP_TC018() {
        Page page = new Page(FakerUtils.name());
        mainPage.createNewPage(page);

        Page childPage = new Page(FakerUtils.name(), page);
        mainPage.createNewPage(childPage);

        Page childPage2 = new Page(FakerUtils.name(), page);
        mainPage.createNewPage(childPage2);
        Assertion.assertTrue(homePage.pageExists(childPage2),"Verify the second child page is added successfully");

        homePage.deletePage(childPage2);
        homePage.deletePage(childPage);
        homePage.deletePage(page);
    }

    @Test(description = "Able to add additional sibling page levels to the parent page successfully.")
    public void DA_MP_TC019() {
        Page page = new Page(FakerUtils.name(), Page.overviewPage());
        mainPage.createNewPage(page);
        Assertion.assertTrue(homePage.pageExists(page), "Verify Overview is parent page of current added page");

        homePage.deletePage(page);
    }

    @Test(description = "Able to delete sibling page as long as that page has not children page under it")
    public void DA_MP_TC020() {
        Page page1 = new Page(FakerUtils.name(), Page.overviewPage());
        mainPage.createNewPage(page1);

        Page page2 = new Page(FakerUtils.name(), page1);
        mainPage.createNewPage(page2);

        homePage.deletePage(page1);
        Assertion.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("block.delete", page1.getName()),"Verify warning message when deleting page has child page(s)");
        DriverUtils.acceptAlert();

        homePage.deletePage(page2);
        Assertion.asserFalse(homePage.pageExists(page2),"Verify the child page is deleted successfully");

        homePage.deletePage(page1);
    }
}
