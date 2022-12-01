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
        Page fistPage = new Page();
        Page secondPage = new Page();
        fistPage.setParent(Page.overviewPage());
        secondPage.setParent(fistPage);

        dialogPage.createNewPage(fistPage);
        homePage.moveToPage(Page.overviewPage());
        dialogPage.createNewPage(secondPage);
        homePage.moveToPageAndClickEdit(fistPage);
        fistPage.setName(FakerUtils.name());
        dialogPage.editExistedPage(fistPage);
        homePage.moveToPageAndClickEdit(secondPage);
        secondPage.setName(FakerUtils.name());
        dialogPage.editExistedPage(secondPage);
        softAssert.assertTrue(homePage.pageExists(fistPage), "Verify the old name of first page is replaced successfully");
        softAssert.assertTrue(homePage.pageExists(secondPage), "Verify the second page is updated successfully");

        softAssert.assertAll();
    }

    @Test(description = "Unable to duplicate the name of sibling page under the same parent page")
    public void DA_MP_TC022() {
        Page fistPage = new Page();
        Page secondPage = new Page();
        secondPage.setParent(fistPage);

        dialogPage.createNewPage(fistPage);
        dialogPage.createNewPage(secondPage);
        homePage.openAddPageDialog();
        dialogPage.enterPageInformation(secondPage);
        dialogPage.clickOKButton();
        softAssert.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("duplicated.name", secondPage.getName()));
        DriverUtils.acceptAlert();
        dialogPage.clickCancelButton();

        softAssert.assertAll();
    }

    @Test(description = "Able to edit the parent page of the sibling page successfully")
    public void DA_MP_TC023() {
        Page fistPage = new Page();
        Page secondPage = new Page();
        fistPage.setParent(Page.overviewPage());
        secondPage.setParent(fistPage);

        dialogPage.createNewPage(fistPage);
        dialogPage.createNewPage(secondPage);
        homePage.moveToPageAndClickEdit(fistPage);
        fistPage.setName(FakerUtils.name());
        dialogPage.editExistedPage(fistPage);
        softAssert.assertTrue(homePage.pageExists(fistPage), "Verify the old name of parent page has the sibling page is replaced successfully");

        softAssert.assertAll();
    }

    @Test(description = "Verify that breadcrumb navigation is correct")
    public void DA_MP_TC024() {
        Page fistPage = new Page();
        Page secondPage = new Page();
        fistPage.setParent(Page.overviewPage());
        secondPage.setParent(fistPage);

        dialogPage.createNewPage(fistPage);
        dialogPage.createNewPage(secondPage);
        homePage.moveToPage(fistPage);
        softAssert.assertEquals(DriverUtils.getCurrentPageTitle(), String.format(Constants.PAGE_TITLE_FORMAT,fistPage.getName()), "The first child page is navigated");
        homePage.moveToPage(secondPage);
        softAssert.assertEquals(DriverUtils.getCurrentPageTitle(), String.format(Constants.PAGE_TITLE_FORMAT,secondPage.getName()), "The second child page is navigated");

        softAssert.assertAll();
    }

    @Test(description = "Page listing is correct when user edit 'Display After'  field of a specific page")
    public void DA_MP_TC025() {
        Page fistPage = new Page();
        Page secondPage = new Page();

        dialogPage.createNewPage(fistPage);
        dialogPage.createNewPage(secondPage);
        secondPage.setDisplayAfter(Page.overviewPage().getName());
        homePage.moveToPageAndClickEdit(secondPage);
        softAssert.assertTrue(homePage.isBesidePage(Page.overviewPage(), secondPage), "Verify the second page is displayed after Overview page");

        softAssert.assertAll();
    }

    @Test(description = "Page column is correct when user edit 'Number of Columns' field of a specific page")
    public void DA_MP_TC026() {
        Page page = new Page();
        dialogPage.createNewPage(page);
        page.setColumn(3);
        homePage.moveToPageAndClickEdit(page);
        dialogPage.editExistedPage(page);
        homePage.moveToPage(page);
        softAssert.assertEquals(homePage.getPageColumns(), page.getColumn(), "Verify page column is correct after updated");
        softAssert.assertAll();
    }
}
