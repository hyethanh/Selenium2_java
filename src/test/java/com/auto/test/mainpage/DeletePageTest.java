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

public class DeletePageTest extends BrowserTestBase {

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


    @Test(description = "Remove any main parent page except 'Overview' page successfully and " +
            "the order of pages stays persistent as long as there is not children page under it")
    public void DA_MP_TC_017() {
        Page page = new Page();
        Page secondPage = new Page();
        secondPage.setParent(page);

        dialogPage.createNewPage(page);
        dialogPage.createNewPage(secondPage);

        homePage.moveToPageAndClickDelete(page);
        softAssert.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("confirm.delete"),"Verify confirm message to delete page");
        DriverUtils.acceptAlert();
        softAssert.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("block.delete", page.getName()),"Verify warning message when deleting page has child page(s)");
        DriverUtils.acceptAlert();

        homePage.moveToPageAndClickDelete(secondPage);
        softAssert.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("confirm.delete"),"Verify confirm message to delete child page");
        DriverUtils.acceptAlert();
        softAssert.assertFalse(homePage.pageExists(secondPage));
        homePage.deletePage(page);

        softAssert.assertAll();
    }

    @Test(description = "Able to delete sibling page as long as that page has not children page under it")
    public void DA_MP_TC020() {
        Page firstPage = new Page();
        Page secondPage = new Page();
        firstPage.setParent(Page.overviewPage());
        secondPage.setParent(firstPage);

        dialogPage.createNewPage(firstPage);
        dialogPage.createNewPage(secondPage);
        homePage.moveToPageAndClickDelete(firstPage);
        softAssert.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("confirm.delete"),"Verify confirm message to delete page");
        DriverUtils.acceptAlert();
        softAssert.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("block.delete", firstPage.getName()),"Verify warning message when deleting page has child page(s)");
        DriverUtils.acceptAlert();
        homePage.deletePage(secondPage);
        softAssert.assertFalse(homePage.pageExists(secondPage),"Verify the child page is deleted successfully");

        softAssert.assertAll();
    }
}
