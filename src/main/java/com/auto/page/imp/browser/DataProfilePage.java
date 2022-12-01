package com.auto.page.imp.browser;

import com.auto.data.enums.ChartType;
import com.auto.data.enums.DataLabel;
import com.auto.data.enums.DataProfiles;
import com.auto.element.Element;
import com.auto.page.IDataProfilePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataProfilePage implements IDataProfilePage {

    private Element dataProfilesColumn = new Element(By.xpath("//td[@class='chkCol']/following-sibling::td[not (@class='center')]"));


    @Step("Verify Data Profiles are populated correctly")
    public boolean areDataProfilesListedCorrectly() {
        List<String> list = Arrays.stream(DataProfiles.values()).map(DataProfiles::value).collect(Collectors.toList());
        return list.containsAll(dataProfilesColumn.elements().stream().map(p -> p.getText()).collect(Collectors.toList()));
    }
}
