package com.auto.model;

import com.auto.data.enums.*;
import com.auto.data.enums.ChartSeries;
import com.auto.data.enums.ChartType;
import com.auto.data.enums.DataProfiles;
import com.auto.utils.Constants;
import com.auto.utils.FakerUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class Panel {

    private PanelType type;
    private DataProfiles dataProfile;
    private String name;
    private String chartTitle;
    private ChartType chartType;
    private ChartSeries chartSeries;
    private ChartLegends chartLegends;
    private DataLabel dataLabel;
    private boolean isShowTitle;
    private String style;

    public Panel() {
        this.type = null;
        this.dataProfile = null;
        this.name = FakerUtils.name();
        this.chartTitle = "";
        this.chartType = null;
        this.chartSeries = ChartSeries.randomSeries();
        this.chartLegends = null;
        this.dataLabel = null;
        this.isShowTitle = false;
        this.style = Constants.DEFAULT_PANEL_STYLE;
    }
}
