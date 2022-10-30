package com.auto.test.mainpage;

import com.auto.model.Page;
import com.auto.model.User;
import com.auto.page.IHomePage;
import com.auto.page.ILoginPage;
import com.auto.page.PageFactory;
import com.auto.test.BrowserTestBase;
import com.auto.utils.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DeletePageTest extends BrowserTestBase {

    private User user;
    private ILoginPage loginPage;
    private IHomePage homePage;

    Page page = new Page(FakerUtils.title());
    Page childPage = new Page(FakerUtils.title());


    @Test(description = "remove any main parent page except 'Overview' page successfully and " +
            "the order of pages stays persistent as long as there is not children page under it")
    public void DA_MP_TC_017() {
        homePage.createNewPage(page.getTitle());
        homePage.openAddPageDialog();
        homePage.createChildPage(page.getTitle(), childPage.getTitle());
        homePage.deletePage(page.getTitle());
        Assertion.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("confirm.delete"),
                       "Verify confirm message to delete page");
        DriverUtils.acceptAlert();
        Assertion.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("block.delete", page.getTitle()),
                        "Verify warning message when deleting page has child page(s)");
        DriverUtils.acceptAlert();
        homePage.deletePage(page.getTitle(), childPage.getTitle());
        Assertion.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("confirm.delete"),
                        "Verify confirm message to delete child page");
        DriverUtils.acceptAlert();
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
        homePage.logout();
    }
}
