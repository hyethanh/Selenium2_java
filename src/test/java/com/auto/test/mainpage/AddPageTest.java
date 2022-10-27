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
import com.auto.utils.DriverUtils;
import com.auto.utils.FakerUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class AddPageTest extends BrowserTestBase {

    private UserModel user;
    private ILoginPage loginPage;
    private IHomePage homePage;

    Page page = new Page(FakerUtils.title());
    Page firstPage = new Page(FakerUtils.title());
    Page secondPage = new Page(FakerUtils.title());


    @Test(description = "Unable to open more than 1 'New Page' dialog")
    public void DA_MP_TC011() {
        loginPage.enterUserAccount(user);
        loginPage.clickLoginButton();
        homePage.openAddPageDialog();
        Assertion.asserFalse(homePage.isAddPageDialogOpened(), "One more than 1 new page dialog is open");
        homePage.clickCancelButton();
    }

    @Test(description = "Able to add additional pages besides 'Overview' page successfully")
    public void DA_MP_TC012() {
        loginPage.enterUserAccount(user);
        loginPage.clickLoginButton();
        homePage.createNewPage(page.getTitle());
        Assertion.assertTrue(homePage.isBesideTab(Navigation.OVERVIEW.value(), page.getTitle()),
                            "A new page does not beside Overview page");
    }

    @Test(description = "The newly added main parent page is positioned at the location specified as set with 'Displayed After' field of 'New Page' form on the main page bar 'Parent Page' dropped down menu")
    public void DA_MP_TC013() {
        loginPage.enterUserAccount(user);
        loginPage.clickLoginButton();

        homePage.createNewPage(firstPage.getTitle());
        homePage.openAddPageDialog();
        homePage.enterPageName(secondPage.getTitle());
        homePage.chooseComboboxOption(Navigation.DISPLAYAFTER.value(), firstPage.getTitle());
        homePage.clickOKButton();
        Assertion.assertTrue(homePage.isBesideTab(firstPage.getTitle(), secondPage.getTitle()), "");
    }


    @BeforeClass(alwaysRun = true)
    public void before() {
        loginPage = PageFactory.getLoginPage();
        homePage = PageFactory.getHomePage();
        user = User.instance().getUser();
    }

    @AfterMethod(alwaysRun = true)
    public void after() {
        DriverUtils.deletePage(homePage.getPageIds());
        homePage.logout();
    }
}
