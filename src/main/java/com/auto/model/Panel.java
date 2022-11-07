package com.auto.model;

import com.auto.data.enums.ChartSeries;
import com.auto.data.enums.ChartType;
import com.auto.data.enums.DataProfile;
import com.auto.data.enums.DisplayType;
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
}
