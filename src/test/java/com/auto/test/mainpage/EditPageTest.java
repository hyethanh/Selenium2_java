package com.auto.test.mainpage;

import com.auto.model.Page;
import com.auto.model.User;
import com.auto.page.IDialogPage;
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

public class EditPageTest extends BrowserTestBase {

    SoftAssert softAssert = new SoftAssert();

    private User user;
    private ILoginPage loginPage;
    private IHomePage homePage;
    private IDialogPage dialogPage;


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

    @Test(description = "Able to edit the name of the page (Parent/Sibling) successfully")
    public void DA_MP_TC021() {
        Page page1 = new Page(FakerUtils.name(), Page.overviewPage());
        Page page2 = new Page(FakerUtils.name(), page1);
        dialogPage.createNewPage(page1);
        dialogPage.createNewPage(page2);

        homePage.moveToPageAndClickEdit(page1);
        page1.setName(FakerUtils.name());
        dialogPage.editExistedPage(page1);
        homePage.moveToPageAndClickEdit(page2);
        page2.setName(FakerUtils.name());
        dialogPage.editExistedPage(page2);
        softAssert.assertTrue(homePage.pageExists(page1), "Verify the old name of first page is replaced successfully");
        softAssert.assertTrue(homePage.pageExists(page2), "Verify the second page is updated successfully");

        softAssert.assertAll();
    }

    @Test(description = "Unable to duplicate the name of sibling page under the same parent page")
    public void DA_MP_TC022() {
        Page page1 = new Page(FakerUtils.name());
        Page page2 = new Page(FakerUtils.name(), page1);

        dialogPage.createNewPage(page1);
        dialogPage.createNewPage(page2);
        dialogPage.createNewPage(page2);
        softAssert.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("duplicated.name", page2.getName()));
        DriverUtils.acceptAlert();
        dialogPage.clickCancelButton();

        softAssert.assertAll();
    }

    @Test(description = "Able to edit the parent page of the sibling page successfully")
    public void DA_MP_TC023() {
        Page page1 = new Page(FakerUtils.name(), Page.overviewPage());
        Page page2 = new Page(FakerUtils.name(), page1);

        dialogPage.createNewPage(page1);
        dialogPage.createNewPage(page2);
        homePage.moveToPageAndClickEdit(page1);
        page1.setName(FakerUtils.name());
        dialogPage.editExistedPage(page1);
        softAssert.assertTrue(homePage.pageExists(page1), "Verify the old name of parent page has the sibling page is replaced successfully");

        softAssert.assertAll();
    }

    @Test(description = "Verify that breadcrumb navigation is correct")
    public void DA_MP_TC024() {
        Page page1 = new Page(FakerUtils.name(), Page.overviewPage());
        Page page2 = new Page(FakerUtils.name(), page1);

        dialogPage.createNewPage(page1);
        dialogPage.createNewPage(page2);
        homePage.moveToPage(page1);
        softAssert.assertEquals(DriverUtils.getCurrentPageTitle(), String.format(Constants.PAGE_TITLE_FORMAT,page1.getName()), "The first child page is navigated");
        homePage.moveToPage(page2);
        softAssert.assertEquals(DriverUtils.getCurrentPageTitle(), String.format(Constants.PAGE_TITLE_FORMAT,page2.getName()), "The second child page is navigated");

        softAssert.assertAll();
    }

    @Test(description = "Page listing is correct when user edit 'Display After'  field of a specific page")
    public void DA_MP_TC025() {
        Page page1 = new Page(FakerUtils.name());
        Page page2 = new Page(FakerUtils.name());

        dialogPage.createNewPage(page1);
        dialogPage.createNewPage(page2);
        page2.setDisplayAfter(Page.overviewPage().getName());
        homePage.moveToPageAndClickEdit(page2);
        softAssert.assertTrue(homePage.isBesidePage(Page.overviewPage(), page2), "Verify the second page is displayed after Overview page");

        softAssert.assertAll();
    }

    @Test(description = "Page column is correct when user edit 'Number of Columns' field of a specific page")
    public void DA_MP_TC026() {
        Page page = new Page(FakerUtils.name());
        dialogPage.createNewPage(page);
        page.setColumn(3);
        homePage.moveToPageAndClickEdit(page);
        dialogPage.editExistedPage(page);
        homePage.moveToPage(page);
        softAssert.assertEquals(homePage.getPageColumns(), page.getColumn(), "Verify page column is correct after updated");
        softAssert.assertAll();
    }
}
