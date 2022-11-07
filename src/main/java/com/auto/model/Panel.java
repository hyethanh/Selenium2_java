package com.auto.model;

import com.auto.data.enums.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Panel {

    private DisplayType type;
    private DataProfile dataProfile;
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
}
