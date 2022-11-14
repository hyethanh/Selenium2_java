package com.auto.model;

import com.auto.data.enums.*;
import com.auto.data.enums.ChartSeries;
import com.auto.data.enums.ChartType;
import com.auto.data.enums.DataProfiles;
import com.auto.data.enums.DisplayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Panel {

    private DisplayType type;
    private DataProfiles dataProfile;
    private String name;
    private String chartTitle;
    private ChartType chartType;
    private ChartSeries chartSeries;
    private ChartLegends chartLegends;
    private DataLabel dataLabel;

    public Panel(String name) {
        this.type = null;
        this.dataProfile = null;
        this.name = name;
        this.chartTitle = "";
        this.chartType = null;
        this.chartSeries = ChartSeries.randomSeries();
        this.chartLegends = null;
        this.dataLabel = null;
    }

    public Panel(String name, ChartSeries chartSeries) {
        this.type = null;
        this.dataProfile = null;
        this.name = name;
        this.chartTitle = "";
        this.chartType = null;
        this.chartSeries = chartSeries;
        this.chartLegends = null;
        this.dataLabel = null;
    }

    public Panel(String name, String chartTitle) {
        this.type = null;
        this.dataProfile = null;
        this.name = name;
        this.chartTitle = chartTitle;
        this.chartType = null;
        this.chartSeries = ChartSeries.randomSeries();
        this.chartLegends = null;
        this.dataLabel = null;
    }
}
