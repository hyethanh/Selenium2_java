package com.auto.test.mainpage;

import com.auto.data.enums.MenuItem;
import com.auto.data.enums.PageCombobox;
import com.auto.model.Page;
import com.auto.model.User;
import com.auto.page.IHomePage;
import com.auto.page.ILoginPage;
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

    Page page = new Page(FakerUtils.title());
    Page firstPage = new Page(FakerUtils.title());
    Page secondPage = new Page(FakerUtils.title());


    @Test(description = "Able to add additional pages besides 'Overview' page successfully")
    public void DA_MP_TC012() {
        homePage.createNewPage(page.getName());
        Assertion.assertTrue(homePage.isBesideTab(MenuItem.OVERVIEW.value(), page.getName()),
                            "A new page does not beside Overview page");
    }

    @Test(description = "The newly added main parent page is positioned at the location specified as set with " +
                        "'Displayed After' field of 'New Page' form on the main page bar 'Parent Page' dropped down menu")
    public void DA_MP_TC013() {
        homePage.createNewPage(firstPage.getName());
        homePage.openAddPageDialog();
        homePage.enterPageName(secondPage.getName());
        homePage.chooseComboboxOption(PageCombobox.DISPLAY_AFTER.value(), firstPage.getName());
        homePage.clickOKButton();
        Assertion.assertTrue(homePage.isBesideTab(firstPage.getName(), secondPage.getName()), "");
    }


    @BeforeMethod(alwaysRun = true)
    public void before() {
        loginPage = PageFactory.getLoginPage();
        homePage = PageFactory.getHomePage();
        user = UserUtils.instance().getUserByIndex(0);

        loginPage.login(user);
    }

    @AfterMethod(alwaysRun = true)
    public void after() {
        DriverUtils.deletePage(homePage.getPageIds());
        Selaium.closeWebDriver();
    }
}
