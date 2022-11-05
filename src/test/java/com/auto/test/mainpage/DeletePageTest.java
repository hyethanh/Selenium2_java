package com.auto.test.mainpage;

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
import org.testng.asserts.SoftAssert;

public class DeletePageTest extends BrowserTestBase {

    SoftAssert softAssert = new SoftAssert();

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
        DriverUtils.deletePage(homePage.getPageIds());
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
        softAssert.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("confirm.delete"),"Verify confirm message to delete page");
        DriverUtils.acceptAlert();
        softAssert.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("block.delete", page.getName()),"Verify warning message when deleting page has child page(s)");
        DriverUtils.acceptAlert();

        homePage.moveToPageAndClickDelete(childPage);
        softAssert.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("confirm.delete"),"Verify confirm message to delete child page");
        DriverUtils.acceptAlert();
        softAssert.assertFalse(homePage.pageExists(childPage));

        softAssert.assertAll();
    }

    @Test(description = "Able to add additional sibling pages to the parent page successfully")
    public void DA_MP_TC018() {
        Page page = new Page(FakerUtils.name());
        mainPage.createNewPage(page);

        Page childPage = new Page(FakerUtils.name(), page);
        mainPage.createNewPage(childPage);

        Page childPage2 = new Page(FakerUtils.name(), page);
        mainPage.createNewPage(childPage2);
        softAssert.assertTrue(homePage.pageExists(childPage2),"Verify the second child page is added successfully");

        softAssert.assertAll();
    }

    @Test(description = "Able to add additional sibling page levels to the parent page successfully.")
    public void DA_MP_TC019() {
        Page page = new Page(FakerUtils.name(), Page.overviewPage());
        mainPage.createNewPage(page);
        softAssert.assertTrue(homePage.pageExists(page), "Verify Overview is parent page of current added page");

        softAssert.assertAll();
    }

    @Test(description = "Able to delete sibling page as long as that page has not children page under it")
    public void DA_MP_TC020() {
        Page page1 = new Page(FakerUtils.name(), Page.overviewPage());
        mainPage.createNewPage(page1);

        Page page2 = new Page(FakerUtils.name(), page1);
        mainPage.createNewPage(page2);

        homePage.deletePage(page1);
        softAssert.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("block.delete", page1.getName()),"Verify warning message when deleting page has child page(s)");
        DriverUtils.acceptAlert();

        homePage.deletePage(page2);
        softAssert.assertFalse(homePage.pageExists(page2),"Verify the child page is deleted successfully");

        softAssert.assertAll();
    }
}
