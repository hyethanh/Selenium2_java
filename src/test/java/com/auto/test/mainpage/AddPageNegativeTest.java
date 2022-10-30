package com.auto.test.mainpage;

import com.auto.model.Page;
import com.auto.model.User;
import com.auto.page.IHomePage;
import com.auto.page.ILoginPage;
import com.auto.page.PageFactory;
import com.auto.test.BrowserTestBase;
import com.auto.utils.Assertion;
import com.auto.utils.FakerUtils;
import com.auto.utils.UserUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AddPageNegativeTest extends BrowserTestBase {

    private User user;
    private ILoginPage loginPage;
    private IHomePage homePage;


    @Test(description = "Unable to open more than 1 'New Page' dialog")
    public void DA_MP_TC011() {
        homePage.openAddPageDialog();
        Assertion.asserFalse(homePage.isAddPageDialogOpened(), "One more than 1 new page dialog is open");
        homePage.clickCancelButton();
    }


    @BeforeClass(alwaysRun = true)
    public void before() {
        loginPage = PageFactory.getLoginPage();
        homePage = PageFactory.getHomePage();
        user = UserUtils.instance().getUser();

        loginPage.login(user);
    }
}
