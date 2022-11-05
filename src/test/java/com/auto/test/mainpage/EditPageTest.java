package com.auto.test.mainpage;

import com.auto.model.Page;
import com.auto.model.User;
import com.auto.page.IHomePage;
import com.auto.page.ILoginPage;
import com.auto.page.IMainPage;
import com.auto.page.PageFactory;
import com.auto.test.BrowserTestBase;
import com.auto.utils.DriverUtils;
import com.auto.utils.FakerUtils;
import com.auto.utils.MessageLoader;
import com.auto.utils.UserUtils;
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
        Page page2 = new Page(FakerUtils.name(), page1);

        mainPage.createNewPage(page1);
        mainPage.createNewPage(page2);
        homePage.moveToPageAndClickEdit(page1);
        page1.setName(FakerUtils.name());
        mainPage.editExistedPage(page1);
        homePage.moveToPageAndClickEdit(page2);
        page2.setName(FakerUtils.name());
        mainPage.editExistedPage(page2);
        softAssert.assertTrue(homePage.pageExists(page1), "Verify the old name of first page is replaced successfully");
        softAssert.assertTrue(homePage.pageExists(page2), "Verify the first page is updated successfully");
        homePage.deletePage(page2);
        homePage.deletePage(page1);
        softAssert.assertAll();
    }

    @Test(description = "Unable to duplicate the name of sibling page under the same parent page")
    public void DA_MP_TC022() {
        Page page1 = new Page(FakerUtils.name());
        Page page2 = new Page(FakerUtils.name(), page1);

        mainPage.createNewPage(page1);
        mainPage.createNewPage(page2);
        mainPage.createNewPage(page2);
        softAssert.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("duplicated.name", page2.getName()));
        DriverUtils.acceptAlert();
        mainPage.clickCancelButton();
        homePage.deletePage(page2);
        homePage.deletePage(page1);
        softAssert.assertAll();
    }
}
