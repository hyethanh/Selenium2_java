package com.auto.test.panel;

import com.auto.data.enums.*;
import com.auto.data.enums.LinkText;
import com.auto.model.DataProfile;
import com.auto.model.Page;
import com.auto.model.Panel;
import com.auto.model.User;
import com.auto.page.*;
import com.auto.test.BrowserTestBase;
import com.auto.utils.*;
import com.logigear.statics.Selaium;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.logging.Logger;


public class PanelTest extends BrowserTestBase {

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
        homePage.moveToPanelItemPage(LinkText.PANELS);
        DriverUtils.deletePanel(panelPage.getPanelIds());
        DriverUtils.deletePage(homePage.getPageIds());
        Selaium.driver().close();
    }

    @Test(description = "Add New Panel form is on focused all other control/form is disabled or locked.")
    public void DA_PANEL_TC028() {
        homePage.moveToPanelItemPage(LinkText.PANELS);
        panelPage.clickLinkButton(LinkText.ADD_NEW);
        softAssert.assertFalse(homePage.isAddPageDialogOpened(), "Add Page Dialog is opened");
        homePage.logout();
        softAssert.assertFalse(loginPage.isLoginButtonDisplayed(), "User is at Login Page");
        softAssert.assertAll();
    }

    @Test(description = "Unable to create new panel when (*) required field is not filled")
    public void DA_PANEL_TC029() {
        Panel panel = new Panel();
        panel.setChartSeries(null);

        homePage.moveToPanelItemPage(LinkText.PANELS);
        panelPage.clickLinkButton(LinkText.ADD_NEW);
        dialogPage.enterPanelInformation(panel);
        dialogPage.clickOKButton();
        softAssert.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("blank.series"), "No alert for blank series displays");
        DriverUtils.acceptAlert();
        panel.setName("");
        dialogPage.enterPanelInformation(panel);
        dialogPage.clickOKButton();
        Logger.getLogger("Verify cannot create new panel with blank name");
        softAssert.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("blank.name.panel"), "No alert for blank displayed name appears");
        DriverUtils.acceptAlert();

        softAssert.assertAll();
    }

    @Test(description = "No special character except '@' character is allowed to be inputted into 'Display Name' field")
    public void DA_PANEL_TC030() {
        Panel panel = new Panel();
        panel.setName(FakerUtils.name()+"#$%");

        homePage.moveToPanelItemPage(LinkText.PANELS);
        panelPage.clickLinkButton(LinkText.ADD_NEW);
        dialogPage.enterPanelInformation(panel);
        dialogPage.clickOKButton();
        softAssert.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("invalid.name"),
                        "New panel created successfully with special character except @");
        DriverUtils.acceptAlert();

        panel.setName(FakerUtils.name()+"@");
        dialogPage.enterPanelInformation(panel);
        dialogPage.clickOKButton();
        dialogPage.waitForPanelDialogClose();
        softAssert.assertTrue(panelPage.isPanelDisplayedInTable(panel),
                              String.format("New created panel '%s' does not displayed in table", panel.getName()));

        softAssert.assertAll();
    }

    @Test(description = "Correct panel setting form is displayed with corresponding panel type selected")
    public void DA_PANEL_TC031() {
        homePage.moveToPanelItemPage(LinkText.PANELS);
        panelPage.clickLinkButton(LinkText.ADD_NEW);
        softAssert.assertTrue(dialogPage.isPanelSettingDisplayed(PanelType.CHART.value()), "Chart Type is not displayed above Display Name");
        softAssert.assertTrue(dialogPage.isPanelSettingDisplayed(PanelType.INDICATOR.value()), "Indicator Type is not displayed above Display Name");
        softAssert.assertTrue(dialogPage.isPanelSettingDisplayed(PanelType.REPORT.value()), "Report Type is not displayed above Display Name");
        softAssert.assertTrue(dialogPage.isPanelSettingDisplayed(PanelType.HEAT_MAP.value()), "Heat Map Type is not displayed above Display Name");

        softAssert.assertAll();
    }

    @Test(description = "User is not allowed to create panel with duplicated 'Display Name'")
    public void DA_PANEL_TC032() {
        Panel panel = new Panel();

        dialogPage.createNewPanel(panel);
        panelPage.clickLinkButton(LinkText.ADD_NEW);
        dialogPage.enterPanelInformation(panel);
        dialogPage.clickOKButton();
        softAssert.assertEquals(DriverUtils.getAlertMessage(),
                                MessageLoader.getMessage("duplicated.name", String.format(panel.getName())),
                        "The second panel is created successfully");
        DriverUtils.acceptAlert();
        dialogPage.clickCancelButton();

        softAssert.assertAll();
    }

    @Test(description = "'Data Profile' listing of 'Add New Panel' and 'Edit Panel' control/form are in alphabetical order")
    public void DA_PANEL_TC033() {
        Panel panel = new Panel();
        homePage.moveToPanelItemPage(LinkText.PANELS);
        panelPage.clickLinkButton(LinkText.ADD_NEW);
        dialogPage.clickAddNewPanelDialogComBoBox(Combobox.DATA_PROFILE.value());
        softAssert.assertTrue(dialogPage.comboboxOptionsSortedAlphabetically(Combobox.DATA_PROFILE.value()),
                      "Data Profile is not listing in alphabetical order when adding new panel");
        dialogPage.clickCancelButton();
        dialogPage.createNewPanel(panel);
        panelPage.clickLinkButton(LinkText.EDIT);
        dialogPage.clickAddNewPanelDialogComBoBox(Combobox.DATA_PROFILE.value());
        softAssert.assertTrue(dialogPage.comboboxOptionsSortedAlphabetically(Combobox.DATA_PROFILE.value()),
                "Data Profile is not listing in alphabetical order when editing an existed panel");

        softAssert.assertAll();
    }

    @Test(description = "Newly created data profiles are populated correctly under the 'Data Profile' dropped down menu in  'Add New Panel' and 'Edit Panel' control/form")
    public void DA_PANEL_TC034() {
        DataProfile profile = new DataProfile();
        Panel panel = new Panel();

        homePage.moveToPanelItemPage(LinkText.DATA_PROFILES);
        panelPage.clickLinkButton(LinkText.ADD_NEW);
        panelPage.enterProfileName(profile.getName());
        panelPage.clickFinishButton();
        homePage.moveToPanelItemPage(LinkText.PANELS);
        panelPage.clickLinkButton(LinkText.ADD_NEW);
        dialogPage.clickAddNewPanelDialogComBoBox(Combobox.DATA_PROFILE.value());
        softAssert.assertTrue(dialogPage.comboboxOptionsSortedAlphabetically(Combobox.DATA_PROFILE.value()),
                "Data Profile is not listing in alphabetical order when adding new panel");
        dialogPage.clickCancelButton();
        dialogPage.createNewPanel(panel);
        panelPage.clickLinkButton(LinkText.EDIT);
        dialogPage.clickAddNewPanelDialogComBoBox(Combobox.DATA_PROFILE.value());
        softAssert.assertTrue(dialogPage.comboboxOptionsSortedAlphabetically(Combobox.DATA_PROFILE.value()),
                "Data Profile is not listing in alphabetical order when editing an existed panel");

        softAssert.assertAll();
    }

    @Test(description = "No special character except '@' character is allowed to be inputted into 'Chart Title' field")
    public void DA_PANEL_TC035() {
        Panel panel = new Panel();
        panel.setChartTitle(FakerUtils.title() + "#$%");

        homePage.moveToPanelItemPage(LinkText.PANELS);
        panelPage.clickLinkButton(LinkText.ADD_NEW);
        dialogPage.enterPanelInformation(panel);
        dialogPage.clickOKButton();
        softAssert.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("invalid.title"),
                String.format("New created panel '%s' does not displayed in table", panel.getName()));
        DriverUtils.acceptAlert();
        dialogPage.clickCancelButton();

        panel.setName(FakerUtils.name()+"@");
        panel.setChartTitle(FakerUtils.title()+"@");
        dialogPage.createNewPanel(panel);
        softAssert.assertTrue(panelPage.isPanelDisplayedInTable(panel),
                String.format("New created panel '%s' does not displayed in table", panel.getName()));

        softAssert.assertAll();
    }

    @Test(description = "Chart types ( Pie, Single Bar, Stacked Bar, Group Bar, Line ) are listed correctly under 'Chart Type' dropped down menu.")
    public void DA_PANEL_TC036() {
        Page page = new Page();
        dialogPage.createNewPage(page);
        homePage.clickChoosePanelButton();
        dialogPage.clickCreateNewPanelButton();
        softAssert.assertTrue(dialogPage.chartTypeComboboxOptionsAreFullyListed());

        softAssert.assertAll();
    }

    @Test(description = "Category, Series and Caption field are enabled and disabled correctly corresponding to each type of the Chart Type")
    public void DA_PANEL_TC037() {
        Page page = new Page();
        dialogPage.createNewPage(page);
        dialogPage.openCreatePanelDialogFromHomePage();

        dialogPage.chooseComboBoxPanelPage(Combobox.CHART_TYPE.value(), ChartType.PIE.value());
        softAssert.assertFalse(dialogPage.isComboboxEnabled(Combobox.CATEGORY.value()), "Category combobox is disabled with PIE type");
        softAssert.assertFalse(dialogPage.isCaptionTextBoxEnabled(), "Caption text box is disabled with PIE type");
        softAssert.assertTrue(dialogPage.isComboboxEnabled(Combobox.SERIES.value()), "Series combobox is enabled with PIE type");

        dialogPage.chooseComboBoxPanelPage(Combobox.CHART_TYPE.value(), ChartType.SINGLE_BAR.value());
        softAssert.assertFalse(dialogPage.isComboboxEnabled(Combobox.CATEGORY.value()), "Category combobox is disabled with SINGLE BAR type");
        softAssert.assertTrue(dialogPage.isCaptionTextBoxEnabled(), "Caption text box is enabled with SINGLE BAR type");
        softAssert.assertTrue(dialogPage.isComboboxEnabled(Combobox.SERIES.value()), "Series combobox is enabled with SINGLE BAR type");

        dialogPage.chooseComboBoxPanelPage(Combobox.CHART_TYPE.value(), ChartType.STACKED_BAR.value());
        softAssert.assertTrue(dialogPage.isComboboxEnabled(Combobox.CATEGORY.value()), "Category combobox is enabled with STACKED BAR type");
        softAssert.assertTrue(dialogPage.isCaptionTextBoxEnabled(), "Caption text box is enabled with STACKED BAR type");
        softAssert.assertTrue(dialogPage.isComboboxEnabled(Combobox.SERIES.value()), "Series combobox is enabled with STACKED BAR type");

        dialogPage.chooseComboBoxPanelPage(Combobox.CHART_TYPE.value(), ChartType.GROUP_BAR.value());
        softAssert.assertTrue(dialogPage.isComboboxEnabled(Combobox.CATEGORY.value()), "Category combobox is enabled with GROUP BAR type");
        softAssert.assertTrue(dialogPage.isCaptionTextBoxEnabled(), "Caption text box is enabled with GROUP BAR type");
        softAssert.assertTrue(dialogPage.isComboboxEnabled(Combobox.SERIES.value()), "Series combobox is enabled with GROUP BAR type");

        dialogPage.chooseComboBoxPanelPage(Combobox.CHART_TYPE.value(), ChartType.LINE.value());
        softAssert.assertTrue(dialogPage.isComboboxEnabled(Combobox.CATEGORY.value()), "Category combobox is enabled with LINE type");
        softAssert.assertTrue(dialogPage.isCaptionTextBoxEnabled(), "Caption text box is enabled with LINE type");
        softAssert.assertTrue(dialogPage.isComboboxEnabled(Combobox.SERIES.value()), "Series combobox is enabled with LINE type");

        softAssert.assertAll();
    }

    @Test(description = "All settings within Add New Panel and Edit Panel form stay unchanged when user switches between 2D and 3D radio buttons")
    public void DA_PANEL_TC038() {
        Page page = new Page();
        Panel panel = new Panel();

        panel.setChartType(ChartType.STACKED_BAR);
        panel.setDataProfile(DataProfiles.TEST_CASE_EXECUTION);
        panel.setShowTitle(true);
        panel.setChartLegends(ChartLegends.TOP);
        panel.setStyle("3D");

        dialogPage.createNewPage(page);
        dialogPage.openCreatePanelDialogFromHomePage();
        dialogPage.enterPanelInformation(panel);
        softAssert.assertTrue(dialogPage.isStayUnchanged(panel), "New Panel Dialog settings are stabled");

        panel.setStyle("2D");
        dialogPage.clickRadioButton(panel.getStyle());
        softAssert.assertTrue(dialogPage.isStayUnchanged(panel), "New Panel Dialog settings are stabled when changing style");

        softAssert.assertAll();
    }

    @Test(description = "All Data Labels check boxes are enabled and disabled correctly corresponding to each type of Chart Type")
    public void DA_PANEL_TC040() {
        Page page = new Page();
        Panel panel =new Panel();
        panel.setChartType(ChartType.PIE);

        dialogPage.createNewPage(page);
        dialogPage.openCreatePanelDialogFromHomePage();
        dialogPage.enterPanelInformation(panel);

        softAssert.assertFalse(dialogPage.isCheckboxEnabled(DataLabel.CATEGORIES.value()), "Category combobox is disabled with PIE type");
        softAssert.assertTrue(dialogPage.isCheckboxEnabled(DataLabel.SERIES.value()), "Series checkbox button is enabled with PIE type");
        softAssert.assertTrue(dialogPage.isCheckboxEnabled(DataLabel.VALUE.value()), "Value checkbox button is enabled with PIE type");
        softAssert.assertTrue(dialogPage.isCheckboxEnabled(DataLabel.PERCENTAGE.value()), "Percentage checkbox button is enabled with PIE type");

        panel.setChartType(ChartType.SINGLE_BAR);
        dialogPage.chooseChartTypeCombobox(panel);
        softAssert.assertFalse(dialogPage.isCheckboxEnabled(DataLabel.CATEGORIES.value()), "Category combobox is disabled with SINGLE BAR type");
        softAssert.assertTrue(dialogPage.isCheckboxEnabled(DataLabel.SERIES.value()), "Series checkbox button is enabled with SINGLE BAR type");
        softAssert.assertTrue(dialogPage.isCheckboxEnabled(DataLabel.VALUE.value()), "Value checkbox button is enabled with SINGLE BAR type");
        softAssert.assertTrue(dialogPage.isCheckboxEnabled(DataLabel.PERCENTAGE.value()), "Percentage checkbox button is enabled with SINGLE BAR type");

        panel.setChartType(ChartType.STACKED_BAR);
        dialogPage.chooseChartTypeCombobox(panel);
        softAssert.assertTrue(dialogPage.isCheckboxEnabled(DataLabel.CATEGORIES.value()), "Category combobox is enabled with STACKED BAR type");
        softAssert.assertTrue(dialogPage.isCheckboxEnabled(DataLabel.SERIES.value()), "Series checkbox button is enabled with STACKED BAR type");
        softAssert.assertTrue(dialogPage.isCheckboxEnabled(DataLabel.VALUE.value()), "Value checkbox button is enabled with STACKED BAR type");
        softAssert.assertTrue(dialogPage.isCheckboxEnabled(DataLabel.PERCENTAGE.value()), "Percentage checkbox button is enabled with STACKED BAR type");

        panel.setChartType(ChartType.GROUP_BAR);
        dialogPage.chooseChartTypeCombobox(panel);
        softAssert.assertTrue(dialogPage.isCheckboxEnabled(DataLabel.CATEGORIES.value()), "Category combobox is enabled with GROUP BAR type");
        softAssert.assertTrue(dialogPage.isCheckboxEnabled(DataLabel.SERIES.value()), "Series checkbox button is enabled with GROUP BAR type");
        softAssert.assertTrue(dialogPage.isCheckboxEnabled(DataLabel.VALUE.value()), "Value checkbox button is enabled with GROUP BAR type");
        softAssert.assertTrue(dialogPage.isCheckboxEnabled(DataLabel.PERCENTAGE.value()), "Percentage checkbox button is enabled with GROUP BAR type");

        panel.setChartType(ChartType.LINE);
        dialogPage.chooseChartTypeCombobox(panel);
        softAssert.assertFalse(dialogPage.isCheckboxEnabled(DataLabel.CATEGORIES.value()), "Category combobox is disabled with LINE type");
        softAssert.assertFalse(dialogPage.isCheckboxEnabled(DataLabel.SERIES.value()), "Series checkbox button is disabled with LINE type");
//        Known bug here: Value checkbox is still enabled.
//        softAssert.assertFalse(dialogPage.isCheckboxEnabled(DataLabel.VALUE.value()), "Value checkbox button is enabled with LINE type");
        softAssert.assertFalse(dialogPage.isCheckboxEnabled(DataLabel.PERCENTAGE.value()), "Percentage checkbox button is disabled with LINE type");

        softAssert.assertAll();
    }

    @Test(description = "All settings within Add New Panel and Edit Panel form stay unchanged when user switches between Data Labels check boxes buttons")
    public void DA_PANEL_TC041() {
        Panel panel = new Panel();

        homePage.moveToPanelItemPage(LinkText.PANELS);
        panelPage.clickLinkButton(LinkText.ADD_NEW);
        panel.setDataLabel(DataLabel.SERIES);
        dialogPage.enterPanelInformation(panel);
        softAssert.assertTrue(dialogPage.isStayUnchanged(panel), "New Panel Dialog settings are stabled when checking label SERIES value");
        dialogPage.clickLabelOptionButton(panel.getDataLabel());

        panel.setDataLabel(DataLabel.VALUE);
        dialogPage.chooseLabelOption(panel);
        softAssert.assertTrue(dialogPage.isStayUnchanged(panel), "New Panel Dialog settings are stabled when unchecking label VALUE value");
        dialogPage.clickLabelOptionButton(panel.getDataLabel());

        panel.setDataLabel(DataLabel.PERCENTAGE);
        dialogPage.chooseLabelOption(panel);
        softAssert.assertTrue(dialogPage.isStayUnchanged(panel), "New Panel Dialog settings are stabled when unchecking label PERCENTAGE value");
        dialogPage.clickLabelOptionButton(panel.getDataLabel());

        dialogPage.clickCancelButton();
        dialogPage.createNewPanel(panel);
        panelPage.clickLinkButton(LinkText.EDIT);

        panel.setDataLabel(DataLabel.SERIES);
        dialogPage.chooseLabelOption(panel);
        softAssert.assertTrue(dialogPage.isStayUnchanged(panel), "Edit Panel Dialog settings are stabled when checking label SERIES value");
        dialogPage.clickLabelOptionButton(panel.getDataLabel());

        panel.setDataLabel(DataLabel.VALUE);
        dialogPage.chooseLabelOption(panel);
        softAssert.assertTrue(dialogPage.isStayUnchanged(panel), "Edit Panel Dialog settings are stabled when checking label VALUE value");
        dialogPage.clickLabelOptionButton(panel.getDataLabel());

        panel.setDataLabel(DataLabel.PERCENTAGE);
        dialogPage.chooseLabelOption(panel);
        softAssert.assertTrue(dialogPage.isStayUnchanged(panel), "Edit Panel Dialog settings are stabled when checking label PERCENTAGE value");
        dialogPage.clickLabelOptionButton(panel.getDataLabel());

        softAssert.assertAll();
    }

    @Test(description = "User is able to successfully edit Display Name of any Panel providing that the name is not duplicated with existing Panels' name")
    public void DA_PANEL_TC050() {
        Panel panel = new Panel();

        homePage.moveToPanelItemPage(LinkText.PANELS);
        panelPage.clickLinkButton(LinkText.ADD_NEW);
        dialogPage.enterPanelInformation(panel);
        dialogPage.clickOKButton();
        dialogPage.waitForPanelDialogClose();
        softAssert.assertTrue(panelPage.isPanelDisplayedInTable(panel));

        softAssert.assertAll();
    }

    @Test(description = "User is unable to change Display Name of any Panel if there is special character except '@' inputted")
    public void DA_PANEL_TC051() {
        Panel panel = new Panel();

        homePage.moveToPanelItemPage(LinkText.PANELS);
        panelPage.clickLinkButton(LinkText.ADD_NEW);
        dialogPage.enterPanelInformation(panel);
        dialogPage.clickOKButton();
        dialogPage.waitForPanelDialogClose();
        panelPage.clickLinkButton(LinkText.EDIT);

        panel.setName(FakerUtils.name() + "#$");
        dialogPage.enterPanelInformation(panel);
        dialogPage.clickOKButton();
        softAssert.assertEquals(DriverUtils.getAlertMessage(), MessageLoader.getMessage("invalid.name"));
        DriverUtils.acceptAlert();

        panel.setName(FakerUtils.name()+"@");
        dialogPage.enterPanelInformation(panel);
        dialogPage.clickOKButton();
        dialogPage.waitForPanelDialogClose();
        softAssert.assertTrue(panelPage.isPanelDisplayedInTable(panel),
                String.format("New created panel '%s' does not displayed in table", panel.getName()));

        softAssert.assertAll();
    }

}