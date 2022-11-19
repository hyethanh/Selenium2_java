package com.auto.model;

import com.auto.utils.FakerUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
public class Page {
    private String name;
    private Page parent;
    private int column;
    private String displayAfter;
    private boolean isPublic;
    private static final Page overviewPage = new Page("Overview", null, 2, "", false);

    public Page() {
        this.name = FakerUtils.name();
        this.parent = null;
        this.column = 2;
        this.displayAfter = "";
        this.isPublic = false;
    }

    public Page(String name) {
        this.name = name;
        this.parent = null;
        this.column = 2;
        this.displayAfter = "";
        this.isPublic = false;
    }

    public static Page overviewPage() {
        return Page.overviewPage;
    }
}
