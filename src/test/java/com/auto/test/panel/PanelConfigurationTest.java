package com.auto.test.panel;

import com.auto.data.enums.Charts;
import com.auto.data.enums.Combobox;
import com.auto.data.enums.Folder;
import com.auto.data.enums.MenuItem;
import com.auto.model.Page;
import com.auto.model.Panel;
import com.auto.model.User;
import com.auto.page.*;
import com.auto.test.BrowserTestBase;
import com.auto.utils.DriverUtils;
import com.auto.utils.MessageLoader;
import com.auto.utils.UserUtils;
import com.logigear.statics.Selaium;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Random;

public class PanelConfigurationTest extends BrowserTestBase {

    SoftAssert softAssert = new SoftAssert();

    private User user;
    private ILoginPage loginPage;
    private IHomePage homePage;
    private IDialogPage dialogPage;
    private IPanelPage panelPage;

    @BeforeClass(alwaysRun = true)
    public void before() {
        loginPage = PageFactory.getLoginPage();
        homePage = PageFactory.getHomePage();
        dialogPage = PageFactory.getDialogPage();
        panelPage = PageFactory.getPanelPage();
        user = UserUtils.getUser();

        loginPage.login(user);
    }

    @AfterClass(alwaysRun = true)
    public void after() {
        DriverUtils.deletePage(homePage.getPageIds());
        DriverUtils.deletePanelContent(panelPage.getPanelContentIds());
        homePage.moveToPanelItemPage(MenuItem.PANELS.value());
        DriverUtils.deletePanel(panelPage.getPanelIds());
        Selaium.driver().close();
    }

    @Test(description = "All pages are listed correctly under the Select page * dropped down menu of Panel Configuration form/ control")
    public void DA_PANEL_TC042() {
        Page page1 = new Page();
        Page page2 = new Page();
        Page page3 = new Page();

        dialogPage.createNewPage(page1);
        dialogPage.createNewPage(page2);
        dialogPage.createNewPage(page3);

        homePage.clickChoosePanelButton();
        dialogPage.clickLinkText(Charts.randomCharts().value());
        softAssert.assertTrue(dialogPage.comboboxOptionsAreFullyListed(Combobox.SELECT_PAGE, page1.getName(), page2.getName(), page3.getName()));
        softAssert.assertAll();
    }

    @Test(description = "Only integer number inputs from 300-800 are valid for Height * field ")
    public void DA_PANEL_TC043() {
        Page page = new Page();
        Random r = new Random();

        dialogPage.createNewPage(page);
        homePage.clickChoosePanelButton();
        dialogPage.clickLinkText(Charts.randomCharts().value());

        dialogPage.enterPanelHeight(String.valueOf(300 - r.nextInt(301)));
        dialogPage.clickOKButton();
        softAssert.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("invalid.height"));
        DriverUtils.acceptAlert();

        dialogPage.enterPanelHeight(String.valueOf(800 + r.nextInt()));
        dialogPage.clickOKButton();
        softAssert.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("invalid.height"));
        DriverUtils.acceptAlert();

        dialogPage.enterPanelHeight(String.valueOf(0 - r.nextInt()));
        dialogPage.clickOKButton();
        softAssert.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("invalid.height"));
        DriverUtils.acceptAlert();

        dialogPage.enterPanelHeight("abc");
        dialogPage.clickOKButton();
        softAssert.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("invalid.type.height"));
        DriverUtils.acceptAlert();

        softAssert.assertAll();
    }

    @Test(description = "Height * field is not allowed to be empty")
    public void DA_PANEL_TC044() {
        Page page = new Page();

        dialogPage.createNewPage(page);
        homePage.clickChoosePanelButton();
        dialogPage.clickLinkText(Charts.randomCharts().value());
        dialogPage.enterPanelHeight("");
        dialogPage.clickOKButton();
        softAssert.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("blank.height"));
        DriverUtils.acceptAlert();

        softAssert.assertAll();
    }

    @Test(description = "Folder field is not allowed to be empty")
    public void DA_PANEL_TC045() {
        Page page = new Page();
        Panel panel = new Panel();

        dialogPage.createNewPage(page);
        dialogPage.openCreatePanelDialogFromHomePage();
        dialogPage.enterPanelInformation(panel);
        dialogPage.clickOKButton();
        dialogPage.enterFolderLink("");
        dialogPage.clickPanelConfigurationOKButton();
        softAssert.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("invalid.panel.folder"));
        DriverUtils.acceptAlert();

        softAssert.assertAll();
    }

    @Test(description = "Only valid folder path of corresponding item type ( e.g. Actions, Test Modules) are allowed to be entered into Folder field")
    public void DA_PANEL_TC046() {
        Page page = new Page();
        Panel panel = new Panel();

        dialogPage.createNewPage(page);
        dialogPage.openCreatePanelDialogFromHomePage();
        dialogPage.enterPanelInformation(panel);
        dialogPage.clickOKButton();
        dialogPage.enterFolderLink("abc");
        dialogPage.clickPanelConfigurationOKButton();
        softAssert.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("invalid.panel.folder"));
        DriverUtils.acceptAlert();
        dialogPage.enterFolderLink(Folder.randomFolderLink());
        dialogPage.clickPanelConfigurationOKButton();
        softAssert.assertTrue(panelPage.isPanelCreated(panel.getName()));

        softAssert.assertAll();
    }

    @Test(description = "User is able to navigate properly to folders with Select Folder form")
    public void DA_PANEL_TC047() {
        Page page = new Page();
        Panel panel = new Panel();

        dialogPage.createNewPage(page);
        dialogPage.openCreatePanelDialogFromHomePage();
        dialogPage.enterPanelInformation(panel);
        dialogPage.clickOKButton();
        dialogPage.clickOpenFolderIcon();
        dialogPage.chooseFolderInForm(Folder.randomFolder());
        dialogPage.clickPanelConfigurationOKButton();
        softAssert.assertTrue(panelPage.isPanelCreated(panel.getName()));

        softAssert.assertAll();
    }

    @Test(description = "Population of corresponding item type ( e.g. Actions, Test Modules) folders is correct in Select Folder form")
    public void DA_PANEL_TC048() {
        Page page = new Page();
        Panel panel = new Panel();

        dialogPage.createNewPage(page);
        dialogPage.openCreatePanelDialogFromHomePage();
        dialogPage.enterPanelInformation(panel);
        dialogPage.clickOKButton();
        dialogPage.clickOpenFolderIcon();
        softAssert.assertTrue(dialogPage.isFolderCorrectInSelectFolderForm());
        dialogPage.closeChooseFolderForm();
        dialogPage.clickPanelConfigurationCancelButton();

        softAssert.assertAll();
    }
}
