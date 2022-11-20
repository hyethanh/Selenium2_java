package com.auto.model;

import com.auto.utils.FakerUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DataProfile {

    private String name;

    public DataProfile() {
        this.name = FakerUtils.name();
    }
}
